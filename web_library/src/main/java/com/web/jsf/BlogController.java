package com.web.jsf;

import com.web.entity.Blog;
import com.web.entity.ImpactStats;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.BlogFacade;

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
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;

import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

@Named("blogController")
@ApplicationScoped
public class BlogController implements Serializable {

    @EJB
    private com.web.session.BlogFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<Blog> items = null;
    private List<Blog> multiselectionItems = null;
    private List<Blog> createItems = null;
    private List<Blog> editItems = null;
    private List<Blog> filteredValues = null;
    private Blog selected;
    private Blog selected1;
    private Blog selected2 = new Blog();
    private String dataName = "Blog";
    protected int first;

    private String title;
    private String category;
    private String imageUrl;
    private String summary;
    private String content;
    private String searchTerm;

    private Blog tempEditedBlog;
    private Blog selectedBlog;

    private List<Blog> blogs;
    private List<Blog> filteredBlogs;
    private Blog blog = new Blog();
    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public BlogController() {
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

    public Blog getSelected() {
        return selected;
    }

    public void setSelected(Blog selected) {
        this.selected = selected;
    }

    public Blog getSelected1() {
        return selected1;
    }

    public void setSelected1(Blog selected1) {
        this.selected1 = selected1;
    }

    public Blog getSelected2() {
        return selected2;
    }

    public void setSelected2(Blog selected2) {
        this.selected2 = selected2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    @PostConstruct
    public void init() {

        blogs = getFacade().findAll();
    }
    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
    public void prepareDelete(Blog blog) {
        this.selectedBlog = blog;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }
    public void loadBlogs() {
        this.blogs =getFacade().findAll();
    }
    public void deleteBlog() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (selectedBlog != null) {
                removeRecord(selectedBlog);
                context.addMessage(null, new FacesMessage("Blog deleted successfully"));
                loadBlogs();
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Blog not selected", "Please select a blog first"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Delete failed", "Could not delete blog"));
        }
    }

    public void discard() {
        selected = null;
        for (Blog item : getCreateItems()) {
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
    private boolean showCreateForm = false;

    public String createBlog() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (title == null || summary == null || content == null ||
                title.isEmpty() || summary.isEmpty() || content.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: Title, summary, and content are required", null));
            return null;
        }

        try {
            Blog newBlog = new Blog();
            newBlog.setId(UUID.randomUUID().toString());
            newBlog.setTitle(title);
            newBlog.setCategory(category);
            newBlog.setImageUrl(imageUrl);
            newBlog.setSummary(summary);
            newBlog.setContent(content);
            newBlog.setCreatedAt(new Date());
            newBlog.setUpdatedAt(new Date());

            boolean created = getFacade().create1(newBlog);
            // Reset fields
            title  = "";
            category = "";
            imageUrl = "";
            summary = "";
            content = "";
            items = null;

            if (created) {
                context.addMessage(null, new FacesMessage("Blog created successfully"));
                loadBlogs(); // Refresh the list
                showCreateForm = false;
                return "blog-list?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error: Blog creation failed", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger if available
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: Unexpected error", null));
            return null;
        }
    }

    public void onRowEdit(RowEditEvent<Blog> event) {

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

    private BlogFacade getFacade() {
        return ejbFacade;
    }

    public Blog prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new Blog();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Blog prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Blog prepareCreateInCreate() {

        selected1 = new Blog();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public Blog prepareCreate1() {
        selected = new Blog();
        int tempId = 0;
        for (Blog p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public Blog prepareCreateInEdit() {

        selected1 = new Blog();
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
        createItems.add(new Blog());
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

        for (Blog item : getCreateItems()) {
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
        for (Blog item : getEditItems()) {
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

        for (Blog item : getEditItems()) {
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

    public void removeInCreate(Blog item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(Blog item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(Blog item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<Blog> aList) {

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

    public List<Blog> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Blog> items) {
        this.items = items;
    }

    public List<Blog> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<Blog> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<Blog> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<Blog> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<Blog> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<Blog> createItems) {
        this.createItems = createItems;
    }

    public List<Blog> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<Blog> editItems) {
        this.editItems = editItems;
    }

    private List<Blog> preparingTempId(Blog item, List<Blog> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (Blog itm : aList) {
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
            for (Blog item : multiselectionItems) {
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
    public List<Blog> searchBlog() {
        String likeTerm = "%" + searchTerm.toLowerCase() + "%";

        TypedQuery<Blog> query = em.createQuery(
                "SELECT b FROM Blog b WHERE LOWER(b.title) LIKE :searchTerm " +
                        "OR b.content = :searchTerm "+
                        "OR b.imageUrl = :searchTerm "+
                        "OR b.category = :searchTerm "+
                        "OR b.summary = :searchTerm",
                Blog.class
        );
        query.setParameter("searchTerm", likeTerm);

        blogs = query.getResultList();
        return blogs;
    }
    public Blog getBlog(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Blog> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Blog> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Blog.class)
    public static class BlogControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BlogController controller = (BlogController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "blogController");
            return controller.getBlog(getKey(value));
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
            if (object instanceof Blog) {
                Blog o = (Blog) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Blog.class.getName()});
                return null;
            }
        }

    }

}
