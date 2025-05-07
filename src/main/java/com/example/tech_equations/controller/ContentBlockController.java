//package com.example.tech_equations.controller;
//
//import com.example.tech_equations.bean.ContentBlockBean;
//import com.example.tech_equations.model.ContentBlock;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//import java.util.List;
//
//@Path("/content-blocks")
//public class ContentBlockController {
//
//    @Inject
//    private ContentBlockBean contentBlockBean;
//
//    // Save new content block
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response saveContentBlock(ContentBlock contentBlock) {
//        boolean isSaved = contentBlockBean.saveContentBlock(contentBlock);
//        if (isSaved) {
//            return Response.status(Response.Status.CREATED).entity(contentBlock).build();
//        } else {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//    }
//
//    // Get all content blocks
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<ContentBlock> getAllContentBlocks() {
//        return contentBlockBean.getAllContentBlocks();
//    }
//
//    // Get content block by ID
//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getContentBlockById(@PathParam("id") int id) {
//        ContentBlock contentBlock = contentBlockBean.getContentBlockById(id);
//        if (contentBlock != null) {
//            return Response.ok(contentBlock).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }
//
//    // Update content block
//    @PUT
//    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateContentBlock(@PathParam("id") int id, ContentBlock contentBlock) {
//        contentBlock.setId(id);
//        boolean isUpdated = contentBlockBean.updateContentBlock(contentBlock);
//        if (isUpdated) {
//            return Response.ok(contentBlock).build();
//        } else {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//    }
//
//    // Delete content block
//    @DELETE
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteContentBlock(@PathParam("id") int id) {
//        boolean isDeleted = contentBlockBean.deleteContentBlock(id);
//        if (isDeleted) {
//            return Response.status(Response.Status.NO_CONTENT).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }
//}
