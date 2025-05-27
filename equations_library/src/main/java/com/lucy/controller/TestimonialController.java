package com.lucy.controller;

import com.lucy.model.Testimonial;
import com.lucy.repository.TestimonialRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named("testimonialController")
@SessionScoped
public class TestimonialController implements Serializable {

    private final TestimonialRepository testimonialRepository;

    private String authorName;
    private String authorTitle;
    private String quoteText;
    private boolean published;
    private int displayOrder;
    private String searchTerm;
    private String avatarUrl;

    private List<Testimonial> testimonials;
    private List<Testimonial> publishedTestimonials;
    private Testimonial testimonial = new Testimonial();

    private boolean showCreateForm = false;
    private Testimonial selectedTestimonial;
    private Testimonial tempEditedTestimonial;
    private List<Testimonial> filteredTestimonial;


    @Inject
    public TestimonialController(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @PostConstruct
    public void init() {
        loadTestimonials();
    }

    public void loadTestimonials() {
        this.testimonials = testimonialRepository.getAllTestimonials();
    }

    public List<Testimonial> getTestimonials() {
        return testimonials;
    }

    public List<Testimonial> searchTestimonials() {
        this.testimonials = testimonialRepository.searchTestimonials(searchTerm);
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
            Testimonial newTestimonial = new Testimonial();
            newTestimonial.setAuthorName(authorName);
            newTestimonial.setAuthorTitle(authorTitle);
            newTestimonial.setQuoteText(quoteText);
            newTestimonial.setPublished(published);
            newTestimonial.setDisplayOrder(displayOrder);
            newTestimonial.setAvatarUrl(avatarUrl);
            newTestimonial.setCreatedAt(LocalDateTime.now());
            newTestimonial.setUpdatedAt(LocalDateTime.now());

            boolean created = testimonialRepository.saveTestimonial(newTestimonial);

            if (created) {
                if (testimonialRepository.countPublishedWithSameDisplayOrder(displayOrder) > 0) {
                    PrimeFaces.current().executeScript("PF('WarningDialogWidget').show();");
                }


                context.addMessage(null, new FacesMessage("Testimonial created successfully"));
                loadTestimonials();
                this.testimonial = new Testimonial(); // Reset form
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

    public void deleteTestimonial(Testimonial testimonial) {
        testimonialRepository.deleteTestimonialById(testimonial.getId());
        loadTestimonials();
    }
    public void deleteTestimonial() {
        try {
            if (selectedTestimonial != null) {
                testimonialRepository.deleteTestimonialById(selectedTestimonial.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Testimonial deleted successfully"));
                loadTestimonials();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Testimonial not selected", "Please select a testimonial first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete testimonial"));
        }
        testimonialRepository.deleteTestimonialById(testimonial.getId());
        loadTestimonials();
    }
    public void save() {
        if (testimonial.getId() == null) {
            testimonial.setCreatedAt(LocalDateTime.now());
            testimonial.setUpdatedAt(LocalDateTime.now());
            testimonialRepository.saveTestimonial(testimonial);
        } else {
            testimonial.setUpdatedAt(LocalDateTime.now());
            testimonialRepository.editTestimonial(testimonial);
        }
        clearForm();
        loadTestimonials();
    }

    public void onRowEdit(RowEditEvent<Testimonial> event) {
        tempEditedTestimonial = event.getObject(); // Store temporarily
    }

    public void confirmEdit() {
        if (tempEditedTestimonial != null) {
            tempEditedTestimonial.setUpdatedAt(LocalDateTime.now());
            testimonialRepository.updateTestimonial(tempEditedTestimonial);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Testimonial edited successfully"));
            loadTestimonials(); // Refresh list
            if (testimonialRepository.countPublishedWithSameDisplayOrder(tempEditedTestimonial.getDisplayOrder()) > 0) {
                PrimeFaces.current().executeScript("PF('WarningDialogWidget').show();");
            }

            tempEditedTestimonial = null;
        }
    }
    public void cancelEdit() {
        this.testimonials = testimonialRepository.getAllTestimonials();
        // Optionally reload data or reset table
        tempEditedTestimonial = null;
    }

    public void clearForm() {
        this.testimonial = new Testimonial();
    }

    public void editTestimonial(Testimonial selectedTestimonial) {
        this.testimonial = selectedTestimonial;
    }

    public void prepareTogglePublished(Testimonial testimonial) {
        this.selectedTestimonial = testimonial;
    }

    public void togglePublishedStatus() {
        if (selectedTestimonial != null) {
            selectedTestimonial.setPublished(selectedTestimonial.getPublished());
            selectedTestimonial.setUpdatedAt(LocalDateTime.now()); // ✅ Update timestamp

            testimonialRepository.saveTestimonial(selectedTestimonial);

            if (testimonialRepository.countPublishedWithSameDisplayOrder(selectedTestimonial.getDisplayOrder()) > 0) {
                PrimeFaces.current().executeScript("PF('WarningDialogWidget').show();");
            }

        }
    }


    // Getters and Setters
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorTitle() { return authorTitle; }
    public void setAuthorTitle(String authorTitle) { this.authorTitle = authorTitle; }

    public String getQuoteText() { return quoteText; }
    public void setQuoteText(String quoteText) { this.quoteText = quoteText; }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Testimonial getTestimonial() { return testimonial; }

    public List<Testimonial> getPublishedTestimonials() {
        if (publishedTestimonials == null) {
            publishedTestimonials = testimonialRepository.getPublishedTestimonials();
        }
        return publishedTestimonials;
    }
    public void setTestimonial(Testimonial testimonial) { this.testimonial = testimonial; }
}
