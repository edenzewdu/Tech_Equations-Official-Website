package com.example.tech_equations.service;// Placeholder for AuthService.java


import com.example.tech_equations.model.User;
import com.example.tech_equations.repository.UserRepository;
import com.example.tech_equations.util.PasswordUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AuthService implements Serializable {
    private UserRepository userRepository = new UserRepository();


    // Login logic
    public User login(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }


    // Signup logic
    public boolean signup(String email, String password, String name) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(PasswordUtil.hashPassword(password));
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.saveUser(user);
    }

}


