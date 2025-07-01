package com.web.jsf;

import com.web.entity.Testimonials;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.TestimonialsFacade;

import java.io.Serializable;
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
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

@Named("testimonialsController")
@ApplicationScoped
public class TestimonialsController implements Serializable {

    @EJB
    private com.web.session.TestimonialsFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<Testimonials> items = null;
    private List<Testimonials> multiselectionItems = null;
    private List<Testimonials> createItems = null;
    private List<Testimonials> editItems = null;
    private List<Testimonials> filteredValues = null;
    private Testimonials selected;
    private Testimonials selected1;
    private Testimonials selected2 = new Testimonials();
    private String dataName = "Testimonials";
    protected int first;

    private String authorName;
    private String authorTitle;
    private String quoteText;
    private boolean isPublished;
    private int displayOrder;
    private String searchTerm;
    private String avatarUrl;

    private List<Testimonials> testimonials;
    private List<Testimonials> publishedTestimonials;
    private Testimonials testimonial = new Testimonials();

    private boolean showCreateForm = false;
    private Testimonials selectedTestimonial;
    private Testimonials tempEditedTestimonial;
    private List<Testimonials> filteredTestimonial;

    public TestimonialsController() {
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

    public Testimonials getSelected() {
        return selected;
    }

    public void setSelected(Testimonials selected) {
        this.selected = selected;
    }

    public Testimonials getSelected1() {
        return selected1;
    }

    public void setSelected1(Testimonials selected1) {
        this.selected1 = selected1;
    }

    public Testimonials getSelected2() {
        return selected2;
    }

    public void setSelected2(Testimonials selected2) {
        this.selected2 = selected2;
    }

    @PostConstruct
    public void init() {
        loadTestimonials();
    }

    public void loadTestimonials() {
        this.testimonials = getFacade().findAll();
    }

    public List<Testimonials> getTestimonials() {
        return testimonials;
    }

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public List<Testimonials> searchTestimonials() {
        String jpql = """
                    SELECT t FROM Testimonials t
                    WHERE (
                        (:keyword = 'published' AND t.isPublished = TRUE)
                        OR (:keyword = 'unpublished' AND t.isPublished = FALSE)
                        OR (:keyword = 'draft' AND t.isPublished = FALSE)
                        OR (
                            :keyword NOT IN ('published', 'unpublished', 'draft') AND
                            (
                                LOWER(t.authorName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                                LOWER(t.authorTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                                LOWER(t.quoteText) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            )
                        )
                    )
                    ORDER BY t.displayOrder ASC
                """;

        TypedQuery<Testimonials> query = em.createQuery(jpql, Testimonials.class);
        query.setParameter("keyword", searchTerm);

        this.testimonials = query.getResultList();
        return testimonials;
    }

    public String createTestimonial() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (authorName == null || quoteText == null || authorName.isEmpty() || quoteText.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Author name and quote text are required", null));
            return null;
        }
        if (displayOrder < 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "testimonialForm:Display order must be non-negative", null));
            return null;
        }

        try {
            Testimonials newTestimonial = new Testimonials();
            newTestimonial.setAuthorName(authorName);
            newTestimonial.setAuthorTitle(authorTitle);
            newTestimonial.setQuoteText(quoteText);
            newTestimonial.setIsPublished(isPublished);
            newTestimonial.setDisplayOrder(displayOrder);
            newTestimonial.setAvatarUrl(avatarUrl);
            newTestimonial.setCreatedAt(new Date());
            newTestimonial.setUpdatedAt(new Date());

            boolean created = getFacade().create1(newTestimonial);

            if (created) {
                String jpql = "SELECT COUNT(t) FROM Testimonials t WHERE t.isPublished = TRUE AND t.displayOrder = :order";

                TypedQuery<Long> query = em.createQuery(jpql, Long.class);
                query.setParameter("order", displayOrder); // Replace displayOrder with your actual value
                Long count = query.getSingleResult();

                if (count > 0) {
                    PrimeFaces.current().executeScript("PF('WarningDialogWidget').show();");
                }


                context.addMessage(null, new FacesMessage("Testimonial created successfully"));
                loadTestimonials();
                this.testimonial = new Testimonials(); // Reset form
                showCreateForm = false;
                return "testimonial-list?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create testimonial", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", null));
            return null;
        }
    }


    public void prepareDelete(Testimonials testimonial) {
        this.selected = testimonial;
    }

    public void deleteTestimonial() {
        try {
            if (selected != null) {
                getFacade().remove(selected);
                items = null;
                selected = null;
                JsfUtil.addSuccessMessage("Testimonial deleted");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Delete failed");
        }
    }

    public void save() {
        if (testimonial.getId() == null) {
            testimonial.setCreatedAt(new Date());
            testimonial.setUpdatedAt(new Date());
            getFacade().create(testimonial);
        } else {
            testimonial.setUpdatedAt(new Date());
            getFacade().edit(testimonial);
        }
        clearForm();
        loadTestimonials();
    }

    public void onRowEdit(RowEditEvent<Testimonials> event) {
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

    public void clearForm() {
        this.testimonial = new Testimonials();
    }

    public void editTestimonial(Testimonials selectedTestimonial) {
        this.testimonial = selectedTestimonial;
    }

    public void prepareTogglePublished(Testimonials testimonial) {
        this.selectedTestimonial = testimonial;
    }

    public void togglePublishedStatus() {
        if (selectedTestimonial != null) {
            selectedTestimonial.setIsPublished(selectedTestimonial.getIsPublished());
            selectedTestimonial.setUpdatedAt(new Date()); // ✅ Update timestamp

            getFacade().create(selectedTestimonial);
            String jpql = "SELECT COUNT(t) FROM Testimonials t WHERE t.isPublished = TRUE AND t.displayOrder = :displayOrder";

            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("displayOrder", selectedTestimonial.getDisplayOrder()); // Replace 'displayOrder' with your value
            Long count = query.getSingleResult();

            if (count > 0) {
                PrimeFaces.current().executeScript("PF('WarningDialogWidget').show();");
            }

        }
    }


    // Getters and Setters
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorTitle() {
        return authorTitle;
    }

    public void setAuthorTitle(String authorTitle) {
        this.authorTitle = authorTitle;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Testimonials getTestimonial() {
        return testimonial;
    }

    public List<Testimonials> getPublishedTestimonials() {
        if (publishedTestimonials == null) {
            String jpql = "SELECT t FROM Testimonials t WHERE t.isPublished = TRUE ORDER BY t.displayOrder ASC";

            TypedQuery<Testimonials> query = em.createQuery(jpql, Testimonials.class);

            publishedTestimonials = query.getResultList();
        }
        return publishedTestimonials;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (Testimonials item : getCreateItems()) {
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

    private TestimonialsFacade getFacade() {
        return ejbFacade;
    }

    public Testimonials prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new Testimonials();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Testimonials prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Testimonials prepareCreateInCreate() {

        selected1 = new Testimonials();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public Testimonials prepareCreate1() {
        selected = new Testimonials();
        int tempId = 0;
        for (Testimonials p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public Testimonials prepareCreateInEdit() {

        selected1 = new Testimonials();
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
        createItems.add(new Testimonials());
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

    public String saveAndAddContinue(String linkName) {
        createItems = new ArrayList<>();
        createItems.add(selected);
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }
//
//    public void save() {
//
//        for (Testimonials item : getCreateItems()) {
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
        for (Testimonials item : getEditItems()) {
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

        for (Testimonials item : getEditItems()) {
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

    public void removeInCreate(Testimonials item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(Testimonials item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(Testimonials item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<Testimonials> aList) {

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

    public List<Testimonials> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Testimonials> items) {
        this.items = items;
    }

    public List<Testimonials> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<Testimonials> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<Testimonials> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<Testimonials> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<Testimonials> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<Testimonials> createItems) {
        this.createItems = createItems;
    }

    public List<Testimonials> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<Testimonials> editItems) {
        this.editItems = editItems;
    }

    private List<Testimonials> preparingTempId(Testimonials item, List<Testimonials> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (Testimonials itm : aList) {
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
            for (Testimonials item : multiselectionItems) {
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

    public Testimonials getTestimonials(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Testimonials> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Testimonials> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Testimonials.class)
    public static class TestimonialsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TestimonialsController controller = (TestimonialsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "testimonialsController");
            return controller.getTestimonials(getKey(value));
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
            if (object instanceof Testimonials) {
                Testimonials o = (Testimonials) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Testimonials.class.getName()});
                return null;
            }
        }

    }

}
