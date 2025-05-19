package com.example.tech_equations.bean;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@ManagedBean
@Named( "contactBean")
@SessionScoped
public class ContactBean implements Serializable {
    private String name;
    private String email;
    private String subject;
    private String message;
    private boolean submitted = false;

    public String send() {
        // Simulate sending email (FR-MAIL-01 could be called here)
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Message sent successfully!", null));
        submitted = true;
        // Reset form
        name = email = subject = message = "";
        return null;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isSubmitted() { return submitted; }
}
