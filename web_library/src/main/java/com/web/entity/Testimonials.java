/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web.entity;

import java.io.Serializable;
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
@Table(name = "testimonials")
@NamedQueries({
    @NamedQuery(name = "Testimonials.findAll", query = "SELECT t FROM Testimonials t"),
    @NamedQuery(name = "Testimonials.findById", query = "SELECT t FROM Testimonials t WHERE t.id = :id"),
    @NamedQuery(name = "Testimonials.findByAuthorName", query = "SELECT t FROM Testimonials t WHERE t.authorName = :authorName"),
    @NamedQuery(name = "Testimonials.findByAuthorTitle", query = "SELECT t FROM Testimonials t WHERE t.authorTitle = :authorTitle"),
    @NamedQuery(name = "Testimonials.findByIsPublished", query = "SELECT t FROM Testimonials t WHERE t.isPublished = :isPublished"),
    @NamedQuery(name = "Testimonials.findByDisplayOrder", query = "SELECT t FROM Testimonials t WHERE t.displayOrder = :displayOrder"),
    @NamedQuery(name = "Testimonials.findByCreatedAt", query = "SELECT t FROM Testimonials t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "Testimonials.findByUpdatedAt", query = "SELECT t FROM Testimonials t WHERE t.updatedAt = :updatedAt"),
    @NamedQuery(name = "Testimonials.findByAvatarUrl", query = "SELECT t FROM Testimonials t WHERE t.avatarUrl = :avatarUrl")})
public class Testimonials implements Serializable {

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
    @Column(name = "author_name")
    private String authorName;
    @Size(max = 255)
    @Column(name = "author_title")
    private String authorTitle;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "quote_text")
    private String quoteText;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_published")
    private boolean isPublished;
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
    @Size(max = 2048)
    @Column(name = "avatar_url")
    private String avatarUrl;

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

    public Testimonials() {
    }

    public Testimonials(String id) {
        this.id = id;
    }

    public Testimonials(String id, String authorName, String quoteText, boolean isPublished, Date createdAt, Date updatedAt) {
        this.id = id;
        this.authorName = authorName;
        this.quoteText = quoteText;
        this.isPublished = isPublished;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(boolean isPublished) {
        this.isPublished = isPublished;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
        if (!(object instanceof Testimonials)) {
            return false;
        }
        Testimonials other = (Testimonials) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.entity.Testimonials[ id=" + id + " ]";
    }
    
}
