package com.web.jsf;

import com.web.entity.Contacts;
import com.web.entity.ContentBlocks;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.ContentBlocksFacade;

import java.io.Serializable;
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

@Named("contentBlocksController")
@ApplicationScoped
public class ContentBlocksController implements Serializable {

    @EJB
    private com.web.session.ContentBlocksFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<ContentBlocks> items = null;
    private List<ContentBlocks> multiselectionItems = null;
    private List<ContentBlocks> createItems = null;
    private List<ContentBlocks> editItems = null;
    private List<ContentBlocks> filteredValues = null;
    private ContentBlocks selected;
    private ContentBlocks selected1;
    private ContentBlocks selected2 = new ContentBlocks();
    private String dataName = "ContentBlocks";
    protected int first;

    private String id;
    private String content;
    private String searchTerm;

    private ContentBlocks tempEditedBlock;
    private ContentBlocks selectedBlock;

    private List<ContentBlocks> blocks;
    private List<ContentBlocks> filteredBlocks;
    private ContentBlocks block = new ContentBlocks();

    public ContentBlocksController() {
    }

    @PostConstruct
    public void init() {
        loadBlocks();
    }

    public void loadBlocks() {
        this.blocks = getFacade().findAll();
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

    public ContentBlocks getSelected() {
        return selected;
    }

    public void setSelected(ContentBlocks selected) {
        this.selected = selected;
    }

    public ContentBlocks getSelected1() {
        return selected1;
    }

    public void setSelected1(ContentBlocks selected1) {
        this.selected1 = selected1;
    }

    public ContentBlocks getSelected2() {
        return selected2;
    }

    public void setSelected2(ContentBlocks selected2) {
        this.selected2 = selected2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public ContentBlocks getTempEditedBlock() {
        return tempEditedBlock;
    }

    public void setTempEditedBlock(ContentBlocks tempEditedBlock) {
        this.tempEditedBlock = tempEditedBlock;
    }

    public ContentBlocks getSelectedBlock() {
        return selectedBlock;
    }

    public void setSelectedBlock(ContentBlocks selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

    public List<ContentBlocks> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<ContentBlocks> blocks) {
        this.blocks = blocks;
    }

    public List<ContentBlocks> getFilteredBlocks() {
        return filteredBlocks;
    }

    public void setFilteredBlocks(List<ContentBlocks> filteredBlocks) {
        this.filteredBlocks = filteredBlocks;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (ContentBlocks item : getCreateItems()) {
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

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public List<ContentBlocks> searchBlocks() {
        String jpql = "SELECT c FROM ContentBlocks c " +
                "WHERE LOWER(c.id) LIKE :id " +
                "OR LOWER(c.content) LIKE :content ";
        TypedQuery<ContentBlocks> query = em.createQuery(jpql, ContentBlocks.class);
        query.setParameter("id", "%" + searchTerm.toLowerCase() + "%");
        query.setParameter("content", "%" + searchTerm.toLowerCase() + "%");
        blocks = query.getResultList();
        return blocks;
    }

    public String getBlockById(String id) {
        String jpql = "SELECT c FROM ContentBlocks c " +
                "WHERE c.id = :id ";
        TypedQuery<ContentBlocks> query = em.createQuery(jpql, ContentBlocks.class);
        query.setParameter("id", id);
        ContentBlocks block = query.getSingleResult();
        return block != null ? block.getContent() : "";
    }

    public String createBlock() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (id == null || content == null || id.isEmpty() || content.isEmpty()) {
            return "Error: ID and content are required";
        }

        try {
            ContentBlocks newBlock = new ContentBlocks();
            newBlock.setId(id);
            newBlock.setContent(content);

            boolean created = getFacade().create1(newBlock);

            if (created) {
                context.addMessage(null, new FacesMessage("Content block created successfully"));
                loadBlocks();
                return "content-blocks?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Block creation failed", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unexpected error";
        }
    }
    public void onRowEdit(RowEditEvent<ContentBlocks> event) {
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


    public void deleteBlock() {
        try {
            if (selectedBlock != null) {
                removeRecord(selectedBlock);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Block deleted successfully"));
                loadBlocks();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No block selected", "Please select a block first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete block"));
        }
    }

    public void save() {
        if (block.getId() == null || block.getId().isEmpty()) {
            return;
        }
        String jpql = "SELECT c FROM ContentBlocks c " +
                "WHERE id = :id ";
        TypedQuery<ContentBlocks> query = em.createQuery(jpql, ContentBlocks.class);
        query.setParameter("id", block.getId());
        if (query.getResultList() == null) {
            getFacade().create(block);
        } else {
            getFacade().edit(block);
        }
        clearForm();
        loadBlocks();
    }

    public void clearForm() {
        this.block = new ContentBlocks();
    }

    public void refreshList() {
        items = null;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ContentBlocksFacade getFacade() {
        return ejbFacade;
    }

    public ContentBlocks prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new ContentBlocks();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public ContentBlocks prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public ContentBlocks prepareCreateInCreate() {

        selected1 = new ContentBlocks();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public ContentBlocks prepareCreate1() {
        selected = new ContentBlocks();
        int tempId = 0;
        for (ContentBlocks p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public ContentBlocks prepareCreateInEdit() {

        selected1 = new ContentBlocks();
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
        createItems.add(new ContentBlocks());
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
//        for (ContentBlocks item : getCreateItems()) {
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
        for (ContentBlocks item : getEditItems()) {
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

        for (ContentBlocks item : getEditItems()) {
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

    public void removeInCreate(ContentBlocks item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(ContentBlocks item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(ContentBlocks item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<ContentBlocks> aList) {

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

    public List<ContentBlocks> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<ContentBlocks> items) {
        this.items = items;
    }

    public List<ContentBlocks> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<ContentBlocks> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<ContentBlocks> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<ContentBlocks> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<ContentBlocks> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<ContentBlocks> createItems) {
        this.createItems = createItems;
    }

    public List<ContentBlocks> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<ContentBlocks> editItems) {
        this.editItems = editItems;
    }

    private List<ContentBlocks> preparingTempId(ContentBlocks item, List<ContentBlocks> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (ContentBlocks itm : aList) {
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
            for (ContentBlocks item : multiselectionItems) {
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

    public ContentBlocks getContentBlocks(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<ContentBlocks> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ContentBlocks> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ContentBlocks.class)
    public static class ContentBlocksControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContentBlocksController controller = (ContentBlocksController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contentBlocksController");
            return controller.getContentBlocks(getKey(value));
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
            if (object instanceof ContentBlocks) {
                ContentBlocks o = (ContentBlocks) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ContentBlocks.class.getName()});
                return null;
            }
        }

    }

}
