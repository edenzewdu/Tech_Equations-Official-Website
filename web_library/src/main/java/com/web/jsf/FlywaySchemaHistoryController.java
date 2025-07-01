package com.web.jsf;

import com.web.entity.FlywaySchemaHistory;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.FlywaySchemaHistoryFacade;

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

@Named("flywaySchemaHistoryController")
@ApplicationScoped
public class FlywaySchemaHistoryController implements Serializable {

    @EJB
    private com.web.session.FlywaySchemaHistoryFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<FlywaySchemaHistory> items = null;
    private List<FlywaySchemaHistory> multiselectionItems = null;
    private List<FlywaySchemaHistory> createItems = null;
    private List<FlywaySchemaHistory> editItems = null;
    private List<FlywaySchemaHistory> filteredValues = null;
    private FlywaySchemaHistory selected;
    private FlywaySchemaHistory selected1;
    private FlywaySchemaHistory selected2 = new FlywaySchemaHistory();
    private String dataName = "FlywaySchemaHistory";
    protected int first;

    public FlywaySchemaHistoryController() {
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

    public FlywaySchemaHistory getSelected() {
        return selected;
    }

    public void setSelected(FlywaySchemaHistory selected) {
        this.selected = selected;
    }

    public FlywaySchemaHistory getSelected1() {
        return selected1;
    }

    public void setSelected1(FlywaySchemaHistory selected1) {
        this.selected1 = selected1;
    }

    public FlywaySchemaHistory getSelected2() {
        return selected2;
    }

    public void setSelected2(FlywaySchemaHistory selected2) {
        this.selected2 = selected2;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (FlywaySchemaHistory item : getCreateItems()) {
            if (item.getInstalledRank() != null) {
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

    private FlywaySchemaHistoryFacade getFacade() {
        return ejbFacade;
    }

    public FlywaySchemaHistory prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new FlywaySchemaHistory();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public FlywaySchemaHistory prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setInstalledRank(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public FlywaySchemaHistory prepareCreateInCreate() {

        selected1 = new FlywaySchemaHistory();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public FlywaySchemaHistory prepareCreate1() {
        selected = new FlywaySchemaHistory();
        int tempId = 0;
        for (FlywaySchemaHistory p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public FlywaySchemaHistory prepareCreateInEdit() {

        selected1 = new FlywaySchemaHistory();
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
        createItems.add(new FlywaySchemaHistory());
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

        for (FlywaySchemaHistory item : getCreateItems()) {
            if (item.getInstalledRank() == null) {
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
        for (FlywaySchemaHistory item : getEditItems()) {
            if (item.getInstalledRank() == null) {
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

        for (FlywaySchemaHistory item : getEditItems()) {
            if (item.getInstalledRank() == null) {
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

    public void removeInCreate(FlywaySchemaHistory item) {

        if (item.getInstalledRank() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getInstalledRank().equals(item.getInstalledRank()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(FlywaySchemaHistory item) {

        if (item.getInstalledRank() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getInstalledRank().equals(item.getInstalledRank()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(FlywaySchemaHistory item) {

        if (item.getInstalledRank() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<FlywaySchemaHistory> aList) {

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

    public List<FlywaySchemaHistory> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<FlywaySchemaHistory> items) {
        this.items = items;
    }

    public List<FlywaySchemaHistory> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<FlywaySchemaHistory> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<FlywaySchemaHistory> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<FlywaySchemaHistory> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<FlywaySchemaHistory> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<FlywaySchemaHistory> createItems) {
        this.createItems = createItems;
    }

    public List<FlywaySchemaHistory> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<FlywaySchemaHistory> editItems) {
        this.editItems = editItems;
    }

    private List<FlywaySchemaHistory> preparingTempId(FlywaySchemaHistory item, List<FlywaySchemaHistory> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (FlywaySchemaHistory itm : aList) {
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
            for (FlywaySchemaHistory item : multiselectionItems) {
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

    public FlywaySchemaHistory getFlywaySchemaHistory(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<FlywaySchemaHistory> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<FlywaySchemaHistory> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = FlywaySchemaHistory.class)
    public static class FlywaySchemaHistoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FlywaySchemaHistoryController controller = (FlywaySchemaHistoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "flywaySchemaHistoryController");
            return controller.getFlywaySchemaHistory(getKey(value));
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
            if (object instanceof FlywaySchemaHistory) {
                FlywaySchemaHistory o = (FlywaySchemaHistory) object;
                return getStringKey(o.getInstalledRank());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), FlywaySchemaHistory.class.getName()});
                return null;
            }
        }

    }

}
