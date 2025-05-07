//package com.example.tech_equations.repository;
//
//import com.example.tech_equations.model.FAQ;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import java.util.List;
//
//public class FAQRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    // Get all FAQs
//    public List<FAQ> getAllFAQs() {
//        return entityManager.createQuery("SELECT f FROM FAQ f", FAQ.class).getResultList();
//    }
//
//    // Get FAQ by ID
//    public FAQ getFAQById(int id) {
//        return entityManager.find(FAQ.class, id);
//    }
//
//    // Save new FAQ
//    @Transactional
//    public boolean saveFAQ(FAQ faq) {
//        try {
//            entityManager.persist(faq);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Update existing FAQ
//    @Transactional
//    public boolean updateFAQ(FAQ faq) {
//        try {
//            entityManager.merge(faq);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Delete FAQ
//    @Transactional
//    public boolean deleteFAQ(int id) {
//        try {
//            FAQ faq = getFAQById(id);
//            if (faq != null) {
//                entityManager.remove(faq);
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//}
