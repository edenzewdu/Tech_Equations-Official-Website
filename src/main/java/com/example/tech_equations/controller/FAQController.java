package com.example.tech_equations.controller;

import com.example.tech_equations.model.FAQ;
import com.example.tech_equations.repository.FAQRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named
@SessionScoped
public class FAQController implements Serializable {

    private final FAQRepository faqRepository;

    private String question;
    private String answer;
    private String category;
    private int displayOrder;

    private List<FAQ> faqs;
    private FAQ faq = new FAQ();

    private boolean showCreateForm = false;

    @Inject
    public FAQController(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @PostConstruct
    public void init() {
        loadFAQs();
    }

    public void loadFAQs() {
        this.faqs = faqRepository.getAllFAQs();
    }

    public List<FAQ> getFAQs() {
        return faqs;
    }

    // CRUD Operations
    public void save() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (faq.getId() == null) {
                faq.setCreatedAt(LocalDateTime.now());
                faq.setUpdatedAt(LocalDateTime.now());
                faqRepository.saveFAQ(faq);
                context.addMessage(null, new FacesMessage("FAQ created successfully"));
            } else {
                faq.setUpdatedAt(LocalDateTime.now());
                faqRepository.updateFAQ(faq);
                context.addMessage(null, new FacesMessage("FAQ updated successfully"));
            }
            loadFAQs();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", null));
        }
    }

    public void edit(FAQ selectedFAQ) {
        this.faq = selectedFAQ;
    }

    public void deleteFAQ(FAQ selectedFAQ) {
        faqRepository.deleteFAQById(selectedFAQ.getId());
        loadFAQs();
    }

    public void clearForm() {
        this.faq = new FAQ();
    }

    public void reorderFAQs() {
        // Logic to reorder FAQs based on display order
        faqRepository.reorderFAQs(faqs);
        loadFAQs();
    }

    // Getters and Setters
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public FAQ getFAQ() { return faq; }
    public void setFAQ(FAQ faq) { this.faq = faq; }

    public boolean isShowCreateForm() { return showCreateForm; }
    public void setShowCreateForm(boolean showCreateForm) { this.showCreateForm = showCreateForm; }
}
