package com.lucy.controller;

import com.lucy.model.ImpactStat;
import com.lucy.repository.ImpactStatRepository;
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

@Named
@SessionScoped
public class ImpactStatController implements Serializable {

    private final ImpactStatRepository impactStatRepository;

    private String label;
    private int value;
    private String searchTerm;

    private List<ImpactStat> stats;
    private List<ImpactStat> filteredStats;
    private ImpactStat impactStat = new ImpactStat();
    private ImpactStat tempEditedStat;

    private ImpactStat selectedStat;

    private boolean showCreateForm = false;

    @Inject
    public ImpactStatController(ImpactStatRepository impactStatRepository) {
        this.impactStatRepository = impactStatRepository;
    }

    @PostConstruct
    public void init() {
        loadStats();
    }

    public void loadStats() {
        this.stats = impactStatRepository.getAllImpactStats();
    }

    public List<ImpactStat> getStats() {
        return stats;
    }

    public List<ImpactStat> searchStats() {
        stats = impactStatRepository.searchImpactStat(searchTerm);
        return stats;
    }

    public ImpactStat findStatById(String id) {
        return impactStatRepository.getImpactStatById(id);
    }

    public String createStat() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.impactStat = new ImpactStat(); // Reset the form
            ImpactStat newStat = new ImpactStat();
            newStat.setLabel(label);
            newStat.setValue(value);
            newStat.setCreatedAt(LocalDateTime.now());
            newStat.setUpdatedAt(LocalDateTime.now());

            boolean created = impactStatRepository.saveImpactStat(newStat);

            if (created) {
                context.addMessage(null, new FacesMessage("Stat created successfully"));
                loadStats();
                showCreateForm = false;
                return "impactstat-list?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Stat may already exist", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", null));
            return null;
        }
    }

    // CRUD Operations
    public void deleteStat() {
        try {
            if (selectedStat != null) {
                impactStatRepository.deleteImpactStatById(selectedStat.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Stat deleted successfully"));
                loadStats();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Stat not selected", "Please select a stat first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete stat"));
        }
    }

    public void save() {
        if (impactStat.getId() == null) {
            impactStat.setCreatedAt(LocalDateTime.now());
            impactStat.setUpdatedAt(LocalDateTime.now());
            impactStatRepository.saveImpactStat(impactStat);
        } else {
            impactStat.setUpdatedAt(LocalDateTime.now());
            impactStatRepository.updateImpactStat(impactStat);
        }
        clearForm();
        loadStats();
    }

    public void onRowEdit(RowEditEvent<ImpactStat> event) {
        tempEditedStat = event.getObject();
    }

    public void confirmEdit() {
        if (tempEditedStat != null) {
            tempEditedStat.setUpdatedAt(LocalDateTime.now());
            impactStatRepository.updateImpactStat(tempEditedStat);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Stat edited successfully"));
            loadStats();
            tempEditedStat = null;
        }
    }

    public void cancelEdit() {
        this.stats = impactStatRepository.getAllImpactStats();
        tempEditedStat = null;
    }

    public void clearForm() {
        this.impactStat = new ImpactStat();
    }

    public void prepareDelete(ImpactStat stat) {
        this.selectedStat = stat;
    }

    // Getters and Setters
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public ImpactStat getImpactStat() { return impactStat; }
    public void setImpactStat(ImpactStat impactStat) { this.impactStat = impactStat; }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }
}
