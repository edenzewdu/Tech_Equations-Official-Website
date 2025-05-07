//package com.example.tech_equations.repository;
//
//import com.example.tech_equations.model.Testimonial;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//
//import java.util.List;
//
//@ApplicationScoped
//public class TestimonialRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    // Find all testimonials
//    public List<Testimonial> findAll() {
//        return entityManager.createQuery("SELECT t FROM Testimonial t", Testimonial.class).getResultList();
//    }
//
//    // Find a testimonial by ID
//    public Testimonial findById(int id) {
//        return entityManager.find(Testimonial.class, id);
//    }
//
//    // Save a new testimonial
//    public boolean save(Testimonial testimonial) {
//        try {
//            entityManager.persist(testimonial);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Update an existing testimonial
//    public boolean update(Testimonial testimonial) {
//        try {
//            entityManager.merge(testimonial);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Delete a testimonial by ID
//    public boolean delete(int id) {
//        try {
//            Testimonial testimonial = findById(id);
//            if (testimonial != null) {
//                entityManager.remove(testimonial);
//                return true;
//            }
//            return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
