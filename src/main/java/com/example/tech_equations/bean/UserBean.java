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
@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

    private boolean showCreateForm = false;
    private User loggedInUser;
    private Date lastAccessTime;

    private User newUser = new User();

    @Inject
    private UserController userController;

    private List<User> users;
    private List<User> searchUser;

    private String searchTerm;


    public UserBean() {
        newUser.setRole("ADMIN"); // ✅ Set role during initialization
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

    public void updateSession() {
        this.lastAccessTime = new Date(); // Update the access time on activity
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


    public boolean isShowCreateForm() {
        return showCreateForm;
    }

    public void setShowCreateForm(boolean showCreateForm) {
        this.showCreateForm = showCreateForm;
    }

    public void toggleCreateForm() {
        this.showCreateForm = !this.showCreateForm;
    }
    // User CRUD Methods
    public List<User> getUsers() {

        users = userController.getUsers();

        return users;
    }

    public List<User> getSearchUser(String term) {
        searchUser = userController.searchUsers(term);
        return searchUser;
    }



    public User getUser(String id) {
        return userController.getUser(id);
    }

    public void createUser() {
        userController.createUser(newUser);
        newUser = new User(); // this resets the form
        newUser.setRole("ADMIN");    // Reset default role again
    }
    public void updateUser(User user) {
        userController.updateUser(user);
    }

    public void deleteUser(String id) {
        userController.deleteUser(id);
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void searchUsers() {
        if (searchTerm == null || searchTerm.isEmpty()) {
            this.searchUser = userController.getUsers(); // fallback
        } else {
            this.searchUser = userController.searchUsers(searchTerm);
        }
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

}
