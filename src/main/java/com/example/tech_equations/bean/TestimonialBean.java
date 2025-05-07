//package com.example.tech_equations.bean;
//
//import com.example.tech_equations.model.Testimonial;
//import com.example.tech_equations.repository.TestimonialRepository;
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.inject.Inject;
//
//import java.util.List;
//
//@RequestScoped
//public class TestimonialBean {
//
//    @Inject
//    private TestimonialRepository testimonialRepository;
//
//    // Retrieve all testimonials
//    public List<Testimonial> getAllTestimonials() {
//        return testimonialRepository.findAll();
//    }
//
//    // Retrieve a specific testimonial by ID
//    public Testimonial getTestimonialById(int id) {
//        return testimonialRepository.findById(id);
//    }
//
//    // Add a new testimonial
//    public boolean saveTestimonial(Testimonial testimonial) {
//        return testimonialRepository.save(testimonial);
//    }
//
//    // Update an existing testimonial
//    public boolean updateTestimonial(Testimonial testimonial) {
//        return testimonialRepository.update(testimonial);
//    }
//
//    // Delete a testimonial by ID
//    public boolean deleteTestimonial(int id) {
//        return testimonialRepository.delete(id);
//    }
//}
