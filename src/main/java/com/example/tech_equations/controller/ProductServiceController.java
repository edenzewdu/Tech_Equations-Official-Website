//package com.example.tech_equations.controller;
//
//import com.example.tech_equations.bean.ProductServiceBean;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//@Path("/productservice")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class ProductServiceController {
//
//    @Inject
//    private ProductServiceBean productServiceBean;
//
//    // Endpoint to get product/service details
//    @GET
//    public Response getProductServiceDetails() {
//        // Return all details in a structured format
//        ProductServiceDetails details = new ProductServiceDetails();
//        details.setName(productServiceBean.getProductName());
//        details.setDescription(productServiceBean.getProductDescription());
//        details.setFeatures(productServiceBean.getProductFeatures());
//        details.setImage(productServiceBean.getProductImages());
//        details.setPrice(productServiceBean.getProductPrice());
//        details.setCallToAction(productServiceBean.getCallToAction());
//
//        return Response.ok(details).build();
//    }
//
//    // Inner class for product/service details
//    public static class ProductServiceDetails {
//        private String name;
//        private String description;
//        private String[] features;
//        private String image;
//        private double price;
//        private String callToAction;
//
//        // Getters and Setters
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public String[] getFeatures() {
//            return features;
//        }
//
//        public void setFeatures(String[] features) {
//            this.features = features;
//        }
//
//        public String getImage() {
//            return image;
//        }
//
//        public void setImage(String image) {
//            this.image = image;
//        }
//
//        public double getPrice() {
//            return price;
//        }
//
//        public void setPrice(double price) {
//            this.price = price;
//        }
//
//        public String getCallToAction() {
//            return callToAction;
//        }
//
//        public void setCallToAction(String callToAction) {
//            this.callToAction = callToAction;
//        }
//    }
//}
