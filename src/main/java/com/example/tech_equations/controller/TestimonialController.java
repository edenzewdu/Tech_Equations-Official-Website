//package com.example.tech_equations.controller;
//
//import com.example.tech_equations.bean.TestimonialBean;
//import com.example.tech_equations.model.Testimonial;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//@Path("/testimonials")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class TestimonialController {
//
//    @Inject
//    private TestimonialBean testimonialBean;
//
//    // Endpoint to get all testimonials
//    @GET
//    public Response getAllTestimonials() {
//        return Response.ok(testimonialBean.getAllTestimonials()).build();
//    }
//
//    // Endpoint to get a single testimonial by ID
//    @GET
//    @Path("/{id}")
//    public Response getTestimonialById(@PathParam("id") int id) {
//        Testimonial testimonial = testimonialBean.getTestimonialById(id);
//        if (testimonial != null) {
//            return Response.ok(testimonial).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).build();
//    }
//
//    // Endpoint to create a new testimonial
//    @POST
//    public Response createTestimonial(Testimonial testimonial) {
//        boolean result = testimonialBean.saveTestimonial(testimonial);
//        if (result) {
//            return Response.status(Response.Status.CREATED).build();
//        }
//        return Response.status(Response.Status.BAD_REQUEST).build();
//    }
//
//    // Endpoint to update an existing testimonial
//    @PUT
//    @Path("/{id}")
//    public Response updateTestimonial(@PathParam("id") int id, Testimonial testimonial) {
//        testimonial.setId(id);
//        boolean result = testimonialBean.updateTestimonial(testimonial);
//        if (result) {
//            return Response.ok().build();
//        }
//        return Response.status(Response.Status.BAD_REQUEST).build();
//    }
//
//    // Endpoint to delete a testimonial
//    @DELETE
//    @Path("/{id}")
//    public Response deleteTestimonial(@PathParam("id") int id) {
//        boolean result = testimonialBean.deleteTestimonial(id);
//        if (result) {
//            return Response.ok().build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).build();
//    }
//}
