package com.web.jsf;

import com.web.entity.ImpactStats;
import com.web.entity.Services;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.ImpactStatsFacade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;

import java.util.ArrayList;

import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.primefaces.event.RowEditEvent;

@Named("impactStatsController")
@ApplicationScoped
public class ImpactStatsController implements Serializable {

    @EJB
    private com.web.session.ImpactStatsFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<ImpactStats> items = null;
    private List<ImpactStats> multiselectionItems = null;
    private List<ImpactStats> createItems = null;
    private List<ImpactStats> editItems = null;
    private List<ImpactStats> filteredValues = null;
    private ImpactStats selected;
    private ImpactStats selected1;
    private ImpactStats selected2 = new ImpactStats();
    private String dataName = "ImpactStats";
    protected int first;
    private String label;
    private int value;
    private String searchTerm;

    private List<ImpactStats> stats;
    private List<ImpactStats> filteredStats;
    private ImpactStats impactStat = new ImpactStats();
    private ImpactStats tempEditedStat;

    private ImpactStats selectedStat;

    public ImpactStatsController() {
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public String getDataName() {
        return this.dataName;
    }

    public void setDataName(final String dataName) {
        this.dataName = dataName;
    }

    public ImpactStats getSelected() {
        return selected;
    }

    public void setSelected(ImpactStats selected) {
        this.selected = selected;
    }

    public ImpactStats getSelected1() {
        return selected1;
    }

    public void setSelected1(ImpactStats selected1) {
        this.selected1 = selected1;
    }

    public ImpactStats getSelected2() {
        return selected2;
    }

    public void setSelected2(ImpactStats selected2) {
        this.selected2 = selected2;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<ImpactStats> getStats() {
        return stats;
    }

    public void setStats(List<ImpactStats> stats) {
        this.stats = stats;
    }

    public List<ImpactStats> getFilteredStats() {
        return filteredStats;
    }

    public void setFilteredStats(List<ImpactStats> filteredStats) {
        this.filteredStats = filteredStats;
    }

    public ImpactStats getImpactStat() {
        return impactStat;
    }

    public void setImpactStat(ImpactStats impactStat) {
        this.impactStat = impactStat;
    }

    public ImpactStats getTempEditedStat() {
        return tempEditedStat;
    }

    public void setTempEditedStat(ImpactStats tempEditedStat) {
        this.tempEditedStat = tempEditedStat;
    }

    public ImpactStats getSelectedStat() {
        return selectedStat;
    }

    public void setSelectedStat(ImpactStats selectedStat) {
        this.selectedStat = selectedStat;
    }
    @PostConstruct
    public void init() {
        loadStats();
    }

    public void loadStats() {
        this.stats = getFacade().findAll();
    }
    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (ImpactStats item : getCreateItems()) {
            if (item.getId() != null) {
                getFacade().remove(item);
            }

        }
        createItems = null;
        items = null;
        if (!JsfUtil.isValidationFailed()) {
            // Invalidate list of items to trigger re-query.
            JsfUtil.addSuccessMessage("All records are removed");
        }
    }

    public void onRowEdit(RowEditEvent<ImpactStats> event) {
        selected1 = event.getObject();
    }

    public void confirmEdit() {
        try {
            if (selected1 != null) {
                selected1.setUpdatedAt(new Date());
                getFacade().edit(selected1);
                JsfUtil.addSuccessMessage("Partner edit confirmed.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Edit confirmation failed.");
        }
    }

    public void cancelEdit() {
        selected1 = null;
        items = null;
    }

    public void cancelCreate() {
        selected = null;
        createItems = null;
        items = null;
    }

    public void refreshList() {
        items = null;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ImpactStatsFacade getFacade() {
        return ejbFacade;
    }

    public ImpactStats prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new ImpactStats();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public List<ImpactStats> searchStats() {
        String jpql = """
    SELECT i FROM ImpactStats i
    WHERE 
        LOWER(i.label) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """;

        TypedQuery<ImpactStats> query = em.createQuery(jpql, ImpactStats.class);
        query.setParameter("keyword", searchTerm);

        this.items = query.getResultList();
        return items;
    }

    public ImpactStats prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public ImpactStats prepareCreateInCreate() {

        selected1 = new ImpactStats();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public ImpactStats prepareCreate1() {
        selected = new ImpactStats();
        int tempId = 0;
        for (ImpactStats p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public ImpactStats prepareCreateInEdit() {

        selected1 = new ImpactStats();
        editItems = preparingTempId(selected1, editItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public void prepareEdit() {
        editItems = new ArrayList<>();
        selected = multiselectionItems.get(0);
        editItems.add(selected);
    }

    public String saveAndClose(String linkName) {
        cancelUpdate();
        cancelCreate();
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

    public String saveAndAddNew(String linkName) {
        createItems = new ArrayList<>();
        createItems.add(new ImpactStats());
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

    public String saveAndAddContinue(String linkName) {
        createItems = new ArrayList<>();
        createItems.add(selected);
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }
    private boolean showCreateForm = false;

    public String createStat() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.impactStat = new ImpactStats(); // Reset the form
            ImpactStats newStat = new ImpactStats();
            newStat.setLabel(label);
            newStat.setValue(value);
            newStat.setCreatedAt(new Date());
            newStat.setUpdatedAt(new Date());

            boolean created = getFacade().create1(newStat);

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

    public void prepareDelete(ImpactStats stat) {
        this.selectedStat = stat;
    }
    // CRUD Operations
    public void deleteStat() {
        try {
            if (selectedStat != null) {
               removeRecord(selectedStat);
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

        for (ImpactStats item : getCreateItems()) {
            if (item.getId() == null) {
                getFacade().create(item);
            } else {
                getFacade().edit(item);
            }
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            JsfUtil.addSuccessMessage("Saved");
        }
    }

    public void saveRow() {
        for (ImpactStats item : getEditItems()) {
            if (item.getId() == null) {
                getFacade().create(item);
            } else {
                getFacade().edit(item);
            }

        }
        if (!JsfUtil.isValidationFailed()) {
            // Invalidate list of items to trigger re-query.
            JsfUtil.addSuccessMessage("Saved");
        }
    }

    public void saveInEdit() {

        for (ImpactStats item : getEditItems()) {
            if (item.getId() == null) {
                getFacade().create(item);
            } else {
                getFacade().edit(item);
            }
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            JsfUtil.addSuccessMessage("Saved");
        }
    }

    public void createInEdit() {
        getFacade().create(selected1);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE);
    }

    public void removeInCreate(ImpactStats item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(ImpactStats item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(ImpactStats item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<ImpactStats> aList) {

        if (aList != null && !aList.isEmpty()) {
            getFacade().removeCollection(aList);
        }

    }

    public void destroy() {
        persist(PersistAction.DELETE);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            multiselectionItems = null;
        }
    }

    public List<ImpactStats> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<ImpactStats> items) {
        this.items = items;
    }

    public List<ImpactStats> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<ImpactStats> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<ImpactStats> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<ImpactStats> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<ImpactStats> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<ImpactStats> createItems) {
        this.createItems = createItems;
    }

    public List<ImpactStats> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<ImpactStats> editItems) {
        this.editItems = editItems;
    }

    private List<ImpactStats> preparingTempId(ImpactStats item, List<ImpactStats> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (ImpactStats itm : aList) {
                if (itm.getTempId() != null && itm.getTempId() > tempId) {
                    tempId = itm.getTempId();
                }
            }
        }
        tempId += 1;
        item.setTempId(tempId);
        aList.add(item);
        return aList;
    }

    private void persist(PersistAction persistAction) {
        if (!multiselectionItems.isEmpty()) {
            for (ImpactStats item : multiselectionItems) {
                setEmbeddableKeys();
                try {
                    if (persistAction != PersistAction.DELETE) {
                        getFacade().edit(item);
                    } else {
                        getFacade().remove(item);
                    }

                } catch (EJBException ex) {
                    String msg = "";
                    Throwable cause = ex.getCause();
                    if (cause != null) {
                        msg = cause.getLocalizedMessage();
                    }
                    if (msg.length() > 0) {
                        JsfUtil.addErrorMessage(msg);
                    } else {
                        JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            }
        }
    }

    public ImpactStats getImpactStats(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<ImpactStats> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ImpactStats> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ImpactStats.class)
    public static class ImpactStatsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ImpactStatsController controller = (ImpactStatsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "impactStatsController");
            return controller.getImpactStats(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ImpactStats) {
                ImpactStats o = (ImpactStats) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ImpactStats.class.getName()});
                return null;
            }
        }

    }

}
