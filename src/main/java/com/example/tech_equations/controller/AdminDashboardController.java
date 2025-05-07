package com.example.tech_equations.controller;

import com.example.tech_equations.bean.UserBean;
import com.example.tech_equations.model.User;
import com.example.tech_equations.repository.UserRepository;
import com.example.tech_equations.service.AuthService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named("adminController")
@RequestScoped
public class AdminDashboardController implements Serializable {

    private final UserRepository userRepository = new UserRepository();

    // Count metrics
    public long getUserCount() {
        return userRepository.getUserCount();
    }

    public long getContactsToday() {
        return userRepository.getContactCountToday();
    }

    public long getTotalTestimonials() {
        return userRepository.getTestimonialCount();
    }

    // CRUD operations

}
