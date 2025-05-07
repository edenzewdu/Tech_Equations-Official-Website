package com.example.tech_equations.controller;

import com.example.tech_equations.model.User;
import com.example.tech_equations.repository.UserRepository;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ManagedBean
@ApplicationScoped
public class UserController implements Serializable {

    @Inject
    private UserRepository userRepository;

    private User user = new User();
    private String searchTerm;
    private List<User> filteredUsers;

    public UserController() {
        this.userRepository = new UserRepository();
        loadUsers(); // Load initial list
    }
    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }

    public List<User> searchUsers(String term) {
        return userRepository.searchUsers(term);
    }

    public User getUser(String id) {
        return userRepository.getUserById(id);
    }

    public void createUser(User user) {
        userRepository.saveUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteUserById(id);
    }

        public void loadUsers() {
            this.filteredUsers = userRepository.getAllUsers();
        }

        public void performSearch() {
            if (searchTerm == null || searchTerm.isEmpty()) {
                loadUsers();
            } else {
                this.filteredUsers = userRepository.searchUsers(searchTerm);
            }
        }

        public void save() {
            if (user.getId() == null) {
                userRepository.saveUser(user);
            } else {
                userRepository.updateUser(user);
            }
            clearForm();
            loadUsers();
        }

        public void delete(String id) {
            userRepository.deleteUserById(id);
            loadUsers();
        }

        public void edit(User selectedUser) {
            this.user = selectedUser;
        }

        public void clearForm() {
            this.user = new User(); // reset form
        }

        // Getters and setters
        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getSearchTerm() {
            return searchTerm;
        }

        public void setSearchTerm(String searchTerm) {
            this.searchTerm = searchTerm;
        }

        public List<User> getFilteredUsers() {
            return filteredUsers;
        }

        public void setFilteredUsers(List<User> filteredUsers) {
            this.filteredUsers = filteredUsers;
        }

}
