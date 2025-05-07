//package com.example.tech_equations.controller;
//
//import com.example.tech_equations.bean.ContactBean;
//import com.example.tech_equations.model.Contact;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//import java.util.List;
//
//@Path("/contacts")
//public class ContactController {
//
//    @Inject
//    private ContactBean contactBean;
//
//    // Save new contact
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response saveContact(Contact contact) {
//        boolean isSaved = contactBean.saveContact(contact);
//        if (isSaved) {
//            return Response.status(Response.Status.CREATED).entity(contact).build();
//        } else {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//    }
//
//    // Get all contacts
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Contact> getAllContacts() {
//        return contactBean.getAllContacts();
//    }
//
//    // Get contact by ID
//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getContactById(@PathParam("id") int id) {
//        Contact contact = contactBean.getContactById(id);
//        if (contact != null) {
//            return Response.ok(contact).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }
//
//    // Update contact
//    @PUT
//    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateContact(@PathParam("id") int id, Contact contact) {
//        contact.setId(id);
//        boolean isUpdated = contactBean.updateContact(contact);
//        if (isUpdated) {
//            return Response.ok(contact).build();
//        } else {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//    }
//
//    // Delete contact
//    @DELETE
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteContact(@PathParam("id") int id) {
//        boolean isDeleted = contactBean.deleteContact(id);
//        if (isDeleted) {
//            return Response.status(Response.Status.NO_CONTENT).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }
//}
