/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Lucy
 */
@Entity
@Table(name = "pricing_plans")
@NamedQueries({
    @NamedQuery(name = "PricingPlans.findAll", query = "SELECT p FROM PricingPlans p"),
    @NamedQuery(name = "PricingPlans.findById", query = "SELECT p FROM PricingPlans p WHERE p.id = :id"),
    @NamedQuery(name = "PricingPlans.findByName", query = "SELECT p FROM PricingPlans p WHERE p.name = :name"),
    @NamedQuery(name = "PricingPlans.findByPrice", query = "SELECT p FROM PricingPlans p WHERE p.price = :price"),
    @NamedQuery(name = "PricingPlans.findByBillingFrequency", query = "SELECT p FROM PricingPlans p WHERE p.billingFrequency = :billingFrequency"),
    @NamedQuery(name = "PricingPlans.findByIsHighlighted", query = "SELECT p FROM PricingPlans p WHERE p.isHighlighted = :isHighlighted"),
    @NamedQuery(name = "PricingPlans.findByDisplayOrder", query = "SELECT p FROM PricingPlans p WHERE p.displayOrder = :displayOrder"),
    @NamedQuery(name = "PricingPlans.findByCreatedAt", query = "SELECT p FROM PricingPlans p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "PricingPlans.findByUpdatedAt", query = "SELECT p FROM PricingPlans p WHERE p.updatedAt = :updatedAt"),
    @NamedQuery(name = "PricingPlans.findByOriginalPrice", query = "SELECT p FROM PricingPlans p WHERE p.originalPrice = :originalPrice")})
public class PricingPlans implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private BigDecimal price;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "billing_frequency")
    private String billingFrequency;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "features")
    private String features;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_highlighted")
    private boolean isHighlighted;
    @Column(name = "display_order")
    private Integer displayOrder;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "original_price")
    private BigDecimal originalPrice;

    @jakarta.persistence.Transient
    private Integer tempId;
    @jakarta.persistence.Transient
    private Boolean validCell = false;

    public Integer getTempId() {
        return tempId;
    }

    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }

    public Boolean getValidCell() {
        return validCell;
    }

    public void setValidCell(Boolean validCell) {
        this.validCell = validCell;
    }

    public PricingPlans() {
    }

    public PricingPlans(String id) {
        this.id = id;
    }

    public PricingPlans(String id, String name, BigDecimal price, String billingFrequency, String features, boolean isHighlighted, Date createdAt, Date updatedAt, BigDecimal originalPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.billingFrequency = billingFrequency;
        this.features = features;
        this.isHighlighted = isHighlighted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.originalPrice = originalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBillingFrequency() {
        return billingFrequency;
    }

    public void setBillingFrequency(String billingFrequency) {
        this.billingFrequency = billingFrequency;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public boolean getIsHighlighted() {
        return isHighlighted;
    }

    public void setIsHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PricingPlans)) {
            return false;
        }
        PricingPlans other = (PricingPlans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.entity.PricingPlans[ id=" + id + " ]";
    }
    
}
