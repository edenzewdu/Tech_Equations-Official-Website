package com.web.bl;

import com.web.entity.Users;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Date;

@Named("userSessionBean")
@SessionScoped
public class UserBean implements Serializable {
    private Users loggedInUser;
    private Date lastAccessTime;



    public UserBean() {
        this.lastAccessTime = new Date();// Set last access time when the bean is created
    }

    public Users getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Users loggedInUser) {
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



}
