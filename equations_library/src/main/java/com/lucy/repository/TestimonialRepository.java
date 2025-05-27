package com.lucy.repository;


import com.lucy.model.Testimonial;
import com.lucy.util.DBUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TestimonialRepository implements Serializable {

    private Connection connection;

    public TestimonialRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    public List<Testimonial> getAllTestimonials() {
        List<Testimonial> testimonials = new ArrayList<>();
        String query = "SELECT * FROM testimonials ORDER BY display_order ASC";

        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                testimonials.add(mapTestimonial(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testimonials;
    }

    public List<Testimonial> getPublishedTestimonials() {
        List<Testimonial> testimonials = new ArrayList<>();
        String query = "SELECT * FROM testimonials WHERE is_published = TRUE ORDER BY display_order ASC";

        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                testimonials.add(mapTestimonial(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testimonials;
    }

    public boolean saveTestimonial(Testimonial testimonial) {
        try {
            // Generate UUID if user ID is null
            String testimonialId = testimonial.getId() != null ? testimonial.getId() : UUID.randomUUID().toString();

                String insert = "INSERT INTO testimonials (id, author_name, author_title, quote_text, is_published, display_order, avatar_url, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(insert);

                stmt.setString(1, testimonialId);
                stmt.setString(2, testimonial.getAuthorName());
                stmt.setString(3, testimonial.getAuthorTitle());
                stmt.setString(4, testimonial.getQuoteText());
                stmt.setBoolean(5, testimonial.getPublished());
                stmt.setInt(6, testimonial.getDisplayOrder());
                stmt.setString(7, testimonial.getAvatarUrl());
                stmt.setTimestamp(8, Timestamp.valueOf(testimonial.getCreatedAt()));
                stmt.setTimestamp(9, Timestamp.valueOf(testimonial.getUpdatedAt()));
                return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean editTestimonial(Testimonial testimonial) {
        try {
        String update = "UPDATE testimonials SET author_name = ?, author_title = ?, quote_text = ?, is_published = ?, display_order = ?, avatar_url=?, updated_at = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(update);

        stmt.setString(1, testimonial.getAuthorName());
        stmt.setString(2, testimonial.getAuthorTitle());
        stmt.setString(3, testimonial.getQuoteText());
        stmt.setBoolean(4, testimonial.getPublished());
        stmt.setInt(5, testimonial.getDisplayOrder());
        stmt.setString(6, testimonial.getAvatarUrl());
        stmt.setTimestamp(7, Timestamp.valueOf(testimonial.getUpdatedAt()));
        stmt.setString(8, testimonial.getId());
        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }
        return false;
}
    public Testimonial getTestimonialById(String id) {
        String query = "SELECT * FROM testimonials WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapTestimonial(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteTestimonialById(String id) {
        String query = "DELETE FROM testimonials WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public long getTestimonialCount() {
        String query = "SELECT COUNT(*) FROM testimonials";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long countPublishedWithSameDisplayOrder(int displayOrder) {
        String query = "SELECT COUNT(*) FROM testimonials WHERE is_published = TRUE AND display_order = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, displayOrder);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Testimonial mapTestimonial(ResultSet rs) throws SQLException {
        Testimonial t = new Testimonial();
        t.setId(rs.getString("id"));
        t.setAuthorName(rs.getString("author_name"));
        t.setAuthorTitle(rs.getString("author_title"));
        t.setQuoteText(rs.getString("quote_text"));
        t.setPublished(rs.getBoolean("is_published"));
        t.setDisplayOrder(rs.getInt("display_order"));
        t.setAvatarUrl(rs.getString("avatar_url"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) t.setCreatedAt(createdAt.toLocalDateTime());

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) t.setUpdatedAt(updatedAt.toLocalDateTime());

        return t;
    }

    public boolean updateTestimonial(Testimonial testimonial) {
        String query = "UPDATE testimonials SET author_name = ?, author_title = ?, quote_text = ?, is_published = ?, display_order = ?, avatar_url=?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, testimonial.getAuthorName());
            stmt.setString(2, testimonial.getAuthorTitle());
            stmt.setString(3, testimonial.getQuoteText());
            stmt.setBoolean(4, testimonial.getPublished());
            stmt.setInt(5, testimonial.getDisplayOrder());
            stmt.setString(6, testimonial.getAvatarUrl());
            stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(8, testimonial.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Testimonial> searchTestimonials(String keyword) {
        List<Testimonial> testimonials = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM testimonials WHERE ");
        List<Object> params = new ArrayList<>();

        if ("published".equalsIgnoreCase(keyword)) {
            query.append("is_published = TRUE");
        } else if ("unpublished".equalsIgnoreCase(keyword)) {
            query.append("is_published = FALSE");
        }else if ("draft".equalsIgnoreCase(keyword)) {
            query.append("is_published = FALSE");
        } else {
            query.append("(author_name LIKE ? OR author_title LIKE ? OR quote_text LIKE ?)");
            String searchKeyword = "%" + keyword + "%";
            params.add(searchKeyword);
            params.add(searchKeyword);
            params.add(searchKeyword);
        }

        query.append(" ORDER BY display_order ASC");

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    testimonials.add(mapTestimonial(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return testimonials;
    }

}
