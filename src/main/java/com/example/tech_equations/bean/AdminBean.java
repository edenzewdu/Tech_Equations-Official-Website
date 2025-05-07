package com.example.tech_equations.bean;

import com.example.tech_equations.controller.AdminDashboardController;
import com.example.tech_equations.controller.AuthController;
import com.example.tech_equations.model.User;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named("adminBean")
@RequestScoped

public class AdminBean {
    @Inject
    private AdminDashboardController adminController;

    // Get statistics for the dashboard
    public long getUserCount() {
        return adminController.getUserCount();
    }

    public long getContactsToday() {
        return adminController.getContactsToday();
    }

    public long getTotalTestimonials() {
        return adminController.getTotalTestimonials();
    }


}
