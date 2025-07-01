package com.web.jsf;

import com.web.entity.Partners;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.PartnersFacade;

import java.io.Serializable;
import java.sql.Timestamp;
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

@Named("partnersController")
@ApplicationScoped
public class PartnersController implements Serializable {

    @EJB
    private com.web.session.PartnersFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<Partners> items = null;
    private List<Partners> multiselectionItems = null;
    private List<Partners> createItems = null;
    private List<Partners> editItems = null;
    private List<Partners> filteredValues = null;
    private Partners selected;
    private Partners selected1;
    private Partners selected2 = new Partners();
    private String dataName = "Partners";
    protected int first;

    private String name;
    private String logoUrl;
    private String websiteUrl;
    private Integer displayOrder;
    private String searchTerm;

    private List<Partners> partners;
    private List<Partners> filteredPartners;
    private Partners partner = new Partners();
    private Partners tempEditedPartner;
    private Partners selectedPartner;

    private boolean showCreateForm = false;

    public PartnersController() {
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

    public Partners getSelected() {
        return selected;
    }

    public void setSelected(Partners selected) {
        this.selected = selected;
    }

    public Partners getSelected1() {
        return selected1;
    }

    public void setSelected1(Partners selected1) {
        this.selected1 = selected1;
    }

    public Partners getSelected2() {
        return selected2;
    }

    public void setSelected2(Partners selected2) {
        this.selected2 = selected2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<Partners> getPartners() {
        return partners;
    }

    public void setPartners(List<Partners> partners) {
        this.partners = partners;
    }

    public List<Partners> getFilteredPartners() {
        return filteredPartners;
    }

    public void setFilteredPartners(List<Partners> filteredPartners) {
        this.filteredPartners = filteredPartners;
    }

    public Partners getPartner() {
        return partner;
    }

    public void setPartner(Partners partner) {
        this.partner = partner;
    }

    public Partners getTempEditedPartner() {
        return tempEditedPartner;
    }

    public void setTempEditedPartner(Partners tempEditedPartner) {
        this.tempEditedPartner = tempEditedPartner;
    }

    public Partners getSelectedPartner() {
        return selectedPartner;
    }

    public void setSelectedPartner(Partners selectedPartner) {
        this.selectedPartner = selectedPartner;
    }

    @PostConstruct
    public void init() {
        loadPartners();
    }

    public void loadPartners() {
        this.partners = getFacade().findAll();
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (Partners item : getCreateItems()) {
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

    public String createPartner() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            this.partner = new Partners(); // Reset form
            Partners newPartner = new Partners();
            newPartner.setName(name);
            newPartner.setLogoUrl(logoUrl);
            newPartner.setWebsiteUrl(websiteUrl);
            newPartner.setDisplayOrder(displayOrder != null ? displayOrder : 0);
            newPartner.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            newPartner.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            boolean created = getFacade().create1(newPartner);

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
    public void onRowEdit(RowEditEvent<Partners> event) {
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
    public void prepareDelete(Partners partner) {
        this.selected = partner;
    }

    public void deletePartner() {
        try {
            if (selected != null) {
                getFacade().remove(selected);
                items = null;
                selected = null;
                JsfUtil.addSuccessMessage("Partner deleted successfully.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error deleting Partner.");
        }
    }

    public void refreshList() {
        items = null;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public void clearForm() {
        this.partner = new Partners();
    }

    private PartnersFacade getFacade() {
        return ejbFacade;
    }

    public Partners prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new Partners();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Partners prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public List<Partners> searchPartners() {
        String likeTerm = "%" + searchTerm.toLowerCase() + "%";

        TypedQuery<Partners> query = em.createQuery(
                "SELECT p FROM Partners p WHERE LOWER(p.name) LIKE :searchTerm OR LOWER(p.websiteUrl) LIKE :searchTerm",
                Partners.class
        );
        query.setParameter("searchTerm", likeTerm);

        partners = query.getResultList();
        return partners;
    }

    public Partners prepareCreateInCreate() {

        selected1 = new Partners();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public Partners prepareCreate1() {
        selected = new Partners();
        int tempId = 0;
        for (Partners p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public Partners prepareCreateInEdit() {

        selected1 = new Partners();
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
        createItems.add(new Partners());
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

    public String saveAndAddContinue(String linkName) {
        createItems = new ArrayList<>();
        createItems.add(selected);
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

    public void save() {

        for (Partners item : getCreateItems()) {
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
        for (Partners item : getEditItems()) {
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

        for (Partners item : getEditItems()) {
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

    public void removeInCreate(Partners item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(Partners item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(Partners item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<Partners> aList) {

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

    public List<Partners> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Partners> items) {
        this.items = items;
    }

    public List<Partners> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<Partners> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<Partners> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<Partners> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<Partners> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<Partners> createItems) {
        this.createItems = createItems;
    }

    public List<Partners> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<Partners> editItems) {
        this.editItems = editItems;
    }

    private List<Partners> preparingTempId(Partners item, List<Partners> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (Partners itm : aList) {
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
            for (Partners item : multiselectionItems) {
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

    public Partners getPartners(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Partners> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Partners> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Partners.class)
    public static class PartnersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PartnersController controller = (PartnersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "partnersController");
            return controller.getPartners(getKey(value));
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
            if (object instanceof Partners) {
                Partners o = (Partners) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Partners.class.getName()});
                return null;
            }
        }

    }

}
