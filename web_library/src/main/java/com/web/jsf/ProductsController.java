package com.web.jsf;

import com.web.entity.*;
import com.web.jsf.util.JsfUtil;
import com.web.jsf.util.JsfUtil.PersistAction;
import com.web.session.ProductsFacade;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;

import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@Named("productsController")
@ApplicationScoped
public class ProductsController implements Serializable {

    @EJB
    private com.web.session.ProductsFacade ejbFacade;
    @EJB
    private com.web.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<Products> items = null;
    private List<Products> multiselectionItems = null;
    private List<Products> createItems = null;
    private List<Products> editItems = null;
    private List<Products> filteredValues = null;
    private Products selected;
    private MicroProducts selectedMicro;
    private SubMicroProducts selectedSubMicro;
    private Products selectedProduct1;
    private MicroProducts selectedMicro1;
    private SubMicroProducts selectedSubMicro1;
    private Products selected2 = new Products();
    private String dataName = "Products";
    protected int first;

    public ProductsController() {
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

    public Products getSelected() {
        return selected;
    }

    public void setSelected(Products selected) {
        this.selected = selected;
    }

    public Products getSelectedProduct1() {
        return selectedProduct1;
    }

    public void setSelectedProduct1(Products selectedProduct1) {
        this.selectedProduct1 = selectedProduct1;
    }

    public MicroProducts getSelectedMicro() {
        return selectedMicro;
    }

    public void setSelectedMicro(MicroProducts selectedMicro) {
        this.selectedMicro = selectedMicro;
    }

    public MicroProducts getSelectedMicro1() {
        return selectedMicro1;
    }

    public void setSelectedMicro1(MicroProducts selectedMicro1) {
        this.selectedMicro1 = selectedMicro1;
    }

    public SubMicroProducts getSelectedSubMicro() {
        return selectedSubMicro;
    }

    public void setSelectedSubMicro(SubMicroProducts selectedSubMicro) {
        this.selectedSubMicro = selectedSubMicro;
    }

    public SubMicroProducts getSelectedSubMicro1() {
        return selectedSubMicro1;
    }

    public void setSelectedSubMicro1(SubMicroProducts selectedSubMicro1) {
        this.selectedSubMicro1 = selectedSubMicro1;
    }

    public Products getSelected2() {
        return selected2;
    }

    public void setSelected2(Products selected2) {
        this.selected2 = selected2;
    }

    private List<Products> products;
    private List<MicroProducts> MicroProductss;
    private List<SubMicroProducts> subMicroProductss;
    private List<MicroProducts> userSelectedMicroProducts;

    private Products selectedProduct = new Products();
    private MicroProducts selectedMicroProducts = new MicroProducts();
    private SubMicroProducts selectedSubMicroProducts = new SubMicroProducts();

    private String productName;
    private String productDescription;
    private String productImageUrl;
    private Integer selectedProductId;
    private Integer userSelectedProductId = 0;
    private Integer selectedMicroProductsId;

    private String microName;
    private String microDescription;
    private Double microPrice;
    private String microImageUrl;
    private Integer microStock;

    private String subMicroName;
    private String subMicroDescription;

    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        this.products = getFacade().findAll();
        this.MicroProductss = microProductsController.getItems();
        this.subMicroProductss = subMicroProductsController.getItems();
    }

    // ------------------------ PRODUCT ------------------------

    public void createProduct() {
        if (productName == null || productName.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product name required", null));
            return;
        }

        Products newProduct = new Products();
        newProduct.setName(productName);
        newProduct.setDescription(productDescription);
        newProduct.setImageUrl(productImageUrl);
        newProduct.setCreatedAt(new Date());
        newProduct.setUpdatedAt(new Date());

        boolean created = getFacade().create1(newProduct);

        productName = "";
        productDescription = "";
        productImageUrl = "";

        if (created) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product created successfully"));
            products = getFacade().findAll();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create product", null));
        }
    }
    public void onProductEdit(RowEditEvent<Products> event) {
        selectedProduct1 = event.getObject();
    }

    public void confirmProductEdit() {
        try {
            if (selectedProduct1 != null) {
                selectedProduct1.setUpdatedAt(new Date());
                getFacade().edit(selectedProduct1);
                JsfUtil.addSuccessMessage("Partner edit confirmed.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Edit confirmation failed.");
        }
    }

    public void cancelProductEdit() {
        selectedProduct1 = null;
        items = null;
    }
    public void prepareDeleteProduct(Products products) {
        this.selected = products;
    }

    public void deleteProduct() {
        try {
            if (selected != null) {
                getFacade().remove(selected);
                products = null;
                selected = null;
                JsfUtil.addSuccessMessage("Product deleted successfully.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Product deletion failed.");
        }
    }



    // ------------------------ MICRO PRODUCT ------------------------
    @Inject
    private MicroProductsController microProductsController;

    public void createMicroProducts() {
        MicroProducts micro = new MicroProducts();
        micro.setName(microName);
        micro.setDescription(microDescription);
        micro.setPrice(BigDecimal.valueOf(microPrice));
        micro.setImageUrl(microImageUrl);
        micro.setStock(microStock);
        micro.setCreatedAt(new Date());
        micro.setUpdatedAt(new Date());

        // Retrieve the Products entity using the selectedProductId
        Products product = em.find(Products.class, selectedProductId);
        if (product != null) {
            micro.setProductId(product); // Set the product_id field
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product not found", null));
            return;
        }

        // Create the micro product
        microProductsController.createMicroProduct(micro);
        MicroProductss = microProductsController.getItems();

        // Reset fields
        selectedProductId = null;
        microName = "";
        microDescription = "";
        microPrice = null;
        microImageUrl = "";
        microStock = null;



        // Query for the created MicroProducts
//        TypedQuery<MicroProducts> query = em.createQuery(
//                "SELECT m FROM MicroProducts m WHERE m.productId = :productId",
//                MicroProducts.class
//        );
//        query.setParameter("productId", selectedProductId);
//        MicroProductss = query.getResultList();
    }
    public void onMicroProductEdit(RowEditEvent<MicroProducts> event) {
        selectedMicro1 = event.getObject();
    }

    public void confirmMicroProductEdit() {
        try {
            if (selectedMicro1 != null) {
                selectedMicro1.setUpdatedAt(new Date());
                microProductsController.save(selectedMicro1);
                JsfUtil.addSuccessMessage("Partner edit confirmed.");
                MicroProductss = microProductsController.getItems();
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Edit confirmation failed.");
        }
    }
    public void cancelMicroEdit() {
        selectedMicro1 = null;
        items = null;
    }


    public List<MicroProducts> getMicroProductsByProductId() {
        TypedQuery<MicroProducts> query = em.createQuery(
                "SELECT m FROM MicroProducts m WHERE m.productId = :productId",
                MicroProducts.class
        );
        query.setParameter("productId", userSelectedProductId);
        MicroProductss = query.getResultList();
        return MicroProductss;
    }

    public void updateMicroProducts(MicroProducts micro) {
        micro.setUpdatedAt(new Date());
        microProductsController.save(micro);
        TypedQuery<MicroProducts> query = em.createQuery(
                "SELECT m FROM MicroProducts m WHERE m.productId = :productId",
                MicroProducts.class
        );
        query.setParameter("productId", userSelectedProductId);
        MicroProductss = query.getResultList();
    }
    public void prepareDeleteMicroProduct(MicroProducts microProducts) {
        this.selectedMicro = microProducts;
    }

    public void deleteMicroProduct() {
        try {
            if (selectedMicro != null) {
                microProductsController.removeRecord(selectedMicro);
                MicroProductss = null;
                selectedMicro = null;
                JsfUtil.addSuccessMessage("Micro Product deleted successfully.");
                MicroProductss = microProductsController.getItems();
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Micro Product deletion failed.");
        }
    }


    // ------------------------ SUB MICRO PRODUCT ------------------------
@Inject
private SubMicroProductsController subMicroProductsController;
    public void createSubMicroProducts() {
        SubMicroProducts sub = new SubMicroProducts();
        sub.setId(selectedMicroProductsId);
        sub.setName(subMicroName);
        sub.setDescription(subMicroDescription);
        sub.setCreatedAt(new Date());
        sub.setUpdatedAt(new Date());

        // Retrieve the MicroProducts entity using the selectedMicroProductsId
        MicroProducts microProduct = em.find(MicroProducts.class, selectedMicroProductsId);
        if (microProduct != null) {
            sub.setMicroProductId(microProduct); // Set the association
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Micro Product not found", null));
            return;
        }

        // Save the SubMicroProducts
        subMicroProductsController.createSubMicroProducts(sub);
        subMicroProductss = subMicroProductsController.getItems();

        selectedMicroProductsId = null;
        subMicroName = "";
        subMicroDescription = "";
        // Query for the associated SubMicroProducts
        TypedQuery<SubMicroProducts> query = em.createQuery(
                "SELECT s FROM SubMicroProducts s WHERE s.microProductId.id = :microProductId",
                SubMicroProducts.class
        );
        query.setParameter("microProductId", selectedMicroProductsId);
        subMicroProductss = query.getResultList(); // Already fetched sub micro products
    }
    public void onSubMicroProductEdit(RowEditEvent<SubMicroProducts> event) {
        selectedSubMicro1 = event.getObject();
    }

    public void confirmSubMicroProductEdit() {
        try {
            if (selectedSubMicro1 != null) {
                selectedSubMicro1.setUpdatedAt(new Date());
                subMicroProductsController.save(selectedSubMicro1);
                JsfUtil.addSuccessMessage("Partner edit confirmed.");
                subMicroProductss = subMicroProductsController.getItems();
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Edit confirmation failed.");
        }
    }

    public void cancelSubMicroEdit() {
        selectedSubMicro1 = null;
        items = null;
    }
    public List<SubMicroProducts> getSubMicroProductssByMicroProductsId(int MicroProductsId) {
        TypedQuery<SubMicroProducts> query = em.createQuery(
                "SELECT s FROM SubMicroProducts s WHERE s.microProductId.id = :microProductId",
                SubMicroProducts.class
        );
        query.setParameter("microProductId", MicroProductsId);
        subMicroProductss = query.getResultList();
        return subMicroProductss;
    }

    public void updateSubMicroProducts(SubMicroProducts sub) {
        sub.setUpdatedAt(new Date());
       subMicroProductsController.save(sub);
        TypedQuery<SubMicroProducts> query = em.createQuery(
                "SELECT s FROM SubMicroProducts s WHERE s.microProductId.id = :microProductId",
                SubMicroProducts.class
        );
        query.setParameter("microProductId", sub.getMicroProductId());
        subMicroProductss = query.getResultList();
    }

    public void prepareDeleteSubMicroProduct(SubMicroProducts subMicroProducts) {
        this.selectedSubMicro = subMicroProducts;
    }

    public void deleteSubMicroProduct() {
        try {
            if (selectedSubMicro != null) {
                subMicroProductsController.removeRecord(selectedSubMicro);
                subMicroProductss = null;
                selectedSubMicro = null;
                JsfUtil.addSuccessMessage("Sub Micro Product deleted successfully.");
                subMicroProductss = subMicroProductsController.getItems();
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Sub Micro Product deletion failed.");
        }
    }



    // ------------------------ NAVIGATION ------------------------


    public String goToProductDetailPage(int productId) throws IOException {
        System.out.println("goToProductDetailPage called with productId = " + productId);
        if (productId != 0) {
            userSelectedProductId = productId;
            String jpql =   "SELECT mp FROM  MicroProducts mp " +
                    "LEFT JOIN SubMicroProducts smp  ON mp.id = smp.id WHERE mp.productId.id = :productId";
            userSelectedMicroProducts = em.createQuery(jpql, MicroProducts.class)
                    .setParameter("productId", productId)
                    .getResultList();;
            return "/website/public/product.xhtml?faces-redirect=true&id=" + productId;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a product", null));
        return null;
    }


    // ------------------------ GETTERS & SETTERS ------------------------

    public List<Products> getProducts() {
        return products = getFacade().findAll();
    }

    public List<MicroProducts> getMicroProductss() {
        return MicroProductss;
    }

    public List<SubMicroProducts> getSubMicroProductss() {
        return subMicroProductss;
    }

    public Integer getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(Integer selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    public Products getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Products selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public MicroProducts getSelectedMicroProducts() {
        return selectedMicroProducts;
    }

    public void setSelectedMicroProducts(MicroProducts selectedMicroProducts) {
        this.selectedMicroProducts = selectedMicroProducts;
    }

    public Integer getSelectedMicroProductsId() {
        return selectedMicroProductsId;
    }

    public void setSelectedMicroProductsId(Integer selectedMicroProductsId) {
        this.selectedMicroProductsId = selectedMicroProductsId;
    }

    public SubMicroProducts getSelectedSubMicroProducts() {
        return selectedSubMicroProducts;
    }

    public void setSelectedSubMicroProducts(SubMicroProducts selectedSubMicroProducts) {
        this.selectedSubMicroProducts = selectedSubMicroProducts;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public Double getMicroPrice() {
        return microPrice;
    }

    public void setMicroPrice(Double microPrice) {
        this.microPrice = microPrice;
    }

    public Integer getMicroStock() {
        return microStock;
    }

    public void setMicroStock(Integer microStock) {
        this.microStock = microStock;
    }

    public String getMicroDescription() {
        return microDescription;
    }

    public void setMicroDescription(String microDescription) {
        this.microDescription = microDescription;
    }

    public String getMicroName() {
        return microName;
    }

    public void setMicroName(String microName) {
        this.microName = microName;
    }

    public String getMicroImageUrl() {
        return microImageUrl;
    }

    public void setMicroImageUrl(String microImageUrl) {
        this.microImageUrl = microImageUrl;
    }

    public String getSubMicroDescription() {
        return subMicroDescription;
    }

    public void setSubMicroDescription(String subMicroDescription) {
        this.subMicroDescription = subMicroDescription;
    }

    public String getSubMicroName() {
        return subMicroName;
    }

    public void setSubMicroName(String subMicroName) {
        this.subMicroName = subMicroName;
    }

    public void setMicroProductss(List<MicroProducts> MicroProductss) {
        this.MicroProductss = MicroProductss;
    }

     public void setProducts(List<Products> products) {
        this.products = products;
    }

    public Integer getUserSelectedProductId() {
        return userSelectedProductId;
    }

    public void setUserSelectedProductId(Integer userSelectedProductId) {
        this.userSelectedProductId = userSelectedProductId;
    }

    public void setSubMicroProductss(List<SubMicroProducts> subMicroProductss) {
        this.subMicroProductss = subMicroProductss;
    }

    public List<MicroProducts> getUserSelectedMicroProducts() {
        return this.userSelectedMicroProducts;
    }

    public void setUserSelectedMicroProducts(List<MicroProducts> userSelectedMicroProducts) {
        this.userSelectedMicroProducts = userSelectedMicroProducts;
    }

    public void cancelUpdate() {
        selectedProduct1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (Products item : getCreateItems()) {
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

    private ProductsFacade getFacade() {
        return ejbFacade;
    }

    public Products prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new Products();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Products prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Products prepareCreateInCreate() {

        selectedProduct1 = new Products();
        createItems = preparingTempId(selectedProduct1, createItems);
        initializeEmbeddableKey();
        return selectedProduct1;
    }

    public Products prepareCreate1() {
        selected = new Products();
        int tempId = 0;
        for (Products p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public Products prepareCreateInEdit() {

        selectedProduct1 = new Products();
        editItems = preparingTempId(selectedProduct1, editItems);
        initializeEmbeddableKey();
        return selectedProduct1;
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
        createItems.add(new Products());
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

        for (Products item : getCreateItems()) {
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
        for (Products item : getEditItems()) {
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

        for (Products item : getEditItems()) {
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
        getFacade().create(selectedProduct1);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE);
    }

    public void removeInCreate(Products item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(Products item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(Products item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<Products> aList) {

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

    public List<Products> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Products> items) {
        this.items = items;
    }

    public List<Products> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<Products> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<Products> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<Products> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<Products> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<Products> createItems) {
        this.createItems = createItems;
    }

    public List<Products> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<Products> editItems) {
        this.editItems = editItems;
    }

    private List<Products> preparingTempId(Products item, List<Products> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (Products itm : aList) {
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
            for (Products item : multiselectionItems) {
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

    public Products getProducts(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Products> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Products> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Products.class)
    public static class ProductsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductsController controller = (ProductsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "productsController");
            return controller.getProducts(getKey(value));
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
            if (object instanceof Products) {
                Products o = (Products) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Products.class.getName()});
                return null;
            }
        }

    }

}
