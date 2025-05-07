//package com.example.tech_equations.bean;
//
//import com.example.tech_equations.model.PricingPlan;
//import com.example.tech_equations.repository.PricingPlanRepository;
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.inject.Inject;
//
//import java.util.List;
//
//@RequestScoped // This ensures that the bean is created per request
//public class PricingPlanBean {
//
//    @Inject
//    private PricingPlanRepository pricingPlanRepository; // CDI injection of the repository
//
//    // Method to save or update a PricingPlan
//    public PricingPlan saveOrUpdatePricingPlan(PricingPlan pricingPlan) {
//        return pricingPlanRepository.save(pricingPlan);
//    }
//
//    // Method to delete a PricingPlan
//    public boolean deletePricingPlan(int id) {
//        return pricingPlanRepository.deleteById(id);
//    }
//
//    // Method to retrieve a PricingPlan by ID
//    public PricingPlan getPricingPlanById(int id) {
//        return pricingPlanRepository.findById(id);
//    }
//
//    // Method to get all PricingPlans
//    public List<PricingPlan> getAllPricingPlans() {
//        return pricingPlanRepository.findAll();
//    }
//}
