package com.lucy.controller;

import com.lucy.model.ContentBlock;
import com.lucy.model.User;
import com.lucy.repository.ContentBlockRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.util.List;

@Named("contentBlockController")
@ApplicationScoped
public class ContentBlockController implements Serializable {

    private final ContentBlockRepository contentBlockRepository;

    private String id;
    private String content;
    private String searchTerm;

    private ContentBlock tempEditedBlock;
    private ContentBlock selectedBlock;

    private List<ContentBlock> blocks;
    private List<ContentBlock> filteredBlocks;
    private ContentBlock block = new ContentBlock();

    @Inject
    public ContentBlockController(ContentBlockRepository contentBlockRepository) {
        this.contentBlockRepository = contentBlockRepository;
    }

    @PostConstruct
    public void init() {
        loadBlocks();
    }

    public void loadBlocks() {
        this.blocks = contentBlockRepository.getAllContentBlocks();
    }

    public List<ContentBlock> getBlocks() {
        return blocks;
    }

    public List<ContentBlock> searchBlocks() {
        blocks = contentBlockRepository.searchBlocks(searchTerm);
        return blocks;
    }

    public String getBlockById(String id) {
        ContentBlock block = contentBlockRepository.getContentBlockById(id);
        return block != null ? block.getContent() : "";
    }
    public String createBlock() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (id == null || content == null || id.isEmpty() || content.isEmpty()) {
            return "Error: ID and content are required";
        }

        try {
            ContentBlock newBlock = new ContentBlock();
            newBlock.setId(id);
            newBlock.setContent(content);

            boolean created = contentBlockRepository.saveContentBlock(newBlock);

            if (created) {
                context.addMessage(null, new FacesMessage("Content block created successfully"));
                loadBlocks();
                return "content-blocks?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Block creation failed", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unexpected error";
        }
    }

    public void deleteBlock() {
        try {
            if (selectedBlock != null) {
                contentBlockRepository.deleteContentBlock(selectedBlock.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Block deleted successfully"));
                loadBlocks();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No block selected", "Please select a block first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete block"));
        }
    }

    public void save() {
        if (block.getId() == null || block.getId().isEmpty()) {
            return;
        }

        if (contentBlockRepository.getContentBlockById(block.getId()) == null) {
            contentBlockRepository.saveContentBlock(block);
        } else {
            contentBlockRepository.updateContentBlock(block);
        }
        clearForm();
        loadBlocks();
    }

    public void onRowEdit(RowEditEvent<ContentBlock> event) {
        tempEditedBlock = event.getObject();
    }

    public void confirmEdit() {
        if (tempEditedBlock != null) {
            contentBlockRepository.updateContentBlock(tempEditedBlock);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Block edited successfully"));
            loadBlocks();
            tempEditedBlock = null;
        }
    }

    public void cancelEdit() {
        this.blocks = contentBlockRepository.getAllContentBlocks();
        tempEditedBlock = null;
    }

    public void clearForm() {
        this.block = new ContentBlock();
    }

    public void prepareDelete(ContentBlock block) {
        this.selectedBlock = block;
    }

    public void edit(ContentBlock selectedBlock) {
        this.block = selectedBlock;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public ContentBlock getBlock() { return block; }
    public void setBlock(ContentBlock block) { this.block = block; }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }

    public List<ContentBlock> getFilteredBlocks() { return filteredBlocks; }
    public void setFilteredBlocks(List<ContentBlock> filteredBlocks) { this.filteredBlocks = filteredBlocks; }

    public ContentBlock getSelectedBlock() { return selectedBlock; }
    public void setSelectedBlock(ContentBlock selectedBlock) { this.selectedBlock = selectedBlock; }
}
