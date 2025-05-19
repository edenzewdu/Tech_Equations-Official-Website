package com.example.tech_equations.controller;

import com.example.tech_equations.model.PricingPlan;
import com.example.tech_equations.model.Testimonial;
import com.example.tech_equations.repository.PricingPlanRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Named("pricingPlanController")
@SessionScoped
public class PricingPlanController implements Serializable {

    private final PricingPlanRepository pricingPlanRepository;

    private String name;
    private double price;
    private String billingFrequency;
    private String features;
    private boolean highlighted;
    private int displayOrder;
    private String searchTerm;

    private String currentFrequency;
    private List<String> frequencies;
    private List<PricingPlan> pricingPlans;
    private List<PricingPlan> publishedPricingPlans;
    private int frequencyIndex = 0;
    private PricingPlan selectedPricingPlan;
    private PricingPlan tempEditedPricingPlan;
    private PricingPlan pricingPlan = new PricingPlan();

    private boolean showCreateForm = false;

    @Inject
    public PricingPlanController(PricingPlanRepository pricingPlanRepository) {
        this.pricingPlanRepository = pricingPlanRepository;
    }

    @PostConstruct
    public void init() {
        frequencies = pricingPlanRepository.getAllBillingFrequencies();
        if (!frequencies.isEmpty()) {
            currentFrequency = frequencies.get(0);
        }
        loadPricingPlans();
    }

    public void loadPricingPlans() {
        this.pricingPlans = pricingPlanRepository.getAllPricingPlans();
    }
    public void nextFrequency() {
        frequencyIndex = (frequencyIndex + 1) % frequencies.size();
        currentFrequency = frequencies.get(frequencyIndex);
        loadPricingPlans();
    }

    public String getCurrentFrequency() {
        return currentFrequency;
    }

    public void setCurrentFrequency(String currentFrequency) {
        this.currentFrequency = currentFrequency;
    }
    public List<PricingPlan> getPublishedPricingPlansByFrequency() {
        if (currentFrequency != null && !currentFrequency.isEmpty()) {
            this.publishedPricingPlans = pricingPlanRepository.getAllPricingPlansByFeatures(currentFrequency);
        } else {
            this.publishedPricingPlans = List.of();
        }
        return publishedPricingPlans;
    }
    public List<PricingPlan> getPublishedPricingPlans() {
        return publishedPricingPlans;
    }


    public List<String> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(List<String> frequencies) {
        this.frequencies = frequencies;
    }
    public List<PricingPlan> getPricingPlans() {
        return pricingPlans;
    }

    public List<PricingPlan> searchPricingPlans() {
        this.pricingPlans = pricingPlanRepository.searchPricingPlans(searchTerm);
        return pricingPlans;
    }

    public String createPricingPlan() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (name == null || billingFrequency == null || features == null || name.isEmpty() || features.isEmpty() || billingFrequency.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name and features are required", null));
            return null;
        }
        if (displayOrder < 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Display order must be non-negative", null));
            return null;
        }

        try {
            PricingPlan newPlan = new PricingPlan();
            newPlan.setId(UUID.randomUUID().toString());
            newPlan.setName(name);
            newPlan.setPrice(price);
            newPlan.setBillingFrequency(billingFrequency);
            newPlan.setFeatures(features);
            newPlan.setHighlighted(highlighted);
            newPlan.setDisplayOrder(displayOrder);
            newPlan.setCreatedAt(LocalDateTime.now());
            newPlan.setUpdatedAt(LocalDateTime.now());

            boolean created = pricingPlanRepository.savePricingPlan(newPlan);

            if (created) {
                if (pricingPlanRepository.countPublishedWithSameDisplayOrder(displayOrder) > 0) {
                    PrimeFaces.current().executeScript("PF('WarningDialogWidget').show();");
                }
                context.addMessage(null, new FacesMessage("Pricing plan created successfully"));
                loadPricingPlans();
                clearForm();
                showCreateForm = false;
                return "pricing-plans?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create pricing plan", null));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", null));
        }

        return null;
    }

    public void deletePricingPlan(PricingPlan plan) {
        pricingPlanRepository.deletePricingPlanById(plan.getId());
        loadPricingPlans();
    }

    public void deletePricingPlan() {
        try {
            if (selectedPricingPlan != null) {
                pricingPlanRepository.deletePricingPlanById(selectedPricingPlan.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pricing plan deleted successfully"));
                loadPricingPlans();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Testimonial not selected", "Please select a testimonial first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete testimonial"));
        }
        pricingPlanRepository.deletePricingPlanById(pricingPlan.getId());
        loadPricingPlans();
    }
    public void save() {
        if (pricingPlan.getId() == null) {
            pricingPlan.setCreatedAt(LocalDateTime.now());
            pricingPlan.setUpdatedAt(LocalDateTime.now());
            pricingPlanRepository.savePricingPlan(pricingPlan);
        } else {
            pricingPlan.setUpdatedAt(LocalDateTime.now());
            pricingPlanRepository.editPricingPlan(pricingPlan);
        }
        clearForm();
        loadPricingPlans();
    }

    public void onRowEdit(RowEditEvent<PricingPlan> event) {
        tempEditedPricingPlan = event.getObject();
    }

    public void confirmEdit() {
        if (tempEditedPricingPlan != null) {
            tempEditedPricingPlan.setUpdatedAt(LocalDateTime.now());
            pricingPlanRepository.updatePricingPlan(tempEditedPricingPlan);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pricing plan updated successfully"));
            loadPricingPlans();
            tempEditedPricingPlan = null;
        }
    }

    public void cancelEdit() {
        this.pricingPlans = pricingPlanRepository.getAllPricingPlans();
        tempEditedPricingPlan = null;
    }

    public void clearForm() {
        this.pricingPlan = new PricingPlan();
    }

    public void editPricingPlan(PricingPlan selectedPlan) {
        this.pricingPlan = selectedPlan;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getBillingFrequency() { return billingFrequency; }
    public void setBillingFrequency(String billingFrequency) { this.billingFrequency = billingFrequency; }

    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }

    public boolean isHighlighted() { return highlighted; }
    public void setHighlighted(boolean highlighted) { this.highlighted = highlighted; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }

    public PricingPlan getPricingPlan() { return pricingPlan; }
    public void setPricingPlan(PricingPlan pricingPlan) { this.pricingPlan = pricingPlan; }

    public PricingPlan getSelectedPricingPlan() { return selectedPricingPlan; }
    public void setSelectedPricingPlan(PricingPlan selectedPricingPlan) { this.selectedPricingPlan = selectedPricingPlan; }
}
