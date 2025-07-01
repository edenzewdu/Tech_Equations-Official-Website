package com.web.jsf;

import com.web.entity.ContentBlocks;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Named("adminController")
@RequestScoped
public class AdminDashboardController implements Serializable {
    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;


    // Count metrics
    public long getUserCount() {

        return em.createQuery("SELECT COUNT(u) FROM Users u", Long.class)
                .getSingleResult();
    }

    public long getContactsToday() {

        // Get current day range
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
        // Convert to java.util.Date
        ZoneId zone = ZoneId.systemDefault();
        Date startDate = Date.from(startOfDay.atZone(zone).toInstant());
        Date endDate = Date.from(endOfDay.atZone(zone).toInstant());

        String jpql = "SELECT COUNT(c) FROM Contacts c " +
                "WHERE c.submittedAt >= :start AND c.submittedAt < :end";

        return em.createQuery(jpql, Long.class)
                .setParameter("start", startDate)
                .setParameter("end", endDate)
                .getSingleResult();
    }

    public long getTotalTestimonials() {

        return em.createQuery("SELECT COUNT(t) FROM Testimonials t", Long.class)
                .getSingleResult();
    }

    // CRUD operations

}
