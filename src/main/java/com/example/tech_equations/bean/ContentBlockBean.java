//package com.example.tech_equations.bean;
//
//import com.example.tech_equations.model.ContentBlock;
//import com.example.tech_equations.repository.ContentBlockRepository;
//
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.inject.Inject;
//import java.util.List;
//
//@RequestScoped
//public class ContentBlockBean {
//
//    @Inject
//    private ContentBlockRepository contentBlockRepository; // CDI injection of the repository
//
//    // Save a new content block
//    public boolean saveContentBlock(ContentBlock contentBlock) {
//        return contentBlockRepository.saveContentBlock(contentBlock);
//    }
//
//    // Get all content blocks
//    public List<ContentBlock> getAllContentBlocks() {
//        return contentBlockRepository.getAllContentBlocks();
//    }
//
//    // Get content block by ID
//    public ContentBlock getContentBlockById(int id) {
//        return contentBlockRepository.getContentBlockById(id);
//    }
//
//    // Update content block
//    public boolean updateContentBlock(ContentBlock contentBlock) {
//        return contentBlockRepository.updateContentBlock(contentBlock);
//    }
//
//    // Delete content block
//    public boolean deleteContentBlock(int id) {
//        return contentBlockRepository.deleteContentBlock(id);
//    }
//}
