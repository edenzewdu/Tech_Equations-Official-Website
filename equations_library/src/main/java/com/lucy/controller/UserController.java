package com.lucy.controller;

import com.lucy.bean.UserBean;
import com.lucy.model.User;
import com.lucy.repository.UserRepository;
import com.lucy.util.PasswordUtil;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@SessionScoped
public class UserController implements Serializable {

    private final UserRepository userRepository;
    private final UserBean userSessionBean;

    private String role;
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String searchTerm;

    private List<User> users;
    private List<User> filteredUsers;
    private User user = new User();
    private User tempEditedUser;

    private User selectedUser;



    private boolean showCreateForm = false;

    @Inject
    public UserController(UserRepository userRepository, @Named("userSessionBean") UserBean userSessionBean) {
        this.userRepository = userRepository;
        this.userSessionBean = userSessionBean;
    }

    @PostConstruct
    public void init() {
        loadUsers();
    }

    public void loadUsers() {
        this.users = userRepository.getAllUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<User> searchUsers() {

        users = userRepository.searchUsers(searchTerm);
        return users;
    }

    public User findUserById(String id) {
        return userRepository.getUserById(id);
    }

    public String createUser() {
        FacesContext context = FacesContext.getCurrentInstance();

        // Validate email format
        if (!isValidEmail(email)) {
            context.addMessage("adminUserForm:email", new FacesMessage("Invalid email"));
            return null;
        }

        // Validate password strength
        if (!isStrongPassword(password)) {
            context.addMessage("adminUserForm:password", new FacesMessage("Password must be at least 8 characters long and include uppercase, lowercase, and a digit"));
            return null;
        }

        try {
            this.user = new User(); // Reset the user form
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPasswordHash(PasswordUtil.hashPassword(password));
            newUser.setRole(role != null ? role : "ADMIN");
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setUpdatedAt(LocalDateTime.now());

            boolean created = userRepository.saveUser(newUser);

            if (created) {
                context.addMessage(null, new FacesMessage("User created successfully"));
                loadUsers();
                showCreateForm = false;
                return "user-list?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User may already exist", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Optionally replace with logger
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", null));
            return null;
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*");
    }

    // CRUD Operations
    public void deleteUser() {
        try {
            if (selectedUser != null) {
                userRepository.deleteUserById(selectedUser.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User deleted successfully"));
                loadUsers();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not selected", "Please select a user first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete user"));
        }
    }


    public void save() {
        if (user.getId() == null) {
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.saveUser(user);
        } else {
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.updateUser(user);
        }
        clearForm();
        loadUsers();
    }

    public void onRowEdit(RowEditEvent<User> event) {
        tempEditedUser = event.getObject(); // Store temporarily
    }

    public void confirmEdit() {
        if (tempEditedUser != null) {
            tempEditedUser.setUpdatedAt(LocalDateTime.now());
            userRepository.updateUser(tempEditedUser);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User edited successfully"));
            loadUsers(); // Refresh list
            tempEditedUser = null;
        }
    }
    public void cancelEdit() {
        this.users = userRepository.getAllUsers();
        // Optionally reload data or reset table
        tempEditedUser = null;
    }

    public void clearForm() {
        this.user = new User();
    }

    public void prepareDelete(User user) {
        this.selectedUser = user;
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
        this.confirmPassword = confirmPassword;
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }

}