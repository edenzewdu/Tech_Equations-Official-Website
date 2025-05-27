package com.lucy.controller;

import com.lucy.model.FAQ;
import com.lucy.model.User;
import com.lucy.repository.FAQRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Named("faqController")
@SessionScoped
public class FAQController implements Serializable {

    private final FAQRepository faqRepository;

    private String question;
    private String answer;
    private String category;
    private Integer displayOrder;
    private String searchTerm;

    private List<FAQ> faqs;
    private List<FAQ> filteredFaqs;
    private FAQ faq = new FAQ();
    private FAQ tempEditedFaq;

    private FAQ selectedFaq;
    private boolean showCreateForm = false;

    @Inject
    public FAQController(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @PostConstruct
    public void init() {
        loadFaqs();
    }

    public void loadFaqs() {
        this.faqs = faqRepository.getAllFAQs();
    }

    public List<FAQ> getFaqs() {
        return faqs;
    }

    public String createFaq() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            FAQ newFaq = new FAQ();
            newFaq.setId(UUID.randomUUID().toString());
            newFaq.setQuestion(question);
            newFaq.setAnswer(answer);
            newFaq.setCategory(category);
            newFaq.setDisplayOrder(displayOrder != null ? displayOrder : 0);
            newFaq.setCreatedAt(LocalDateTime.now());
            newFaq.setUpdatedAt(LocalDateTime.now());

            boolean created = faqRepository.saveFAQ(newFaq);
            if (created) {
                context.addMessage(null, new FacesMessage("FAQ created successfully"));
                loadFaqs();
                clearForm();
                showCreateForm = false;
                return "faq-list?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Creation failed", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", null));
            return null;
        }
    }

    public void deleteFaq() {
        try {
            if (selectedFaq != null) {
                faqRepository.deleteFAQById(selectedFaq.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("FAQ deleted successfully"));
                loadFaqs();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAQ not selected", "Please select a FAQ first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete FAQ"));
        }
    }

    public void save() {
        if (faq.getId() == null) {
            faq.setId(UUID.randomUUID().toString());
            faq.setCreatedAt(LocalDateTime.now());
        }
        faq.setUpdatedAt(LocalDateTime.now());
        faqRepository.saveFAQ(faq);
        clearForm();
        loadFaqs();
    }

    public void onRowEdit(RowEditEvent<FAQ> event) {
        tempEditedFaq = event.getObject();
    }

    public void confirmEdit() {
        if (tempEditedFaq != null) {
            tempEditedFaq.setUpdatedAt(LocalDateTime.now());
            faqRepository.updateFAQ(tempEditedFaq);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("FAQ edited successfully"));
            loadFaqs();
            tempEditedFaq = null;
        }
    }

    public void cancelEdit() {
        loadFaqs();
        tempEditedFaq = null;
    }

    public void prepareDelete(FAQ faq) {
        this.selectedFaq = faq;
    }

    public void clearForm() {
        this.faq = new FAQ();
        this.question = null;
        this.answer = null;
        this.category = null;
        this.displayOrder = null;
    }

    // Getters and Setters
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public List<String> getFaqCategories() {
        return faqRepository.getDistinctCategories();
    }

    public List<FAQ> searchUsers() {

        faqs = faqRepository.searchFAQs(searchTerm);
        return faqs;
    }
    public List<FAQ> getFaqsByCategory(String category) {
        return faqRepository.getFaqsByCategory(category);
    }

    public FAQ getFaq() { return faq; }
    public void setFaq(FAQ FAQ) { this.faq = faq; }

    public List<FAQ> getFilteredFaqs() { return filteredFaqs; }
    public void setFilteredFaqs(List<FAQ> filteredFaqs) { this.filteredFaqs = filteredFaqs; }

    public boolean isShowCreateForm() { return showCreateForm; }
    public void setShowCreateForm(boolean showCreateForm) { this.showCreateForm = showCreateForm; }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }

}
