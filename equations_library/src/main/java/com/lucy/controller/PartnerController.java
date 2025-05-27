package com.lucy.controller;

import com.lucy.model.Partner;
import com.lucy.repository.PartnerRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Named
@SessionScoped
public class PartnerController implements Serializable {

    private final PartnerRepository partnerRepository;

    private String name;
    private String logoUrl;
    private String websiteUrl;
    private Integer displayOrder;
    private String searchTerm;

    private List<Partner> partners;
    private List<Partner> filteredPartners;
    private Partner partner = new Partner();
    private Partner tempEditedPartner;
    private Partner selectedPartner;

    private boolean showCreateForm = false;

    @Inject
    public PartnerController(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @PostConstruct
    public void init() {
        loadPartners();
    }

    public void loadPartners() {
        this.partners = partnerRepository.getAllPartners();
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public List<Partner> searchPartners() {
        partners = partnerRepository.searchPartners(searchTerm);
        return partners;
    }

    public Partner findPartnerById(String id) {
        return partnerRepository.getPartnerById(id);
    }

    public String createPartner() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            this.partner = new Partner(); // Reset form
            Partner newPartner = new Partner();
            newPartner.setName(name);
            newPartner.setLogoUrl(logoUrl);
            newPartner.setWebsiteUrl(websiteUrl);
            newPartner.setDisplayOrder(displayOrder != null ? displayOrder : 0);
            newPartner.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            newPartner.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            boolean created = partnerRepository.savePartner(newPartner);

            if (created) {
                context.addMessage(null, new FacesMessage("Partner created successfully"));
                loadPartners();
                showCreateForm = false;
                return "partner-list?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Partner may already exist", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Optionally replace with logger
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", null));
            return null;
        }
    }

    public void deletePartner() {
        try {
            if (selectedPartner != null) {
                partnerRepository.deletePartnerById(selectedPartner.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Partner deleted successfully"));
                loadPartners();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Partner not selected", "Please select a partner first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete partner"));
        }
    }

    public void save() {
        if (partner.getId() == null) {
            partner.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            partner.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            partnerRepository.savePartner(partner);
        } else {
            partner.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            partnerRepository.updatePartner(partner);
        }
        clearForm();
        loadPartners();
    }

    public void onRowEdit(RowEditEvent<Partner> event) {
        tempEditedPartner = event.getObject();
    }

    public void confirmEdit() {
        if (tempEditedPartner != null) {
            tempEditedPartner.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            partnerRepository.updatePartner(tempEditedPartner);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Partner edited successfully"));
            loadPartners();
            tempEditedPartner = null;
        }
    }

    public void cancelEdit() {
        this.partners = partnerRepository.getAllPartners();
        tempEditedPartner = null;
    }

    public void clearForm() {
        this.partner = new Partner();
    }

    public void prepareDelete(Partner partner) {
        this.selectedPartner = partner;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getWebsiteUrl() { return websiteUrl; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }

    public Partner getPartner() { return partner; }
    public void setPartner(Partner partner) { this.partner = partner; }

    public Partner getSelectedPartner() { return selectedPartner; }
    public void setSelectedPartner(Partner selectedPartner) { this.selectedPartner = selectedPartner; }
}
