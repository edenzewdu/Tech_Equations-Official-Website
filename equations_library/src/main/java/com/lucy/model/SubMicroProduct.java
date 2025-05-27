package com.lucy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sub_micro_products")
public class SubMicroProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "micro_product_id", nullable = false)
    private MicroProduct microProduct;

    private String name;
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private Integer microProductId;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MicroProduct getMicroProduct() {
        return microProduct;
    }

    public void setMicroProduct(MicroProduct microProduct) {
        this.microProduct = microProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Integer getMicroProductId() {
        return microProductId;
    }

    public void setMicroProductId(Integer microProductId) {
        this.microProductId = microProductId;
    }
}
