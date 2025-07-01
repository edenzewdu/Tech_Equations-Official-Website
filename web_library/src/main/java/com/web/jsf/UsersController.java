package com.web.jsf;

import com.web.entity.TeamMembers;
import com.web.entity.Users;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.UsersFacade;

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

@Named("usersController")
@ApplicationScoped
public class UsersController implements Serializable {

    @EJB
    private com.web.session.UsersFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<Users> items = null;
    private List<Users> multiselectionItems = null;
    private List<Users> createItems = null;
    private List<Users> editItems = null;
    private List<Users> filteredValues = null;
    private Users selected;
    private Users selected1;
    private Users selected2 = new Users();
    private String dataName = "Users";
    protected int first;
    private String name;
    private String email;
    private String passwordHash;
    private String role = "ADMIN";
    private String searchTerm;

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UsersController() {
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

    public Users getSelected() {
        return selected;
    }

    public void setSelected(Users selected) {
        this.selected = selected;
    }

    public Users getSelected1() {
        return selected1;
    }

    public void setSelected1(Users selected1) {
        this.selected1 = selected1;
    }

    public Users getSelected2() {
        return selected2;
    }

    public void setSelected2(Users selected2) {
        this.selected2 = selected2;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (Users item : getCreateItems()) {
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

    private UsersFacade getFacade() {
        return ejbFacade;
    }
    public void createUser() {
        try {
            // Validate required fields
            if (name == null || name.isEmpty() ||
                    email == null || email.isEmpty() ||
                    passwordHash == null || passwordHash.isEmpty()) {
                JsfUtil.addErrorMessage("Name, Email, and Password are required.");
                return;
            }

            // Check for duplicate email
            String jpql = "SELECT u FROM Users u WHERE u.email = :email";
            Users existingUser = null;
            try {
                existingUser = em.createQuery(jpql, Users.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                // No user found — continue
            }

            if (existingUser != null) {
                JsfUtil.addErrorMessage("Email already exists. Please use a different email.");
                return;
            }
            Users newUser = new Users();

            // Set timestamps
            Date now = new Date();
            newUser.setCreatedAt(now);
            newUser.setUpdatedAt(now);

            // Set fields
            newUser.setId(UUID.randomUUID().toString());  // Set UUID
            newUser.setName(this.name);
            newUser.setEmail(this.email);
            newUser.setPasswordHash(PasswordUtil.hashPassword(this.passwordHash));
            newUser.setRole1(this.role);

            getFacade().create(newUser);

            // Reset fields
            name = "";
            email = "";
            passwordHash = "";
            role = "Admin";
            items = null;

            JsfUtil.addSuccessMessage("User created successfully.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error creating user");
        }
    }


    public void onRowEdit(RowEditEvent<Users> event) {
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

    public void prepareDelete(Users user) {
        this.selected = user;
    }

    public void deleteUser() {
        try {
            if (selected != null) {
                getFacade().remove(selected);
                items = null;
                selected = null;
                JsfUtil.addSuccessMessage("User deleted");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Delete failed");
        }
    }

    public List<Users> searchUsers() {
        String jpql = """
        SELECT u FROM Users u
        WHERE 
                    LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                    LOWER(u.role) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                    LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY u.name ASC
    """;


        TypedQuery<Users> query = em.createQuery(jpql, Users.class);
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Users prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new Users();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Users prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Users prepareCreateInCreate() {

        selected1 = new Users();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public Users prepareCreate1() {
        selected = new Users();
        int tempId = 0;
        for (Users p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public Users prepareCreateInEdit() {

        selected1 = new Users();
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
        createItems.add(new Users());
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

        for (Users item : getCreateItems()) {
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
        for (Users item : getEditItems()) {
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

        for (Users item : getEditItems()) {
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

    public void removeInCreate(Users item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(Users item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(Users item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<Users> aList) {

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

    public List<Users> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Users> items) {
        this.items = items;
    }

    public List<Users> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<Users> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<Users> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<Users> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<Users> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<Users> createItems) {
        this.createItems = createItems;
    }

    public List<Users> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<Users> editItems) {
        this.editItems = editItems;
    }

    private List<Users> preparingTempId(Users item, List<Users> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (Users itm : aList) {
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
            for (Users item : multiselectionItems) {
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

    public Users getUsers(java.lang.String id) {
        return getFacade().find(id);
    }

    public Users getUsersByEmail(java.lang.String id) {
        List<Users> userList = (List<Users>) ejbFacade1.findByStringId("Users.findByEmail", "email", id);
       if(!userList.isEmpty()){
           return userList.get(0);
       }
return null;
    }

    public List<Users> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Users> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Users.class)
    public static class UsersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsersController controller = (UsersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usersController");
            return controller.getUsers(getKey(value));
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
            if (object instanceof Users) {
                Users o = (Users) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Users.class.getName()});
                return null;
            }
        }

    }

}
