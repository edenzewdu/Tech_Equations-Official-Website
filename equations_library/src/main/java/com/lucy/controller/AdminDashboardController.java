package com.lucy.controller;

import com.lucy.repository.UserRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;

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
