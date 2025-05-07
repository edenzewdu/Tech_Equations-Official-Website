//package com.example.tech_equations.controller;
//
//import com.example.tech_equations.model.FAQ;
//import com.example.tech_equations.repository.FAQRepository;
//import jakarta.inject.Inject;
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.faces.application.FacesMessage;
//import jakarta.faces.context.FacesContext;
//import java.io.Serializable;
//import java.util.List;
//
//@RequestScoped
//public class FAQController implements Serializable {
//
//    @Inject
//    private FAQRepository faqRepository;
//
//    private FAQ faq;
//    private List<FAQ> faqList;
//
//    public FAQController() {
//        faq = new FAQ();
//    }
//
//    public String createFAQ() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        if (faqRepository.saveFAQ(faq)) {
//            context.addMessage(null, new FacesMessage("FAQ created successfully"));
//            return "faq_list.xhtml?faces-redirect=true";
//        } else {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create FAQ", null));
//            return null;
//        }
//    }
//
//    public String updateFAQ() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        if (faqRepository.updateFAQ(faq)) {
//            context.addMessage(null, new FacesMessage("FAQ updated successfully"));
//            return "faq_list.xhtml?faces-redirect=true";
//        } else {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to update FAQ", null));
//            return null;
//        }
//    }
//
//    public String deleteFAQ(int id) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        if (faqRepository.deleteFAQ(id)) {
//            context.addMessage(null, new FacesMessage("FAQ deleted successfully"));
//            return "faq_list.xhtml?faces-redirect=true";
//        } else {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to delete FAQ", null));
//            return null;
//        }
//    }
//
//    public List<FAQ> getAllFAQs() {
//        return faqRepository.getAllFAQs();
//    }
//
//    public FAQ getFAQ() {
//        return faq;
//    }
//
//    public void setFAQ(FAQ faq) {
//        this.faq = faq;
//    }
//
//    public List<FAQ> getFaqList() {
//        return faqList;
//    }
//
//    public void setFaqList(List<FAQ> faqList) {
//        this.faqList = faqList;
//    }
//}
