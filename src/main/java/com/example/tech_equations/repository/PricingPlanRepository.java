//package com.example.tech_equations.repository;
//
//import com.example.tech_equations.model.PricingPlan;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//
//import java.util.List;
//
//@Transactional
//public class PricingPlanRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public PricingPlan save(PricingPlan pricingPlan) {
//        if (pricingPlan.getId() == 0) {
//            entityManager.persist(pricingPlan);
//        } else {
//            entityManager.merge(pricingPlan);
//        }
//        return pricingPlan;
//    }
//
//    public boolean deleteById(int id) {
//        PricingPlan pricingPlan = entityManager.find(PricingPlan.class, id);
//        if (pricingPlan != null) {
//            entityManager.remove(pricingPlan);
//            return true;
//        }
//        return false;
//    }
//
//    public PricingPlan findById(int id) {
//        return entityManager.find(PricingPlan.class, id);
//    }
//
//    public List<PricingPlan> findAll() {
//        return entityManager.createQuery("SELECT p FROM PricingPlan p", PricingPlan.class)
//                .getResultList();
//    }
//}
