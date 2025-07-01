package com.web.service;

import com.web.entity.RememberedDevice;

import com.web.entity.Users;
import com.web.jsf.PasswordUtil;
import com.web.jsf.SessionManager;
import com.web.session.RememberedDevicesFacade;
import com.web.session.UsersFacade;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
import java.util.UUID;

@ApplicationScoped
public class AuthService implements Serializable {

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Inject
    private SessionManager sessionManager;

    @EJB
    private UsersFacade ejbFacade;

    public UsersFacade getEjbFacade() {
        return ejbFacade;
    }
    @EJB
    private RememberedDevicesFacade rememberedDeviceFacade;


    public Users login(String email, String password) {
        try {
            String jpql = "SELECT u FROM Users u WHERE u.email = :email";
            Users user = em.createQuery(jpql, Users.class)
                    .setParameter("email", email)
                    .getSingleResult();

            if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                sessionManager.setCurrentUser(user); // Store in session
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean signup(String email, String password, String name) {
        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(PasswordUtil.hashPassword(password));
        user.setRole1("USER");
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        return getEjbFacade().create1(user);
    }

    public void storeRememberedDevice(String userId, String ipAddress, String userAgent, String deviceInfo, String browserInfo) {
try{
        RememberedDevice device = new RememberedDevice();
        device.setUserId(userId);
        device.setIpAddress(ipAddress);
        device.setUserAgent(userAgent);
        device.setDeviceInfo(deviceInfo);
        device.setBrowserInfo(browserInfo);

        boolean saved = rememberedDeviceFacade.create1(device);

    } catch (Exception e) {
        e.printStackTrace(); // You should use a logger in real apps
    }
    }

}
