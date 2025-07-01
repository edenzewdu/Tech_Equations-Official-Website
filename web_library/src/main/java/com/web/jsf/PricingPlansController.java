package com.web.jsf;

import com.web.entity.PricingPlans;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.PricingPlansFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
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

import java.util.stream.Collectors;

import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

@Named("pricingPlansController")
@ApplicationScoped
public class PricingPlansController implements Serializable {

    @EJB
    private com.web.session.PricingPlansFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<PricingPlans> items = null;
    private List<PricingPlans> multiselectionItems = null;
    private List<PricingPlans> createItems = null;
    private List<PricingPlans> editItems = null;
    private List<PricingPlans> filteredValues = null;
    private PricingPlans selected;
    private PricingPlans selected1;
    private PricingPlans selected2 = new PricingPlans();
    private String dataName = "PricingPlans";
    protected int first;

    private String name;
    private double price;
    private double original_price;
    private String billingFrequency;
    private String features;
    private boolean highlighted;
    private int displayOrder;
    private String searchTerm;

    private String currentFrequency;
    private List<String> frequencies;
    private List<PricingPlans> pricingPlans;
    private List<PricingPlans> publishedPricingPlans;
    private int frequencyIndex = 0;
    private PricingPlans selectedPricingPlan;
    private PricingPlans tempEditedPricingPlan;
    private PricingPlans pricingPlan = new PricingPlans();

    private boolean showCreateForm = false;

    public PricingPlansController() {
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

    public PricingPlans getSelected() {
        return selected;
    }

    public void setSelected(PricingPlans selected) {
        this.selected = selected;
    }

    public PricingPlans getSelected1() {
        return selected1;
    }

    public void setSelected1(PricingPlans selected1) {
        this.selected1 = selected1;
    }

    public PricingPlans getSelected2() {
        return selected2;
    }

    public void setSelected2(PricingPlans selected2) {
        this.selected2 = selected2;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public String getBillingFrequency() {
        return billingFrequency;
    }

    public void setBillingFrequency(String billingFrequency) {
        this.billingFrequency = billingFrequency;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getCurrentFrequency() {
        return currentFrequency;
    }

    public void setCurrentFrequency(String currentFrequency) {
        this.currentFrequency = currentFrequency;
    }

    public List<String> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(List<String> frequencies) {
        this.frequencies = frequencies;
    }

    public List<PricingPlans> getPricingPlans() {
        return pricingPlans;
    }

    public void setPricingPlans(List<PricingPlans> pricingPlans) {
        this.pricingPlans = pricingPlans;
    }

    public List<PricingPlans> getPublishedPricingPlans() {
        return publishedPricingPlans;
    }

    public void setPublishedPricingPlans(List<PricingPlans> publishedPricingPlans) {
        this.publishedPricingPlans = publishedPricingPlans;
    }

    public int getFrequencyIndex() {
        return frequencyIndex;
    }

    public void setFrequencyIndex(int frequencyIndex) {
        this.frequencyIndex = frequencyIndex;
    }

    public PricingPlans getSelectedPricingPlan() {
        return selectedPricingPlan;
    }

    public void setSelectedPricingPlan(PricingPlans selectedPricingPlan) {
        this.selectedPricingPlan = selectedPricingPlan;
    }

    public PricingPlans getTempEditedPricingPlan() {
        return tempEditedPricingPlan;
    }

    public void setTempEditedPricingPlan(PricingPlans tempEditedPricingPlan) {
        this.tempEditedPricingPlan = tempEditedPricingPlan;
    }

    public PricingPlans getPricingPlan() {
        return pricingPlan;
    }

    public void setPricingPlan(PricingPlans pricingPlan) {
        this.pricingPlan = pricingPlan;
    }

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        TypedQuery<String> query = em.createQuery("SELECT DISTINCT p.billingFrequency FROM PricingPlans p",
                String.class);
        frequencies = query.getResultList();
        if (!frequencies.isEmpty()) {
            currentFrequency = frequencies.get(0);
        }
        loadPricingPlans();
    }

    public void loadPricingPlans() {
        this.pricingPlans = getFacade().findAll();
    }

    public void nextFrequency() {
        frequencyIndex = (frequencyIndex + 1) % frequencies.size();
        currentFrequency = frequencies.get(frequencyIndex);
        loadPricingPlans();
    }

    public List<PricingPlans> getPublishedPricingPlansByFrequency() {
        if (currentFrequency != null && !currentFrequency.isEmpty()) {
            TypedQuery<PricingPlans> query = em.createQuery(
                    "SELECT p FROM PricingPlans p WHERE p.billingFrequency = :billingFrequency ORDER BY p.displayOrder",
                    PricingPlans.class
            );
            query.setParameter("billingFrequency", currentFrequency);  // e.g. "monthly", "yearly"
            this.publishedPricingPlans = query.getResultList();
            ;
        } else {
            this.publishedPricingPlans = List.of();
        }
        return publishedPricingPlans;
    }

    public List<PricingPlans> searchPricingPlans() {
        String likeTerm = "%" + searchTerm.toLowerCase() + "%";

        this.pricingPlans = em.createQuery(
                        "SELECT p FROM PricingPlans p " +
                                "WHERE LOWER(p.name) LIKE :search " +
                                "OR LOWER(p.features) LIKE :search " +
                                "OR LOWER(p.billingFrequency) LIKE :search",
                        PricingPlans.class
                )
                .setParameter("search", likeTerm)
                .getResultList();
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
            PricingPlans newPlan = new PricingPlans();
            newPlan.setId(UUID.randomUUID().toString());
            newPlan.setName(name);
            newPlan.setPrice(BigDecimal.valueOf(price));
            newPlan.setOriginalPrice(BigDecimal.valueOf(original_price));
            newPlan.setBillingFrequency(billingFrequency);
            newPlan.setFeatures(features);
            newPlan.setIsHighlighted(highlighted);
            newPlan.setDisplayOrder(displayOrder);
            newPlan.setCreatedAt(new Date());
            newPlan.setUpdatedAt(new Date());

            boolean created = getFacade().create1(newPlan);

            if (created) {
                Long count = em.createQuery(
                                "SELECT COUNT(p) FROM PricingPlans p WHERE p.displayOrder = :displayOrder", Long.class)
                        .setParameter("displayOrder", displayOrder)
                        .getSingleResult();

                if (count > 0) {
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

    public void onRowEdit(RowEditEvent<PricingPlans> event) {
        selected1 = event.getObject();
    }

    public void confirmEdit() {
        try {
            if (selected1 != null) {
                selected1.setUpdatedAt(new Date());
                getFacade().edit(selected1);
                JsfUtil.addSuccessMessage("Edit confirmed for Pricing Plan.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Edit confirmation failed.");
        }
    }

    public void cancelEdit() {
        selected1 = null;
        items = null;
    }

    public void prepareDelete(PricingPlans plan) {
        this.selected = plan;
    }
    public void deletePricingPlan() {
        try {
            if (selected != null) {
                getFacade().remove(selected);
                items = null;
                selected = null;
                JsfUtil.addSuccessMessage("Pricing Plan deleted successfully.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error deleting Pricing Plan.");
        }
    }

    public void save() {
        if (pricingPlan.getId() == null) {
            pricingPlan.setCreatedAt(new Date());
            pricingPlan.setUpdatedAt(new Date());
            getFacade().create(pricingPlan);
        } else {
            pricingPlan.setUpdatedAt(new Date());
            getFacade().edit(pricingPlan);
        }
        clearForm();
        loadPricingPlans();
    }

    public void clearForm() {
        this.pricingPlan = new PricingPlans();
    }

    public void discard() {
        selected = null;
        for (PricingPlans item : getCreateItems()) {
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

    private PricingPlansFacade getFacade() {
        return ejbFacade;
    }

    public PricingPlans prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new PricingPlans();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public PricingPlans prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public PricingPlans prepareCreateInCreate() {

        selected1 = new PricingPlans();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public PricingPlans prepareCreate1() {
        selected = new PricingPlans();
        int tempId = 0;
        for (PricingPlans p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public PricingPlans prepareCreateInEdit() {

        selected1 = new PricingPlans();
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
        createItems.add(new PricingPlans());
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

    public String saveAndAddContinue(String linkName) {
        createItems = new ArrayList<>();
        createItems.add(selected);
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

//    public void save() {
//
//        for (PricingPlans item : getCreateItems()) {
//            if (item.getId() == null) {
//                getFacade().create(item);
//            } else {
//                getFacade().edit(item);
//            }
//        }
//        if (!JsfUtil.isValidationFailed()) {
//            items = null;    // Invalidate list of items to trigger re-query.
//            JsfUtil.addSuccessMessage("Saved");
//        }
//    }

    public void saveRow() {
        for (PricingPlans item : getEditItems()) {
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

        for (PricingPlans item : getEditItems()) {
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

    public void removeInCreate(PricingPlans item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(PricingPlans item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(PricingPlans item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<PricingPlans> aList) {

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

    public List<PricingPlans> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<PricingPlans> items) {
        this.items = items;
    }

    public List<PricingPlans> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<PricingPlans> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<PricingPlans> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<PricingPlans> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<PricingPlans> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<PricingPlans> createItems) {
        this.createItems = createItems;
    }

    public List<PricingPlans> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<PricingPlans> editItems) {
        this.editItems = editItems;
    }

    private List<PricingPlans> preparingTempId(PricingPlans item, List<PricingPlans> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (PricingPlans itm : aList) {
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
            for (PricingPlans item : multiselectionItems) {
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

    public PricingPlans getPricingPlans(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<PricingPlans> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PricingPlans> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PricingPlans.class)
    public static class PricingPlansControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PricingPlansController controller = (PricingPlansController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pricingPlansController");
            return controller.getPricingPlans(getKey(value));
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
            if (object instanceof PricingPlans) {
                PricingPlans o = (PricingPlans) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PricingPlans.class.getName()});
                return null;
            }
        }

    }

}
