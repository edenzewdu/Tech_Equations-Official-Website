package com.lucy.controller;

import com.lucy.model.Product;
import com.lucy.model.MicroProduct;
import com.lucy.model.Service;
import com.lucy.model.SubMicroProduct;
import com.lucy.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named("productController")
@SessionScoped
public class ProductController implements Serializable {

    @Inject
    private ProductRepository productRepository;

    private List<Product> products;
    private List<MicroProduct> microProducts;
    private List<SubMicroProduct> subMicroProducts;
    private List<MicroProduct> userSelectedMicroProduct;

    private Product selectedProduct = new Product();
    private MicroProduct selectedMicroProduct = new MicroProduct();
    private SubMicroProduct selectedSubMicroProduct = new SubMicroProduct();

    private String productName;
    private String productDescription;
    private String productImageUrl;
    private Integer selectedProductId;
    private Integer userSelectedProductId = 0;
    private Integer selectedMicroProductId;

    private String microName;
    private String microDescription;
    private  Double microPrice;
    private String microImageUrl;
    private Integer microStock;

    private String subMicroName;
    private String subMicroDescription;

    private Product tempEditedProduct;
    private MicroProduct tempEditedMicroProduct;
    private SubMicroProduct tempEditedSubMicroProduct;
    private Product product = new Product();
    private MicroProduct microProduct = new MicroProduct();
    private SubMicroProduct subMicroProduct = new SubMicroProduct();

    @PostConstruct
    public void init() {
        products = productRepository.getAllProducts();
        microProducts = productRepository.getAllMicroProducts();
        subMicroProducts = productRepository.getAllSubMicroProducts();
    }

    // ------------------------ PRODUCT ------------------------

    public void createProduct() {
        if (productName == null || productName.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product name required", null));
            return;
        }

        Product newProduct = new Product();
        newProduct.setName(productName);
        newProduct.setDescription(productDescription);
        newProduct.setImageUrl(productImageUrl);
        newProduct.setCreatedAt(LocalDateTime.now());
        newProduct.setUpdatedAt(LocalDateTime.now());

        boolean created = productRepository.saveProduct(newProduct);
        if (created) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product created successfully"));
            products = productRepository.getAllProducts();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create product", null));
        }
    }

    public void deleteProduct() {
        try {
            if (selectedProduct != null) {
                productRepository.deleteProductById(selectedProduct.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product deleted successfully"));
                products = productRepository.getAllProducts();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product not selected", "Please select a Product first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete product"));
        }
        productRepository.deleteProductById(product.getId());
        products = productRepository.getAllProducts();
    }
    public void onProductEdit(CellEditEvent event) {
        Product edited = (Product) ((DataTable) event.getSource()).getRowData();
        try {
            productRepository.updateProduct(edited);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Product Updated", edited.getName()));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", e.getMessage()));
        }
    }



    // ------------------------ MICRO PRODUCT ------------------------

    public void createMicroProduct() {
        MicroProduct micro = new MicroProduct();
        micro.setProductId(selectedProductId);
        micro.setName(microName);
        micro.setDescription(microDescription);
        micro.setPrice(microPrice);
        micro.setImageUrl(microImageUrl);
        micro.setStock(microStock);
        micro.setCreatedAt(LocalDateTime.now());
        micro.setUpdatedAt(LocalDateTime.now());

        productRepository.createMicroProduct(micro);
        microProducts = productRepository.getMicroProductsByProductId(selectedProductId);
    }


    public List<MicroProduct> getMicroProductsByProductId() {
        microProducts = productRepository.getMicroProductsByProductId(userSelectedProductId);
        return microProducts;
    }

    public void updateMicroProduct(MicroProduct micro) {
        micro.setUpdatedAt(LocalDateTime.now());
        productRepository.updateMicroProduct(micro);
        microProducts = productRepository.getMicroProductsByProductId(micro.getProductId());
    }

    public void deleteMicroProduct() {
        try {
            if (selectedMicroProduct != null) {
                productRepository.deleteMicroProduct(selectedMicroProduct.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product deleted successfully"));
                products = productRepository.getAllProducts();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product not selected", "Please select a product first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete product"));
        }
        productRepository.deleteMicroProduct(microProduct.getId());
        microProducts = productRepository.getAllMicroProducts();
    }
    public void onMicroProductEdit(CellEditEvent event) {
        MicroProduct edited = (MicroProduct) ((DataTable) event.getSource()).getRowData();
        try {
            productRepository.updateMicroProduct(edited);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "MicroProduct Updated", edited.getName()));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", e.getMessage()));
        }
    }

    // ------------------------ SUB MICRO PRODUCT ------------------------

    public void createSubMicroProduct() {
        SubMicroProduct sub = new SubMicroProduct();
        sub.setMicroProductId(selectedMicroProductId);
        sub.setName(subMicroName);
        sub.setDescription(subMicroDescription);
        sub.setCreatedAt(LocalDateTime.now());
        sub.setUpdatedAt(LocalDateTime.now());

        productRepository.createSubMicroProduct(sub);
        subMicroProducts = productRepository.getSubMicroProductsByMicroId(selectedMicroProduct.getId());
    }


    public List<SubMicroProduct> getSubMicroProductsByMicroProductId(int microProductId) {
        subMicroProducts = productRepository.getSubMicroProductsByMicroId(microProductId);
        return subMicroProducts;
    }

    public void updateSubMicroProduct(SubMicroProduct sub) {
        sub.setUpdatedAt(LocalDateTime.now());
        productRepository.updateSubMicroProduct(sub);
        subMicroProducts = productRepository.getSubMicroProductsByMicroId(sub.getMicroProductId());
    }

    public void deleteSubMicroProduct() {
        try {
            if (selectedSubMicroProduct != null) {
                productRepository.deleteSubMicroProduct(selectedSubMicroProduct.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product deleted successfully"));
                products = productRepository.getAllProducts();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product not selected", "Please select a product first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete product"));
        }
        productRepository.deleteSubMicroProduct(subMicroProduct.getId());
        subMicroProducts = productRepository.getAllSubMicroProducts();
    }

    public void onSubMicroProductEdit(CellEditEvent event) {
        SubMicroProduct edited = (SubMicroProduct) ((DataTable) event.getSource()).getRowData();
        try {
            productRepository.updateSubMicroProduct(edited);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SubMicroProduct Updated", edited.getName()));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", e.getMessage()));
        }
    }


    // ------------------------ NAVIGATION ------------------------


//    public String goToProductDetailPage() {
//        System.out.println("goToProductDetailPage called with productId = " + userSelectedProductId);
//
//        if (userSelectedProductId <= 0) {
//            System.out.println("Invalid productId, skipping...");
//            return null;
//        }
//
//        userSelectedMicroProduct = productRepository.getMicroProductsWithSubsByProductId(userSelectedProductId);
//
//        return "/public/product.xhtml?faces-redirect=true&id=" + userSelectedProductId;
//    }

    public String goToProductDetailPage(int productId) throws IOException {
        System.out.println("goToProductDetailPage called with productId = " + productId);
        if (productId != 0) {
            userSelectedProductId = productId;
            userSelectedMicroProduct = productRepository.getMicroProductsWithSubsByProductId(productId);
            return "/public/product.xhtml?faces-redirect=true&id=" + productId;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a product", null));
        return null;
    }




    // ------------------------ GETTERS & SETTERS ------------------------

    public List<Product> getProducts() {
        return products = productRepository.getAllProducts();
    }

    public List<MicroProduct> getMicroProducts() {
        return microProducts;
    }

    public List<SubMicroProduct> getSubMicroProducts() {
        return subMicroProducts;
    }

    public Integer getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(Integer selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public MicroProduct getSelectedMicroProduct() {
        return selectedMicroProduct;
    }

    public void setSelectedMicroProduct(MicroProduct selectedMicroProduct) {
        this.selectedMicroProduct = selectedMicroProduct;
    }

    public Integer getSelectedMicroProductId() {
        return selectedMicroProductId;
    }

    public void setSelectedMicroProductId(Integer selectedMicroProductId) {
        this.selectedMicroProductId = selectedMicroProductId;
    }

    public SubMicroProduct getSelectedSubMicroProduct() {
        return selectedSubMicroProduct;
    }

    public void setSelectedSubMicroProduct(SubMicroProduct selectedSubMicroProduct) {
        this.selectedSubMicroProduct = selectedSubMicroProduct;
    }

    public void clearForm() {
        this.product = new Product();
        this.microProduct = new MicroProduct();
        this.subMicroProduct = new SubMicroProduct();
    }

    public void prepareDeleteProduct(Product product) {
        this.selectedProduct = product;
    }
    public void prepareDeleteMicroProduct(MicroProduct microProduct) {
        this.selectedMicroProduct = microProduct;
    }
    public void prepareDeleteSubMicroProduct(SubMicroProduct subMicroProduct) {
        this.selectedSubMicroProduct = subMicroProduct;
    }
    public void onRowProductEdit(RowEditEvent<Product> event) {
        tempEditedProduct = event.getObject(); // Store temporarily
    }

    public void confirmProductEdit() {
        if (tempEditedProduct != null) {
            tempEditedProduct.setUpdatedAt(LocalDateTime.now());
            productRepository.updateProduct(tempEditedProduct);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product edited successfully"));
            productRepository.getAllProducts(); // Refresh list
            tempEditedProduct = null;
        }
    }
    public void cancelProductEdit() {
        this.products = productRepository.getAllProducts();
        // Optionally reload data or reset table
        tempEditedProduct = null;
    }
    public void onRowMicroProductEdit(RowEditEvent<MicroProduct> event) {
        tempEditedMicroProduct = event.getObject(); // Store temporarily
    }

    public void confirmMicroProductEdit() {
        if (tempEditedMicroProduct != null) {
            tempEditedMicroProduct.setUpdatedAt(LocalDateTime.now());
            productRepository.updateMicroProduct(tempEditedMicroProduct);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Micro Product edited successfully"));
            productRepository.getAllMicroProducts(); // Refresh list
            tempEditedMicroProduct = null;
        }
    }
    public void cancelMicroProductEdit() {
        this.microProducts = productRepository.getAllMicroProducts();
        // Optionally reload data or reset table
        tempEditedMicroProduct = null;
    }

    public void onRowSubMicroProductEdit(RowEditEvent<SubMicroProduct> event) {
        tempEditedSubMicroProduct = event.getObject(); // Store temporarily
    }

    public void confirmSubMicroProductEdit() {
        if (tempEditedSubMicroProduct != null) {
            tempEditedSubMicroProduct.setUpdatedAt(LocalDateTime.now());
            productRepository.updateSubMicroProduct(tempEditedSubMicroProduct);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Micro Product edited successfully"));
            productRepository.getAllSubMicroProducts(); // Refresh list
            tempEditedSubMicroProduct = null;
        }
    }
    public void cancelSubMicroProductEdit() {
        this.subMicroProducts = productRepository.getAllSubMicroProducts();
        // Optionally reload data or reset table
        tempEditedSubMicroProduct = null;
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

    public void setMicroProducts(List<MicroProduct> microProducts) {
        this.microProducts = microProducts;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getUserSelectedProductId() {
        return userSelectedProductId;
    }

    public void setUserSelectedProductId(Integer userSelectedProductId) {
        this.userSelectedProductId = userSelectedProductId;
    }

    public void setSubMicroProducts(List<SubMicroProduct> subMicroProducts) {
        this.subMicroProducts = subMicroProducts;
    }
    public List<MicroProduct> getMicroProductsWithSubsByProductId(){
        return this.userSelectedMicroProduct = productRepository.getMicroProductsWithSubsByProductId(userSelectedProductId);
    }
    public List<MicroProduct> getUserSelectedMicroProduct() {
        return this.userSelectedMicroProduct;
    }

    public void setUserSelectedMicroProduct(List<MicroProduct> userSelectedMicroProduct) {
        this.userSelectedMicroProduct = userSelectedMicroProduct;
    }

}
