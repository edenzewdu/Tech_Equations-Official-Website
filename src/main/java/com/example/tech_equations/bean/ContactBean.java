//package com.example.tech_equations.bean;
//
//import com.example.tech_equations.model.Contact;
//import com.example.tech_equations.repository.ContactRepository;
//
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.inject.Inject;
//import java.util.List;
//
//@RequestScoped
//public class ContactBean {
//
//    @Inject
//    private ContactRepository contactRepository; // CDI injection of the repository
//
//    // Save a contact
//    public boolean saveContact(Contact contact) {
//        return contactRepository.saveContact(contact);
//    }
//
//    // Get all contacts
//    public List<Contact> getAllContacts() {
//        return contactRepository.getAllContacts();
//    }
//
//    // Get contact by ID
//    public Contact getContactById(int id) {
//        return contactRepository.getContactById(id);
//    }
//
//    // Update contact
//    public boolean updateContact(Contact contact) {
//        return contactRepository.updateContact(contact);
//    }
//
//    // Delete contact
//    public boolean deleteContact(int id) {
//        return contactRepository.deleteContact(id);
//    }
//}
