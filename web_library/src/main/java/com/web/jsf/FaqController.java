package com.web.jsf;

import com.web.entity.Blog;
import com.web.entity.Faq;
import com.web.entity.MicroProducts;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.FaqFacade;

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

import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.primefaces.event.RowEditEvent;

@Named("faqController")
@ApplicationScoped
public class FaqController implements Serializable {

    @EJB
    private com.web.session.FaqFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<Faq> items = null;
    private List<Faq> multiselectionItems = null;
    private List<Faq> createItems = null;
    private List<Faq> editItems = null;
    private List<Faq> filteredValues = null;
    private Faq selected;
    private Faq selected1;
    private Faq selected2 = new Faq();
    private String dataName = "Faq";
    protected int first;

    private String question;
    private String answer;
    private String category;
    private Integer displayOrder;
    private String searchTerm;

    private List<Faq> faqs;
    private List<Faq> filteredFaqs;
    private Faq faq = new Faq();
    private Faq tempEditedFaq;

    private Faq selectedFaq;
    private boolean showCreateForm = false;

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public FaqController() {
    }
    public List<String> getFaqCategories() {
        TypedQuery<String> query = em.createQuery("SELECT DISTINCT f.category FROM Faq f",
                String.class);
        return query.getResultList();
    }
    // Fetch all FAQs by category
    public List<Faq> getFaqsByCategory(String category) {
        TypedQuery<Faq> query = em.createQuery(
                "SELECT f FROM Faq f WHERE f.category = :category ORDER BY f.displayOrder", Faq.class);
        query.setParameter("category", category);
        return query.getResultList();
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

    public Faq getSelected() {
        return selected;
    }

    public void setSelected(Faq selected) {
        this.selected = selected;
    }

    public Faq getSelected1() {
        return selected1;
    }

    public void setSelected1(Faq selected1) {
        this.selected1 = selected1;
    }

    public Faq getSelected2() {
        return selected2;
    }

    public void setSelected2(Faq selected2) {
        this.selected2 = selected2;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
    public List<Faq> searchFaq() {
        String jpql = """
    SELECT f FROM Faq f
    WHERE 
        LOWER(f.question) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(f.answer) LIKE LOWER(CONCAT('%', :keyword, '%'))
    ORDER BY f.question ASC
    """;

        TypedQuery<Faq> query = em.createQuery(jpql, Faq.class);
        query.setParameter("keyword", searchTerm);

        this.items = query.getResultList();
        return items;
    }

    public List<Faq> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<Faq> faqs) {
        this.faqs = faqs;
    }

    public List<Faq> getFilteredFaqs() {
        return filteredFaqs;
    }

    public void setFilteredFaqs(List<Faq> filteredFaqs) {
        this.filteredFaqs = filteredFaqs;
    }

    public Faq getFaq() {
        return faq;
    }

    public void setFaq(Faq faq) {
        this.faq = faq;
    }

    public Faq getTempEditedFaq() {
        return tempEditedFaq;
    }

    public void setTempEditedFaq(Faq tempEditedFaq) {
        this.tempEditedFaq = tempEditedFaq;
    }

    public Faq getSelectedFaq() {
        return selectedFaq;
    }

    public void setSelectedFaq(Faq selectedFaq) {
        this.selectedFaq = selectedFaq;
    }

    public boolean isShowCreateForm() {
        return showCreateForm;
    }

    public void setShowCreateForm(boolean showCreateForm) {
        this.showCreateForm = showCreateForm;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (Faq item : getCreateItems()) {
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

    @PostConstruct
    public void init() {
        loadFaqs();
    }

    public void loadFaqs() {
        if (faqs == null) {
            faqs = getFacade().findAll();
        }
    }

    public void cancelCreate() {
        selected = null;
        createItems = null;
        items = null;
    }

    public String createFaq() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Faq newFaq = new Faq();
            newFaq.setId(UUID.randomUUID().toString());
            newFaq.setQuestion(question);
            newFaq.setAnswer(answer);
            newFaq.setCategory(category);
            newFaq.setDisplayOrder(displayOrder != null ? displayOrder : 0);
            newFaq.setCreatedAt(new Date());
            newFaq.setUpdatedAt(new Date());

            boolean created = getFacade().create1(newFaq);
            question = "";
            answer = "";
            category = "";
            displayOrder = null;

            if (created) {
                context.addMessage(null, new FacesMessage("FAQ created successfully"));
                loadFaqs();
                showCreateForm = false;
                return "faq-list?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Creation failed", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", null));
            return null;
        }
    }
    public void onRowEdit(RowEditEvent<Faq> event) {
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


    public void clearForm() {
        this.faq = new Faq();
        this.question = null;
        this.answer = null;
        this.category = null;
        this.displayOrder = null;
    }

    public void refreshList() {
        items = null;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private FaqFacade getFacade() {
        return ejbFacade;
    }

    public Faq prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new Faq();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Faq prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Faq prepareCreateInCreate() {

        selected1 = new Faq();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public Faq prepareCreate1() {
        selected = new Faq();
        int tempId = 0;
        for (Faq p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public Faq prepareCreateInEdit() {

        selected1 = new Faq();
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
        createItems.add(new Faq());
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

        for (Faq item : getCreateItems()) {
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
        for (Faq item : getEditItems()) {
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

        for (Faq item : getEditItems()) {
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

    public void removeInCreate(Faq item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(Faq item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(Faq item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<Faq> aList) {

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

    public List<Faq> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Faq> items) {
        this.items = items;
    }

    public List<Faq> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<Faq> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<Faq> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<Faq> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<Faq> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<Faq> createItems) {
        this.createItems = createItems;
    }

    public List<Faq> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<Faq> editItems) {
        this.editItems = editItems;
    }

    private List<Faq> preparingTempId(Faq item, List<Faq> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (Faq itm : aList) {
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
            for (Faq item : multiselectionItems) {
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

    public Faq getFaq(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Faq> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Faq> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Faq.class)
    public static class FaqControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FaqController controller = (FaqController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "faqController");
            return controller.getFaq(getKey(value));
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
            if (object instanceof Faq) {
                Faq o = (Faq) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Faq.class.getName()});
                return null;
            }
        }

    }

}
