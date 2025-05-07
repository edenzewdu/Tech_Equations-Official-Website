//package com.example.tech_equations.repository;
//
//import com.example.tech_equations.model.Contact;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import java.util.List;
//
//public class ContactRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    // Save contact
//    @Transactional
//    public boolean saveContact(Contact contact) {
//        try {
//            entityManager.persist(contact);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Get all contacts
//    public List<Contact> getAllContacts() {
//        return entityManager.createQuery("SELECT c FROM Contact c", Contact.class).getResultList();
//    }
//
//    // Get contact by ID
//    public Contact getContactById(int id) {
//        return entityManager.find(Contact.class, id);
//    }
//
//    // Update contact
//    @Transactional
//    public boolean updateContact(Contact contact) {
//        try {
//            entityManager.merge(contact);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Delete contact
//    @Transactional
//    public boolean deleteContact(int id) {
//        try {
//            Contact contact = getContactById(id);
//            if (contact != null) {
//                entityManager.remove(contact);
//                return true;
//            }
//            return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
