package com.web.jsf;

import com.web.entity.SubMicroProducts;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.SubMicroProductsFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import java.util.ArrayList;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@Named("subMicroProductsController")
@ApplicationScoped
public class SubMicroProductsController implements Serializable {

    @EJB
    private com.web.session.SubMicroProductsFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<SubMicroProducts> items = null;
    private List<SubMicroProducts> multiselectionItems = null;
    private List<SubMicroProducts> createItems = null;
    private List<SubMicroProducts> editItems = null;
    private List<SubMicroProducts> filteredValues = null;
    private SubMicroProducts selected;
    private SubMicroProducts selected1;
    private SubMicroProducts selected2 = new SubMicroProducts();
    private String dataName = "SubMicroProducts";
    protected int first;

    public SubMicroProductsController() {
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

    public SubMicroProducts getSelected() {
        return selected;
    }

    public void setSelected(SubMicroProducts selected) {
        this.selected = selected;
    }

    public SubMicroProducts getSelected1() {
        return selected1;
    }

    public void setSelected1(SubMicroProducts selected1) {
        this.selected1 = selected1;
    }

    public SubMicroProducts getSelected2() {
        return selected2;
    }

    public void setSelected2(SubMicroProducts selected2) {
        this.selected2 = selected2;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }


    public void discard() {
        selected = null;
        for (SubMicroProducts item : getCreateItems()) {
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
    public void createSubMicroProducts(SubMicroProducts sub){
        getFacade().create1(sub);
    }

    private SubMicroProductsFacade getFacade() {
        return ejbFacade;
    }

    public SubMicroProducts prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new SubMicroProducts();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public SubMicroProducts prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public SubMicroProducts prepareCreateInCreate() {

        selected1 = new SubMicroProducts();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public SubMicroProducts prepareCreate1() {
        selected = new SubMicroProducts();
        int tempId = 0;
        for (SubMicroProducts p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public SubMicroProducts prepareCreateInEdit() {

        selected1 = new SubMicroProducts();
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
        createItems.add(new SubMicroProducts());
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

        for (SubMicroProducts item : getCreateItems()) {
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
    public void save(SubMicroProducts item) {

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
        for (SubMicroProducts item : getEditItems()) {
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

        for (SubMicroProducts item : getEditItems()) {
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

    public void removeInCreate(SubMicroProducts item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(SubMicroProducts item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(SubMicroProducts item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<SubMicroProducts> aList) {

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

    public List<SubMicroProducts> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<SubMicroProducts> items) {
        this.items = items;
    }

    public List<SubMicroProducts> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<SubMicroProducts> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<SubMicroProducts> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<SubMicroProducts> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<SubMicroProducts> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<SubMicroProducts> createItems) {
        this.createItems = createItems;
    }

    public List<SubMicroProducts> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<SubMicroProducts> editItems) {
        this.editItems = editItems;
    }

    private List<SubMicroProducts> preparingTempId(SubMicroProducts item, List<SubMicroProducts> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (SubMicroProducts itm : aList) {
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
            for (SubMicroProducts item : multiselectionItems) {
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

    public SubMicroProducts getSubMicroProducts(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<SubMicroProducts> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<SubMicroProducts> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = SubMicroProducts.class)
    public static class SubMicroProductsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SubMicroProductsController controller = (SubMicroProductsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "subMicroProductsController");
            return controller.getSubMicroProducts(getKey(value));
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
            if (object instanceof SubMicroProducts) {
                SubMicroProducts o = (SubMicroProducts) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SubMicroProducts.class.getName()});
                return null;
            }
        }

    }

}
