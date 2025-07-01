package com.web.jsf;

import com.web.bl.UserBean;
import  com.web.entity.Users;
import com.web.service.AuthService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Named("authController")
@SessionScoped
public class AuthController implements Serializable {
    @Inject
    private UserBean userSessionBean;

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private boolean rememberMe;


    @Inject
    private AuthService authService;

    @Inject
    public AuthController(@Named("userSessionBean") UserBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }
    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies).filter(c -> "userEmail".equals(c.getName()))
                    .findFirst()
                    .ifPresent(c -> this.email = c.getValue()); // pre-fill
        }
    }
    // Login Method
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();

        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email and password are required", null));
            return null;
        }

        if (!isValidEmail(email)) {
            context.addMessage("loginForm:email", new FacesMessage("Invalid email format"));
            return null;
        }

        if (password.length() < 6) {
            context.addMessage("loginForm:password", new FacesMessage("Password must be at least 6 characters"));
            return null;
        }

        try {
            Users user = authService.login(email, password);
            if (user != null) {
                this.name = user.getName();

                if (rememberMe) {
                    // Set Cookie
                    Cookie cookie = new Cookie("rememberMe", user.getId().toString());
                    cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                    cookie.setHttpOnly(true);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);

                    // Capture IP, browser, and device
                    String userAgent = request.getHeader("User-Agent");
                    String ipAddress = request.getRemoteAddr();
                    String deviceInfo = parseDevice(userAgent);
                    String browserInfo = parseBrowser(userAgent);

                    // Store in remembered_devices
                    authService.storeRememberedDevice(user.getId().toString(), ipAddress, userAgent, deviceInfo, browserInfo);
                }

                userSessionBean.setLoggedInUser(user);

                if ("ADMIN".equalsIgnoreCase(user.getRole1())) {
                    return "admin_home.xhtml?faces-redirect=true";
                } else {
                    return "index.xhtml?faces-redirect=true";
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error during login", null));
            return null;
        }
    }


    private String parseBrowser(String userAgent) {
        if (userAgent == null) return "Unknown";
        if (userAgent.contains("Chrome")) return "Chrome";
        if (userAgent.contains("Firefox")) return "Firefox";
        if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) return "Safari";
        if (userAgent.contains("Edge")) return "Edge";
        return "Unknown";
    }

    private String parseDevice(String userAgent) {
        if (userAgent == null) return "Unknown";
        if (userAgent.contains("Mobile")) return "Mobile";
        if (userAgent.contains("Windows")) return "Windows PC";
        if (userAgent.contains("Macintosh")) return "Mac";
        return "Unknown Device";
    }
    public void checkRememberMe() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberMe".equals(cookie.getName())) {
                    // Example: prefill email or auto-login
                    String rememberedUserId = cookie.getValue();

                    // Use authService to load user by ID, then prefill email or auto-login
                    Users user = authService.getEjbFacade().find(rememberedUserId);
                    if (user != null) {
                        this.email = user.getEmail(); // prefill email field
                        // Optionally, auto-login user here by setting session info
                    }
                    break;
                }
            }
        }
    }

    // Signup Method
    public String signup() {
        FacesContext context = FacesContext.getCurrentInstance();

        // Validate email format
        if (!isValidEmail(email)) {
            context.addMessage("signupForm:email", new FacesMessage("Invalid email format"));
            return null;
        }

        // Validate password strength
        if (!isStrongPassword(password)) {
            context.addMessage("signupForm:password", new FacesMessage("Password must be at least 8 characters, include uppercase, lowercase, and a number"));
            return null;
        }

        try {
            // Confirm password match check
            if (!password.equals(confirmPassword)) {
                context.addMessage("signupForm:confirmPassword", new FacesMessage("Passwords do not match"));
                return null;
            }

            boolean created = authService.signup(email, password, name);
            if (created) {
                context.addMessage(null, new FacesMessage("Signup successful. Please login."));
                return "login.xhtml?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Signup failed: User may already exist", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error during signup", null));
            return null;
        }
    }

    // Logout Method
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
    }

    // Email Validation
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Password Validation
    private boolean isStrongPassword(String password) {
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*[0-9].*")) return false;
        return true;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;}
    public boolean isRememberMe() { return rememberMe; }
    public void setRememberMe(boolean rememberMe) { this.rememberMe = rememberMe; }



}
