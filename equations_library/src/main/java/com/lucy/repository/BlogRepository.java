package com.lucy.repository;

import com.lucy.model.Blog;
import com.lucy.util.DBUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named("blogRepository")
@ApplicationScoped
public class BlogRepository {

    private final Connection connection;

    public BlogRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    public boolean saveBlog(Blog blog) {
        String id = blog.getId() != null ? blog.getId() : UUID.randomUUID().toString();
        String query = "INSERT INTO blog (id, title, content, category , image_url, summary, created_at, updated_at) VALUES (?,?,?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.setString(2, blog.getTitle());
            stmt.setString(3, blog.getContent());
            stmt.setString(4, blog.getCategory());
            stmt.setString(5, blog.getImageUrl());
            stmt.setString(6, blog.getSummary());
            stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        String query = "SELECT * FROM blog";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                blogs.add(mapBlog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    public Blog getBlogById(String id) {
        String query = "SELECT * FROM blog WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapBlog(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBlog(Blog blog) {
        String query = "UPDATE blog SET title = ?, content = ?, category = ?,image_url = ?, summary = ?,updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setString(3, blog.getCategory());
            stmt.setString(4, blog.getImageUrl());
            stmt.setString(5, blog.getSummary());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(7, blog.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBlogById(String id) {
        String query = "DELETE FROM blog WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Blog mapBlog(ResultSet rs) throws SQLException {
        Blog blog = new Blog();
        blog.setId(rs.getString("id"));
        blog.setTitle(rs.getString("title"));
        blog.setContent(rs.getString("content"));
        blog.setContent(rs.getString("category"));
        blog.setImageUrl(rs.getString("image_url"));
        blog.setContent(rs.getString("summary"));
        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");
        if (created != null) blog.setCreatedAt(created.toLocalDateTime());
        if (updated != null) blog.setUpdatedAt(updated.toLocalDateTime());
        return blog;
    }

    public List<Blog> searchBlogs(String searchTerm) {
        List<Blog> blogs = new ArrayList<>();
        String query = "SELECT * FROM blog WHERE LOWER(title) LIKE ? OR LOWER(content) LIKE ? OR LOWER(image_url) LIKE ? OR LOWER(category) LIKE ? OR LOWER(summary) LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);
            stmt.setString(3, likeTerm);
            stmt.setString(4, likeTerm);
            stmt.setString(5, likeTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    blogs.add(mapBlog(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blogs;
    }
}
