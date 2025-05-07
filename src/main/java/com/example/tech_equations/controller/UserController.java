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

    public UserController() {
        this.userRepository = new UserRepository();
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
        user.role = "ADMIN";
        userRepository.saveUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteUserById(id);
    }
}
