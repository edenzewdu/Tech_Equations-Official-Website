package com.web.jsf;

import com.web.entity.Services;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.ServicesFacade;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.primefaces.event.RowEditEvent;

@Named("servicesController")
@ApplicationScoped
public class ServicesController implements Serializable {

    @EJB
    private com.web.session.ServicesFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<Services> items = null;
    private List<Services> multiselectionItems = null;
    private List<Services> createItems = null;
    private List<Services> editItems = null;
    private List<Services> filteredValues = null;
    private Services selected;
    private Services selected1;
    private Services selected2 = new Services();
    private String dataName = "Services";
    protected int first;

    private String name;
    private String description;
    private String img;
    private String searchTerm;
    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;


    public ServicesController() {
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

    public Services getSelected() {
        return selected;
    }

    public void setSelected(Services selected) {
        this.selected = selected;
    }

    public Services getSelected1() {
        return selected1;
    }

    public void setSelected1(Services selected1) {
        this.selected1 = selected1;
    }

    public Services getSelected2() {
        return selected2;
    }

    public void setSelected2(Services selected2) {
        this.selected2 = selected2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (Services item : getCreateItems()) {
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

    public void createService() {
        try {
            // Validate required fields
            if (name == null || name.isEmpty() ||
                    description == null || description.isEmpty()) {
                JsfUtil.addErrorMessage("Name and Description are required.");
                return;
            }

            // Optional: Check if service with the same name exists
            String jpql = "SELECT s FROM Services s WHERE s.name = :name";
            Services existingService = null;
            try {
                existingService = em.createQuery(jpql, Services.class)
                        .setParameter("name", name)
                        .getSingleResult();
            } catch (NoResultException e) {
                // No duplicate — continue
            }

            if (existingService != null) {
                JsfUtil.addErrorMessage("Service with this name already exists.");
                return;
            }

            Services newService = new Services();
            Date now = new Date();

            newService.setId(UUID.randomUUID().toString());
            newService.setName(this.name);
            newService.setDescription(this.description);
            newService.setCreatedAt(now);
            newService.setUpdatedAt(now);
            newService.setImg(this.img); // Optional image URL or path

            getFacade().create(newService);

            // Reset fields
            name = "";
            description = "";
            img = "";
            items = null;

            JsfUtil.addSuccessMessage("Service created successfully.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error creating service");
        }
    }

    public void onRowEdit(RowEditEvent<Services> event) {
        selected1 = event.getObject();
    }

    public void confirmEdit() {
        try {
            if (selected1 != null) {
                selected1.setUpdatedAt(new Date());
                getFacade().edit(selected1);
                JsfUtil.addSuccessMessage("Service updated successfully.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Update failed.");
        }
    }

    public void cancelEdit() {
        selected1 = null;
        items = null; // Force refresh
    }

    public void prepareDelete(Services service) {
        this.selected = service;
    }

    public void deleteService() {
        try {
            if (selected != null) {
                getFacade().remove(selected);
                items = null;
                selected = null;
                JsfUtil.addSuccessMessage("Service deleted successfully.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Service deletion failed.");
        }
    }

    public List<Services> searchServices() {
        String jpql = """
    SELECT s FROM Services s
    WHERE 
        LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    ORDER BY s.name ASC
    """;

        TypedQuery<Services> query = em.createQuery(jpql, Services.class);
        query.setParameter("keyword", searchTerm);

        this.items = query.getResultList();
        return items;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
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

    private ServicesFacade getFacade() {
        return ejbFacade;
    }

    public Services prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new Services();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Services prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Services prepareCreateInCreate() {

        selected1 = new Services();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public Services prepareCreate1() {
        selected = new Services();
        int tempId = 0;
        for (Services p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public Services prepareCreateInEdit() {

        selected1 = new Services();
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
        createItems.add(new Services());
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

        for (Services item : getCreateItems()) {
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
        for (Services item : getEditItems()) {
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

        for (Services item : getEditItems()) {
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

    public void removeInCreate(Services item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(Services item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(Services item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<Services> aList) {

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

    public List<Services> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Services> items) {
        this.items = items;
    }

    public List<Services> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<Services> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<Services> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<Services> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<Services> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<Services> createItems) {
        this.createItems = createItems;
    }

    public List<Services> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<Services> editItems) {
        this.editItems = editItems;
    }

    private List<Services> preparingTempId(Services item, List<Services> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (Services itm : aList) {
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
            for (Services item : multiselectionItems) {
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

    public Services getServices(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Services> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Services> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Services.class)
    public static class ServicesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ServicesController controller = (ServicesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "servicesController");
            return controller.getServices(getKey(value));
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
            if (object instanceof Services) {
                Services o = (Services) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Services.class.getName()});
                return null;
            }
        }

    }

}
