package com.web.jsf;

import com.web.entity.MicroProducts;
import com.web.entity.Products;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.MicroProductsFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

@Named("microProductsController")
@ApplicationScoped
public class MicroProductsController implements Serializable {

    @EJB
    private com.web.session.MicroProductsFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<MicroProducts> items = null;
    private List<MicroProducts> multiselectionItems = null;
    private List<MicroProducts> createItems = null;
    private List<MicroProducts> editItems = null;
    private List<MicroProducts> filteredValues = null;
    private MicroProducts selected;
    private MicroProducts selected1;
    private MicroProducts selected2 = new MicroProducts();
    private String dataName = "MicroProducts";
    protected int first;

    public MicroProductsController() {
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

    public MicroProducts getSelected() {
        return selected;
    }

    public void setSelected(MicroProducts selected) {
        this.selected = selected;
    }

    public MicroProducts getSelected1() {
        return selected1;
    }

    public void setSelected1(MicroProducts selected1) {
        this.selected1 = selected1;
    }

    public MicroProducts getSelected2() {
        return selected2;
    }

    public void setSelected2(MicroProducts selected2) {
        this.selected2 = selected2;
    }



    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (MicroProducts item : getCreateItems()) {
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

    private MicroProductsFacade getFacade() {
        return ejbFacade;
    }

    public MicroProducts prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new MicroProducts();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public MicroProducts prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public MicroProducts prepareCreateInCreate() {

        selected1 = new MicroProducts();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public MicroProducts prepareCreate1() {
        selected = new MicroProducts();
        int tempId = 0;
        for (MicroProducts p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public MicroProducts prepareCreateInEdit() {

        selected1 = new MicroProducts();
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
        createItems.add(new MicroProducts());
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

        for (MicroProducts item : getCreateItems()) {
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
    public void createMicroProduct( MicroProducts microProducts) {
        boolean created = getFacade().create1(microProducts);
        // Provide feedback
        if (created) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Micro Product created successfully"));
            items = getFacade().findAll();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create micro product", null));
        }

    }
    public void save(MicroProducts item) {

        if (item.getId() == null) {
            getFacade().create(item);
        } else {
            getFacade().edit(item);
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            JsfUtil.addSuccessMessage("Saved");
        }
    }

    public void saveRow() {
        for (MicroProducts item : getEditItems()) {
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

        for (MicroProducts item : getEditItems()) {
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

    public void removeInCreate(MicroProducts item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(MicroProducts item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(MicroProducts item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<MicroProducts> aList) {

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

    public List<MicroProducts> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<MicroProducts> items) {
        this.items = items;
    }

    public List<MicroProducts> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<MicroProducts> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<MicroProducts> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<MicroProducts> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<MicroProducts> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<MicroProducts> createItems) {
        this.createItems = createItems;
    }

    public List<MicroProducts> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<MicroProducts> editItems) {
        this.editItems = editItems;
    }

    private List<MicroProducts> preparingTempId(MicroProducts item, List<MicroProducts> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (MicroProducts itm : aList) {
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
            for (MicroProducts item : multiselectionItems) {
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

    public MicroProducts getMicroProducts(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<MicroProducts> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<MicroProducts> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = MicroProducts.class)
    public static class MicroProductsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MicroProductsController controller = (MicroProductsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "microProductsController");
            return controller.getMicroProducts(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MicroProducts) {
                MicroProducts o = (MicroProducts) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MicroProducts.class.getName()});
                return null;
            }
        }

    }

}
