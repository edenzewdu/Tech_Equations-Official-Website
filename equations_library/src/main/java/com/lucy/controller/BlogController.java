package com.lucy.controller;

import com.lucy.model.Blog;
import com.lucy.repository.BlogRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named("blogController")
@ApplicationScoped
public class BlogController implements Serializable {

    private final BlogRepository blogRepository;

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
    private boolean showCreateForm = false;

    @Inject
    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @PostConstruct
    public void init() {
        loadBlogs();
    }

    public void loadBlogs() {
        this.blogs = blogRepository.getAllBlogs();
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public List<Blog> searchBlogs() {
        blogs = blogRepository.searchBlogs(searchTerm);
        return blogs;
    }

    public Blog findBlogById(String id) {
        return blogRepository.getBlogById(id);
    }

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
            newBlog.setTitle(title);
            newBlog.setCategory(category);
            newBlog.setImageUrl(imageUrl);
            newBlog.setSummary(summary);
            newBlog.setContent(content);
            newBlog.setCreatedAt(LocalDateTime.now());

            boolean created = blogRepository.saveBlog(newBlog);

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

    // CRUD Operations

    public void deleteBlog() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (selectedBlog != null) {
                blogRepository.deleteBlogById(selectedBlog.getId());
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

    public void save() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (blog.getId() == null) {
            blog.setCreatedAt(LocalDateTime.now());
            boolean saved = blogRepository.saveBlog(blog);
            if (saved) {
                context.addMessage(null, new FacesMessage("Blog saved successfully"));
            }
        } else {
            blogRepository.updateBlog(blog);
            context.addMessage(null, new FacesMessage("Blog updated successfully"));
        }
        clearForm();
        loadBlogs();
    }

    public void onRowEdit(RowEditEvent<Blog> event) {
        tempEditedBlog = event.getObject(); // Store temporarily
    }

    public void confirmEdit() {
        if (tempEditedBlog != null) {
            tempEditedBlog.setCreatedAt(tempEditedBlog.getCreatedAt() != null ? tempEditedBlog.getCreatedAt() : LocalDateTime.now());
            blogRepository.updateBlog(tempEditedBlog);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Blog edited successfully"));
            loadBlogs(); // Refresh list
            tempEditedBlog = null;
        }
    }

    public void cancelEdit() {
        this.blogs = blogRepository.getAllBlogs();
        tempEditedBlog = null;
    }

    public void clearForm() {
        this.blog = new Blog();
    }

    public void prepareDelete(Blog blog) {
        this.selectedBlog = blog;
    }

    public void edit(Blog selectedBlog) {
        this.blog = selectedBlog;
    }

    // Getters and Setters

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Blog getBlog() { return blog; }
    public void setBlog(Blog blog) { this.blog = blog; }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }

    public Blog getSelectedBlog() { return selectedBlog; }
    public void setSelectedBlog(Blog selectedBlog) { this.selectedBlog = selectedBlog; }
}
