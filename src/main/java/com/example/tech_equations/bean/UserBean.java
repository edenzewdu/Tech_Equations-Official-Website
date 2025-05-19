package com.example.tech_equations.bean;

import com.example.tech_equations.controller.AuthController;
import com.example.tech_equations.controller.UserController;
import com.example.tech_equations.model.User;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named("userSessionBean")
@SessionScoped
public class UserBean implements Serializable {
    private User loggedInUser;
    private Date lastAccessTime;



    public UserBean() {
        this.lastAccessTime = new Date();// Set last access time when the bean is created
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        this.lastAccessTime = new Date(); // Update last access time on login
    }

    public boolean isLoggedIn() {
        // Check if the user is logged in and session is valid (within 30 min)
        long currentTime = new Date().getTime();
        long sessionDuration = currentTime - lastAccessTime.getTime();

        if (sessionDuration > 1800000) {
            loggedInUser = null; // Invalidate the session if it has expired
        }

        return loggedInUser != null;
    }

    public String getFirstLetter() {
        return isLoggedIn() ? loggedInUser.getName().substring(0, 1).toUpperCase() : "";
    }

    public String getUsername() {
        return loggedInUser != null ? loggedInUser.getName() : "";
    }

    public void logout() {
        // Invalidate the session by setting loggedInUser to null
        loggedInUser = null;

        // Redirect to the login page
        try {
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
                    FacesContext.getCurrentInstance(), null, "login?faces-redirect=true"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
