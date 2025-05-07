//package com.example.tech_equations.repository;
//
//import com.example.tech_equations.model.ContentBlock;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import java.util.List;
//
//public class ContentBlockRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    // Save content block
//    @Transactional
//    public boolean saveContentBlock(ContentBlock contentBlock) {
//        try {
//            entityManager.persist(contentBlock);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Get all content blocks
//    public List<ContentBlock> getAllContentBlocks() {
//        return entityManager.createQuery("SELECT c FROM ContentBlock c", ContentBlock.class).getResultList();
//    }
//
//    // Get content block by ID
//    public ContentBlock getContentBlockById(int id) {
//        return entityManager.find(ContentBlock.class, id);
//    }
//
//    // Update content block
//    @Transactional
//    public boolean updateContentBlock(ContentBlock contentBlock) {
//        try {
//            entityManager.merge(contentBlock);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Delete content block
//    @Transactional
//    public boolean deleteContentBlock(int id) {
//        try {
//            ContentBlock contentBlock = getContentBlockById(id);
//            if (contentBlock != null) {
//                entityManager.remove(contentBlock);
//                return true;
//            }
//            return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
