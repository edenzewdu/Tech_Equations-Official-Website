//package com.example.tech_equations.controller;
//
//import com.example.tech_equations.bean.PartnerBean;
//import com.example.tech_equations.model.Partner;
//
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//import java.util.List;
//
//@Path("/partners") // Base URI for partner-related endpoints
//@Produces(MediaType.APPLICATION_JSON) // Default response type as JSON
//@Consumes(MediaType.APPLICATION_JSON) // Default request type as JSON
//public class PartnerController {
//
//    @Inject
//    private PartnerBean partnerBean; // Inject the PartnerBean to interact with the service layer
//
//    /**
//     * Save a new partner.
//     * @param partner The partner to be saved.
//     * @return Response indicating the result of the operation.
//     */
//    @POST
//    @Path("/save")
//    public Response savePartner(Partner partner) {
//        boolean success = partnerBean.savePartner(partner);
//        if (success) {
//            return Response.status(Response.Status.CREATED).entity("Partner created successfully").build();
//        } else {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Error saving partner").build();
//        }
//    }
//
//    /**
//     * Update an existing partner.
//     * @param partner The partner to be updated.
//     * @return Response indicating the result of the operation.
//     */
//    @PUT
//    @Path("/update")
//    public Response updatePartner(Partner partner) {
//        boolean success = partnerBean.updatePartner(partner);
//        if (success) {
//            return Response.status(Response.Status.OK).entity("Partner updated successfully").build();
//        } else {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Error updating partner").build();
//        }
//    }
//
//    /**
//     * Delete a partner by ID.
//     * @param id The ID of the partner to be deleted.
//     * @return Response indicating the result of the operation.
//     */
//    @DELETE
//    @Path("/delete/{id}")
//    public Response deletePartner(@PathParam("id") int id) {
//        boolean success = partnerBean.deletePartner(id);
//        if (success) {
//            return Response.status(Response.Status.NO_CONTENT).entity("Partner deleted successfully").build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).entity("Partner not found").build();
//        }
//    }
//
//    /**
//     * Get a partner by ID.
//     * @param id The ID of the partner to retrieve.
//     * @return Response containing the partner or error message.
//     */
//    @GET
//    @Path("/{id}")
//    public Response getPartnerById(@PathParam("id") int id) {
//        Partner partner = partnerBean.getPartnerById(id);
//        if (partner != null) {
//            return Response.status(Response.Status.OK).entity(partner).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).entity("Partner not found").build();
//        }
//    }
//
//    /**
//     * Get all partners.
//     * @return List of all partners.
//     */
//    @GET
//    @Path("/all")
//    public Response getAllPartners() {
//        List<Partner> partners = partnerBean.getAllPartners();
//        if (partners.isEmpty()) {
//            return Response.status(Response.Status.NO_CONTENT).entity("No partners found").build();
//        }
//        return Response.status(Response.Status.OK).entity(partners).build();
//    }
//}
