//package com.example.tech_equations.controller;
//
//import com.example.tech_equations.bean.PricingPlanBean;
//import com.example.tech_equations.model.PricingPlan;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//import java.util.List;
//
//@Path("/pricingplans")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class PricingPlanController {
//
//    @Inject
//    private PricingPlanBean pricingPlanBean; // CDI injection of the PricingPlanBean
//
//    // Endpoint to create or update a PricingPlan
//    @POST
//    public Response createOrUpdatePricingPlan(PricingPlan pricingPlan) {
//        PricingPlan savedPlan = pricingPlanBean.saveOrUpdatePricingPlan(pricingPlan);
//        return Response.status(Response.Status.CREATED).entity(savedPlan).build();
//    }
//
//    // Endpoint to delete a PricingPlan by ID
//    @DELETE
//    @Path("/{id}")
//    public Response deletePricingPlan(@PathParam("id") int id) {
//        boolean success = pricingPlanBean.deletePricingPlan(id);
//        if (success) {
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).build();
//    }
//
//    // Endpoint to get a PricingPlan by ID
//    @GET
//    @Path("/{id}")
//    public Response getPricingPlanById(@PathParam("id") int id) {
//        PricingPlan pricingPlan = pricingPlanBean.getPricingPlanById(id);
//        if (pricingPlan != null) {
//            return Response.ok(pricingPlan).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).build();
//    }
//
//    // Endpoint to get all PricingPlans
//    @GET
//    public Response getAllPricingPlans() {
//        List<PricingPlan> pricingPlans = pricingPlanBean.getAllPricingPlans();
//        return Response.ok(pricingPlans).build();
//    }
//}
