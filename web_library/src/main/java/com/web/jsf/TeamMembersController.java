package com.web.jsf;

import com.web.entity.TeamMembers;
import com.web.entity.Testimonials;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.TeamMembersFacade;

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
import jakarta.persistence.EntityManager;

import jakarta.persistence.Column;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.Size;
import org.primefaces.event.RowEditEvent;

import jakarta.persistence.TypedQuery;

@Named("teamMembersController")
@ApplicationScoped
public class TeamMembersController implements Serializable {

    @EJB
    private com.web.session.TeamMembersFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<TeamMembers> items = null;
    private List<TeamMembers> multiselectionItems = null;
    private List<TeamMembers> createItems = null;
    private List<TeamMembers> editItems = null;
    private List<TeamMembers> filteredValues = null;
    private TeamMembers selected;
    private TeamMembers selected1;
    private TeamMembers selected2 = new TeamMembers();
    private String name;
    private String role;
    private String imageUrl;
    private String dataName = "TeamMembers";
    protected int first;
    private String searchTerm;

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public TeamMembersController() {
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

    public TeamMembers getSelected() {
        return selected;
    }

    public void setSelected(TeamMembers selected) {
        this.selected = selected;
    }

    public TeamMembers getSelected1() {
        return selected1;
    }

    public void setSelected1(TeamMembers selected1) {
        this.selected1 = selected1;
    }

    public TeamMembers getSelected2() {
        return selected2;
    }

    public void setSelected2(TeamMembers selected2) {
        this.selected2 = selected2;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (TeamMembers item : getCreateItems()) {
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

    private TeamMembersFacade getFacade() {
        return ejbFacade;
    }

    public TeamMembers prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new TeamMembers();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public TeamMembers prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public TeamMembers prepareCreateInCreate() {

        selected1 = new TeamMembers();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public TeamMembers prepareCreate1() {
        selected = new TeamMembers();
        int tempId = 0;
        for (TeamMembers p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public TeamMembers prepareCreateInEdit() {

        selected1 = new TeamMembers();
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
        createItems.add(new TeamMembers());
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

        for (TeamMembers item : getCreateItems()) {
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
        for (TeamMembers item : getEditItems()) {
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

        for (TeamMembers item : getEditItems()) {
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

    public void removeInCreate(TeamMembers item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(TeamMembers item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(TeamMembers item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<TeamMembers> aList) {

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
    public void createMember() {
        try {
            TeamMembers newMember = new TeamMembers();

            // Set timestamps
            Date now = new Date();
            newMember.setCreatedAt(now);
            newMember.setUpdatedAt(now);
            newMember.setId(UUID.randomUUID().toString());
            newMember.setName(this.name);
            newMember.setRole(this.role);
            newMember.setImageUrl(this.imageUrl);

            getFacade().create(newMember);

            // reset fields
            name = "";
            role = "";
            imageUrl = "";
            items = null;
            JsfUtil.addSuccessMessage("Team member created successfully.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error creating team member");
        }
    }

    public void onRowEdit(RowEditEvent<TeamMembers> event) {
        selected1 = event.getObject(); // Temporarily store the edited object
    }


    public void confirmEdit() {
        try {
            if (selected1 != null) {
                selected1.setUpdatedAt(new Date());
                getFacade().edit(selected1);
                JsfUtil.addSuccessMessage("Edit confirmed");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Edit confirmation failed");
        }
    }


    public void cancelEdit() {
        selected1 = null;
        items = null; // Force reloading from DB
    }


    public void prepareDelete(TeamMembers member) {
        this.selected = member;
    }

    public void deleteMember() {
        try {
            if (selected != null) {
                getFacade().remove(selected);
                items = null;
                selected = null;
                JsfUtil.addSuccessMessage("Team Member deleted");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Delete failed");
        }
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<TeamMembers> searchTeamMembers() {
        String jpql = """
        SELECT t FROM TeamMembers t
        WHERE 
                    LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                    LOWER(t.role) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                    LOWER(t.imageUrl) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY t.name ASC
    """;


        TypedQuery<TeamMembers> query = em.createQuery(jpql, TeamMembers.class);
        query.setParameter("keyword", searchTerm);

        this.items = query.getResultList();
        return items;
    }



    public List<TeamMembers> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<TeamMembers> items) {
        this.items = items;
    }

    public List<TeamMembers> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<TeamMembers> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<TeamMembers> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<TeamMembers> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<TeamMembers> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<TeamMembers> createItems) {
        this.createItems = createItems;
    }

    public List<TeamMembers> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<TeamMembers> editItems) {
        this.editItems = editItems;
    }

    private List<TeamMembers> preparingTempId(TeamMembers item, List<TeamMembers> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (TeamMembers itm : aList) {
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
            for (TeamMembers item : multiselectionItems) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public TeamMembers getTeamMembers(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<TeamMembers> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TeamMembers> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TeamMembers.class)
    public static class TeamMembersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TeamMembersController controller = (TeamMembersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "teamMembersController");
            return controller.getTeamMembers(getKey(value));
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
            if (object instanceof TeamMembers) {
                TeamMembers o = (TeamMembers) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TeamMembers.class.getName()});
                return null;
            }
        }

    }

}
