//package com.example.tech_equations.bean;
//
//import com.example.tech_equations.model.Partner;
//import com.example.tech_equations.repository.PartnerRepository;
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.inject.Inject;
//
//import java.util.List;
//
//@RequestScoped // @RequestScoped to make the bean request-scoped
//public class PartnerBean {
//
//    @Inject
//    private PartnerRepository partnerRepository; // CDI injection of the repository
//
//    /**
//     * Save a new partner.
//     * @param partner The partner to be saved.
//     * @return true if the partner was saved successfully, false otherwise.
//     */
//    public boolean savePartner(Partner partner) {
//        try {
//            return partnerRepository.savePartner(partner);
//        } catch (Exception e) {
//            // Log the exception and handle accordingly
//            e.printStackTrace(); // Placeholder for logging
//            return false;
//        }
//    }
//
//    /**
//     * Update an existing partner.
//     * @param partner The partner to be updated.
//     * @return true if the partner was updated successfully, false otherwise.
//     */
//    public boolean updatePartner(Partner partner) {
//        try {
//            return partnerRepository.updatePartner(partner);
//        } catch (Exception e) {
//            // Log the exception and handle accordingly
//            e.printStackTrace(); // Placeholder for logging
//            return false;
//        }
//    }
//
//    /**
//     * Delete a partner by ID.
//     * @param id The ID of the partner to be deleted.
//     * @return true if the partner was deleted successfully, false otherwise.
//     */
//    public boolean deletePartner(int id) {
//        try {
//            return partnerRepository.deletePartner(id);
//        } catch (Exception e) {
//            // Log the exception and handle accordingly
//            e.printStackTrace(); // Placeholder for logging
//            return false;
//        }
//    }
//
//    /**
//     * Get a partner by ID.
//     * @param id The ID of the partner to retrieve.
//     * @return The partner, or null if not found.
//     */
//    public Partner getPartnerById(int id) {
//        try {
//            return partnerRepository.getPartnerById(id);
//        } catch (Exception e) {
//            // Log the exception and handle accordingly
//            e.printStackTrace(); // Placeholder for logging
//            return null;
//        }
//    }
//
//    /**
//     * Get all partners.
//     * @return List of all partners.
//     */
//    public List<Partner> getAllPartners() {
//        try {
//            return partnerRepository.getAllPartners();
//        } catch (Exception e) {
//            // Log the exception and handle accordingly
//            e.printStackTrace(); // Placeholder for logging
//            return null;
//        }
//    }
//}
