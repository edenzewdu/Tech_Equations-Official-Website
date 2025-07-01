package com.web.security;


import com.web.entity.Users;
import com.web.jsf.UsersController;
import com.web.jsf.util.JsfUtil;
import com.web.session.AbstractFacadeQuerySavvy;
import jakarta.ejb.EJB;
import jakarta.el.ELContext;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "loginView")
@SessionScoped
public class LoginView implements Serializable {
    private static final long serialVersionUID = 3254181235309041386L;
    private static Logger log = Logger.getLogger(LoginView.class.getName());
   // private final UsersController UsersController;

    @EJB
    private AbstractFacadeQuerySavvy ejbFacade1;

    private Integer employeeId;
    private String userName;
    private String password;
    private String oldPassword;

    private String newPassword;
    private String confirmPassword;
    

    private boolean availSMSAndChapa = true;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Users user;

    public Users getAuthenticatedUser() {
        return user;
    }

    public boolean isAvailSMSAndChapa() {
        return availSMSAndChapa;
    }

    public void setAvailSMSAndChapa(boolean availSMSAndChapa) {
        this.availSMSAndChapa = availSMSAndChapa;
    }

    @Inject
    private UsersController usersController;

    public String login() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        ELContext elContext = context.getELContext();


      //  Employees employee = employeesController.getEmployees(employeeId);
        Users user = usersController.getUsersByEmail(userName);
        if (user == null) {
            JsfUtil.addErrorMessage("Login Failed.");
            return "signin";
        }
//        List<Users> usersTable = (List<Users>) ejbFacade1.findById("Users.findByEmployee", "employeeId", employee.getId());
//
//        if (usersTable.isEmpty()) {
//            JsfUtil.addErrorMessage("Login Failed");
//            return "signin";
//        }

      //  user = usersTable.get(0);
        try {
            if (AuthenticationUtils.encodeSHA2561(password).equals(user.getPasswordHash())) {
                log.info("Authentication done for user: " + user.getName());
                this.user = user;

                if (user.getStatus().equalsIgnoreCase("Pending")) {

                    JsfUtil.addErrorMessage("Your account is Pending Confirmation.");
                  //  return "signin";
                }

                if (user.getStatus().equalsIgnoreCase("Inactive")) {
                    JsfUtil.addErrorMessage("Your account is inactive, Please contact system administrator.");
                    return "signin";
                }

                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                Map<String, Object> sessionMap = externalContext.getSessionMap();
                HttpSession session = request.getSession();
                session.setAttribute("username", user);

                sessionMap.put("User", user);
               // loadUserLinkPrivileges(user);

                if (user.getStatus().equalsIgnoreCase("New")) {
                    // return "changePassword";
                    FacesContext.getCurrentInstance().getExternalContext().redirect("changePassword.xhtml");
                    return "";
                } else if (user.getStatus().equalsIgnoreCase("Inactive")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("inactive.xhtml");
                    return "";
                }



                this.password = "";

                if(user.getRole1().equals("ADMIN")){
                    urlBean.redirectWithLabel("/website/admin/dashboard.xhtml");
                } else if (user.getRole1().equals("USER")) {
                    return "/website/public/index.xhtml";
                } else if (user.getRole1().equals("EMPLOYEE")) {
                    urlBean.redirectWithLabel("/website/employee/dashboard.xhtml");
                } else {
                    urlBean.redirectWithLabel("/website/guest/dashboard.xhtml");

                }
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                return "";
            } else {
                JsfUtil.addErrorMessage("Login Failed.");
            }
        } catch (Exception e) {
            //  ejbFacade.clearingGeneralQueryObjects();
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed!", null));
            return "signin";
        }
        return "signin";
    }

@Inject
private URLBean urlBean;

    private Map<String, String> userLinkPrivileges = new HashMap<>();
    private Map<String, String> userButtonPrivileges = new HashMap<>();

    public Map<String, String> getUserLinkPrivileges() {
        return userLinkPrivileges;
    }

    public void setUserLinkPrivileges(Map<String, String> userLinkPrivileges) {
        this.userLinkPrivileges = userLinkPrivileges;
    }

    public Map<String, String> getUserButtonPrivileges() {
        return userButtonPrivileges;
    }

    public void setUserButtonPrivileges(Map<String, String> userButtonPrivileges) {
        this.userButtonPrivileges = userButtonPrivileges;
    }



    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        ELContext elContext = context.getELContext();
        // loginManager loginManager = (loginManager) elContext.getELResolver().getValue(elContext, null, "loginManager");
        //  LoginView loginView = (LoginView) elContext.getELResolver().getValue(elContext, null, "loginView");

        try {

            request.logout();
            // clear the session
            ((HttpSession) context.getExternalContext().getSession(false)).invalidate();

            // loginManager.getUsersList().remove(user);
            this.user = null;
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
        }
        try {
           return "/signin.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }





    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
