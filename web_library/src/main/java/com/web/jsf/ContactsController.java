package com.web.jsf;

import com.web.entity.Blog;
import com.web.entity.Contacts;
import com.web.entity.Users;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.ContactsFacade;

import java.io.Serializable;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.primefaces.event.RowEditEvent;

@Named("contactsController")
@ApplicationScoped
public class ContactsController implements Serializable {

    @EJB
    private com.web.session.ContactsFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<Contacts> items = null;
    private List<Contacts> multiselectionItems = null;
    private List<Contacts> createItems = null;
    private List<Contacts> editItems = null;
    private List<Contacts> filteredValues = null;
    private Contacts selected;
    private Contacts selected1;
    private Contacts selected2 = new Contacts();
    private String dataName = "Contacts";

    private String name;
    private String email;
    private String subject;
    private String message;
    private String searchTerm;
    private boolean submitted = false;

    private List<Contacts> contacts;
    private Contacts contact = new Contacts();
    protected int first;

    public ContactsController() {
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

    public Contacts getSelected() {
        return selected;
    }

    public void setSelected(Contacts selected) {
        this.selected = selected;
    }

    public Contacts getSelected1() {
        return selected1;
    }

    public void setSelected1(Contacts selected1) {
        this.selected1 = selected1;
    }

    public Contacts getSelected2() {
        return selected2;
    }

    public void setSelected2(Contacts selected2) {
        this.selected2 = selected2;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Contacts getContact() {
        return contact;
    }

    public void setContact(Contacts contact) {
        this.contact = contact;
    }

    @PostConstruct
    public void init() {
        loadContacts();
    }

    public void loadContacts() {
        this.contacts = getFacade().findAll();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void clearForm() {
        this.name = "";
        this.email = "";
        this.subject = "";
        this.message = "";
    }

    public String submitContact() {
        FacesContext context = FacesContext.getCurrentInstance();

        // Validate email format
        if (!isValidEmail(email)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email address", null));
            return null;
        }

        if (message == null || message.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Message is required", null));
            return null;
        }

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            Contacts newContact = new Contacts();
            newContact.setId(UUID.randomUUID().toString());
            newContact.setName(name);
            newContact.setEmail(email);
            newContact.setSubject(subject);
            newContact.setMessage(message);
            newContact.setIsRead(false);
            newContact.setSubmittedAt(new Date());


            getFacade().create(newContact);
            context.addMessage(null, new FacesMessage("Message sent successfully"));
            clearForm();
            return "contact-success?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to submit message", null));
            return null;
        }
    }

    public void onRowEdit(RowEditEvent<Contacts> event) {
        selected1 = event.getObject();
    }

    public void confirmEdit() {
        try {
            if (selected1 != null) {
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


    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (Contacts item : getCreateItems()) {
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

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public void cancelCreate() {
        selected = null;
        createItems = null;
        items = null;
    }

    public List<Contacts> searchContact() {
        String jpql = "SELECT c FROM Contacts c " +
                "WHERE LOWER(c.name) LIKE :name " +
                "OR LOWER(c.email) LIKE :email " +
                "OR LOWER(c.message) LIKE :message " +
                "OR LOWER(c.subject) LIKE :subject";
        TypedQuery<Contacts> query = em.createQuery(jpql, Contacts.class);
        query.setParameter("name", "%" + searchTerm.toLowerCase() + "%");
        query.setParameter("email", "%" + searchTerm.toLowerCase() + "%");
        query.setParameter("message", "%" + searchTerm.toLowerCase() + "%");
        query.setParameter("subject", "%" + searchTerm.toLowerCase() + "%");
        contacts = query.getResultList();
        return contacts;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public void refreshList() {
        items = null;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ContactsFacade getFacade() {
        return ejbFacade;
    }

    public Contacts prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new Contacts();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Contacts prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Contacts prepareCreateInCreate() {

        selected1 = new Contacts();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public Contacts prepareCreate1() {
        selected = new Contacts();
        int tempId = 0;
        for (Contacts p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public Contacts prepareCreateInEdit() {

        selected1 = new Contacts();
        editItems = preparingTempId(selected1, editItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public boolean markAsRead(String id) {
        String jpql = "UPDATE Contacts c SET c.isRead = TRUE WHERE c.id = :id";
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            int updated = em.createQuery(jpql)
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return false;
    }

    public void toggleRead(Contacts contact) {
        // Toggle the read status of the contact and save/update in DB
        contact.setIsRead(!contact.getIsRead());
        markAsRead(contact.getId());
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
        createItems.add(new Contacts());
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

    public String saveAndAddContinue(String linkName) {
        createItems = new ArrayList<>();
        createItems.add(selected);
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }
    public String createContact() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (name == null || email == null || subject == null || message == null ||
                name.isEmpty() || email.isEmpty() || subject.isEmpty() || message.isEmpty()) {

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: Name, email, subject, and message are required", null));
            return null;
        }

        try {
            Contacts newMessage = new Contacts();
            newMessage.setId(UUID.randomUUID().toString());
            newMessage.setName(name);
            newMessage.setEmail(email);
            newMessage.setSubject(subject);
            newMessage.setMessage(message);
            newMessage.setSubmittedAt(new Date());

            boolean saved = getFacade().create1(newMessage);

            // Clear fields
            name = "";
            email = "";
            subject = "";
            message = "";

            if (saved) {
                setSubmitted(true);
                context.addMessage(null, new FacesMessage("Your message was sent successfully."));
                return "contact?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error: Could not send your message", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: Unexpected error occurred", null));
            return null;
        }
    }


    public void save() {

        for (Contacts item : getCreateItems()) {
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
        for (Contacts item : getEditItems()) {
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

        for (Contacts item : getEditItems()) {
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

    public void removeInCreate(Contacts item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(Contacts item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(Contacts item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<Contacts> aList) {

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

    public List<Contacts> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Contacts> items) {
        this.items = items;
    }

    public List<Contacts> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<Contacts> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<Contacts> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<Contacts> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<Contacts> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<Contacts> createItems) {
        this.createItems = createItems;
    }

    public List<Contacts> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<Contacts> editItems) {
        this.editItems = editItems;
    }

    private List<Contacts> preparingTempId(Contacts item, List<Contacts> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (Contacts itm : aList) {
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
            for (Contacts item : multiselectionItems) {
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

    public Contacts getContacts(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Contacts> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Contacts> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Contacts.class)
    public static class ContactsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContactsController controller = (ContactsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contactsController");
            return controller.getContacts(getKey(value));
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
            if (object instanceof Contacts) {
                Contacts o = (Contacts) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Contacts.class.getName()});
                return null;
            }
        }

    }

}
