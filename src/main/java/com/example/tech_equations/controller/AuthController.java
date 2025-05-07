package com.example.tech_equations.controller;

import com.example.tech_equations.bean.UserBean;
import com.example.tech_equations.model.User;
import com.example.tech_equations.service.AuthService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named("authController")
@SessionScoped
public class AuthController implements Serializable {
    @Inject
    private UserBean userSessionBean;

    private String name;
    private String email;
    private String password;

    @Inject
    private AuthService authService;

    @Inject
    public AuthController(@Named("userSessionBean") UserBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    // Login Method
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email and password are required", null));
            return null;
        }

        // Email format check
        if (!isValidEmail(email)) {
            context.addMessage("loginForm:email", new FacesMessage("Invalid email format"));
            return null;
        }

        // Password strength check
        if (password.length() < 6) {
            context.addMessage("loginForm:password", new FacesMessage("Password must be at least 6 characters"));
            return null;
        }

        try {
            User user = authService.login(email, password);
            if (user != null) {
                this.name = user.getName(); // Assuming user has a name

                // Successful login, set the logged-in user in the session
                userSessionBean.setLoggedInUser(user); // Set the logged-in user in session bean
                System.out.println("Login successful");

                // Redirect based on user role
                if ("ADMIN".equalsIgnoreCase(user.getRole())) {  // Directly check the user role here
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


}
