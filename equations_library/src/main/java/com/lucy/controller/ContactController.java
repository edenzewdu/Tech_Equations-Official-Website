package com.lucy.controller;

import com.lucy.bean.UserBean;
import com.lucy.model.Contact;
import com.lucy.model.User;
import com.lucy.repository.ContactRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;

@Named("contactController")
@SessionScoped
public class ContactController implements Serializable {

    private final ContactRepository contactRepository;
    private final UserBean userSessionBean;

    private String name;
    private String email;
    private String subject;
    private String message;
    private String searchTerm;
    private boolean submitted = false;

    private List<Contact> contacts;
    private Contact contact = new Contact();

    @Inject
    public ContactController(ContactRepository contactRepository, @Named("userSessionBean") UserBean userSessionBean) {
        this.contactRepository = contactRepository;
        this.userSessionBean = userSessionBean;
    }

    @PostConstruct
    public void init() {
        loadContacts();
    }

    public void loadContacts() {
        this.contacts = contactRepository.getAllContacts();
    }

    public String submitContact() {
        FacesContext context = FacesContext.getCurrentInstance();

        // Validate email format
        if (!isValidEmail(email)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email address", null));
            return null;
        }

        if (message == null || message.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Message is required", null));
            return null;
        }

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            Contact newContact = new Contact();
            newContact.setId(UUID.randomUUID().toString());
            newContact.setName(name);
            newContact.setEmail(email);
            newContact.setSubject(subject);
            newContact.setMessage(message);
            newContact.setRead(false);
            newContact.setSubmittedAt(LocalDateTime.now());


        contactRepository.saveContact(newContact);
            context.addMessage(null, new FacesMessage("Message sent successfully"));
            clearForm();
            return "contact-success?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to submit message", null));
            return null;
        }
    }

    public List<Contact> searchContact() {

        contacts = contactRepository.searchContact(searchTerm);
        return contacts;
    }
    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void clearForm() {
        this.name = "";
        this.email = "";
        this.subject = "";
        this.message = "";
    }

    public void toggleRead(Contact contact) {
        // Toggle the read status of the contact and save/update in DB
        contact.setRead(!contact.getRead());
        contactRepository.markAsRead(contact.getId());
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<Contact> getContacts() { return contacts; }
    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact; }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }
}
