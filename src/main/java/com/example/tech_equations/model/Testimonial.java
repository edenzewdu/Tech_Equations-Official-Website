package com.example.tech_equations.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "testimonials")
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "author_title")
    private String authorTitle;

    @Column(name = "quote_text", nullable = false)
    private String quoteText;

    @Column(name = "is_published", nullable = false)
    private boolean isPublished = false;

    @Column(name = "display_order", nullable = false)
    private int displayOrder = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "avatar_url")
    private String avatarUrl;

    public Testimonial() {
    }

    // Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorTitle() {
        return authorTitle;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
