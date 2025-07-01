package com.web.bl;

import com.web.jsf.AdminDashboardController;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

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
