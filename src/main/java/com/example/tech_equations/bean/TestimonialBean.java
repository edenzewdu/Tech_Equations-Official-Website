package com.example.tech_equations.bean;

import com.example.tech_equations.model.Testimonial;
import com.example.tech_equations.repository.TestimonialRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("testimonialBean")
@SessionScoped
public class TestimonialBean implements Serializable {

        @Inject
        private TestimonialRepository repository;




}
