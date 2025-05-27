package com.lucy.repository;

import com.lucy.model.FAQ;
import com.lucy.util.DBUtil;
import jakarta.faces.view.ViewScoped;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ViewScoped
public class FAQRepository implements Serializable {

    private Connection connection;

    public FAQRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    // Fetch FAQ by ID
    public FAQ getFAQById(String id) {
        try {
            String query = "SELECT * FROM faq WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapFAQ(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save new FAQ to the database
    public boolean saveFAQ(FAQ faq) {
        try {
            // Generate UUID if FAQ ID is null
            String faqId = faq.getId() != null ? faq.getId() : UUID.randomUUID().toString();

            String query = "INSERT INTO faq (id, question, answer, category, display_order, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, faqId);
            stmt.setString(2, faq.getQuestion());
            stmt.setString(3, faq.getAnswer());
            stmt.setString(4, faq.getCategory());
            stmt.setInt(5, faq.getDisplayOrder());
            stmt.setTimestamp(6, Timestamp.valueOf(faq.getCreatedAt()));
            stmt.setTimestamp(7, Timestamp.valueOf(faq.getUpdatedAt()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update existing FAQ
    public boolean updateFAQ(FAQ faq) {
        String query = "UPDATE faq SET question = ?, answer = ?, category = ?, display_order = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, faq.getQuestion());
            stmt.setString(2, faq.getAnswer());
            stmt.setString(3, faq.getCategory());
            stmt.setInt(4, faq.getDisplayOrder());
            stmt.setTimestamp(5, Timestamp.valueOf(faq.getUpdatedAt()));
            stmt.setString(6, faq.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete FAQ by ID
    public boolean deleteFAQById(String id) {
        String query = "DELETE FROM faq WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // List all FAQs
    public List<FAQ> getAllFAQs() {
        List<FAQ> faqs = new ArrayList<>();
        String query = "SELECT * FROM faq ORDER BY display_order";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                faqs.add(mapFAQ(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faqs;
    }

    // Search FAQs by question
    public List<FAQ> searchFAQs(String searchTerm) {
        List<FAQ> faqs = new ArrayList<>();
        String query = "SELECT * FROM faq WHERE LOWER(question) LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            stmt.setString(1, likeTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    faqs.add(mapFAQ(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return faqs;
    }

    // Fetch all distinct categories
    public List<String> getDistinctCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT category FROM faq";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Fetch all FAQs by category
    public List<FAQ> getFaqsByCategory(String category) {
        List<FAQ> faqs = new ArrayList<>();
        String query = "SELECT * FROM faq WHERE category = ? ORDER BY display_order";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    faqs.add(mapFAQ(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faqs;
    }


    // Helper method to map result set to FAQ model
    private FAQ mapFAQ(ResultSet rs) throws SQLException {
        FAQ faq = new FAQ();
        faq.setId(rs.getString("id"));
        faq.setQuestion(rs.getString("question"));
        faq.setAnswer(rs.getString("answer"));
        faq.setCategory(rs.getString("category"));
        faq.setDisplayOrder(rs.getInt("display_order"));

        // Handling null timestamps and mapping them to LocalDateTime
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            faq.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            faq.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return faq;
    }
    // Method to reorder FAQs based on displayOrder
    @Transactional
    public void reorderFAQs(List<FAQ> faqs) {
        int order = 1;
        for (FAQ faq : faqs) {
            faq.setDisplayOrder(order++);
            // Update each FAQ in the database after modifying the display order
            updateFAQ(faq);  // This calls the updateFAQ method to persist changes to the database

        }
    }
}
