//package com.example.tech_equations.repository;
//
//import com.example.tech_equations.model.Partner;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import java.util.List;
//
//public class PartnerRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    // Save partner
//    @Transactional
//    public boolean savePartner(Partner partner) {
//        try {
//            entityManager.persist(partner);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Get all partners
//    public List<Partner> getAllPartners() {
//        return entityManager.createQuery("FROM Partner", Partner.class).getResultList();
//    }
//
//    // Get partner by ID
//    public Partner getPartnerById(int id) {
//        return entityManager.find(Partner.class, id);
//    }
//
//    // Update partner
//    @Transactional
//    public boolean updatePartner(Partner partner) {
//        try {
//            entityManager.merge(partner);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Delete partner
//    @Transactional
//    public boolean deletePartner(int id) {
//        try {
//            Partner partner = getPartnerById(id);
//            if (partner != null) {
//                entityManager.remove(partner);
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//}
