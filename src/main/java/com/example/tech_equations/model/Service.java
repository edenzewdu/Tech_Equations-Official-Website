package com.example.tech_equations.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;  // Specify the correct type (Long)

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public Service() {}

    public Service(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and setters
    public String getId() {  // Specify the return type as Long
        return id;
    }

    public void setId(String id) {  // Specify the parameter type as Long
        this.id = id;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
