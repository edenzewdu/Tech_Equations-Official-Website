package com.lucy.repository;

import com.lucy.model.PricingPlan;
import com.lucy.util.DBUtil;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SessionScoped
public class PricingPlanRepository implements Serializable {

    private Connection connection;

    public PricingPlanRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    // Fetch pricing plan by name
    public PricingPlan getPricingPlanByName(String name) {
        try {
            String query = "SELECT * FROM pricing_plans WHERE name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPricingPlan(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save a new pricing plan to the database
    public boolean savePricingPlan(PricingPlan pricingPlan) {
        try {
            // Generate UUID if pricing plan ID is null
            String planId = pricingPlan.getId() != null ? pricingPlan.getId() : UUID.randomUUID().toString();

            String query = "INSERT INTO pricing_plans (id, name, price, original_price, billing_frequency, features, is_highlighted, display_order, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, planId);
            stmt.setString(2, pricingPlan.getName());
            stmt.setDouble(3, pricingPlan.getPrice());
            stmt.setDouble(4, pricingPlan.getOriginal_price());
            stmt.setString(5, pricingPlan.getBillingFrequency());
            stmt.setString(6, pricingPlan.getFeatures());
            stmt.setBoolean(7, pricingPlan.isHighlighted());
            stmt.setInt(8, pricingPlan.getDisplayOrder());
            stmt.setTimestamp(9, Timestamp.valueOf(pricingPlan.getCreatedAt()));
            stmt.setTimestamp(10, Timestamp.valueOf(pricingPlan.getUpdatedAt()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get the count of pricing plans
    public long getPricingPlanCount() {
        String query = "SELECT COUNT(*) FROM pricing_plans";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get all pricing plans
    public List<PricingPlan> getAllPricingPlans() {
        List<PricingPlan> plans = new ArrayList<>();
        String query = "SELECT * FROM pricing_plans order by display_order ASC";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                plans.add(mapPricingPlan(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }

    public List<PricingPlan> getAllPricingPlansByFeatures(String billingFrequency) {
        List<PricingPlan> plans = new ArrayList<>();
        String query = "SELECT * FROM pricing_plans WHERE billing_frequency = ? order by display_order";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, billingFrequency);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    plans.add(mapPricingPlan(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }
    public long countPublishedWithSameDisplayOrder(int displayOrder) {
        String query = "SELECT COUNT(*) FROM pricing_plans WHERE display_order = ?";
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

    public boolean editPricingPlan(PricingPlan pricingPlan) {
        try {
            String update = "UPDATE pricing_plans SET name = ?, price = ?, original_price = ?, billing_frequency = ?, features = ?, is_highlighted = ?, display_order = ?, updated_at = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(update);

            stmt.setString(1, pricingPlan.getName());
            stmt.setDouble(2, pricingPlan.getPrice());
            stmt.setDouble(3, pricingPlan.getOriginal_price());
            stmt.setString(4, pricingPlan.getBillingFrequency());
            stmt.setString(5, pricingPlan.getFeatures()); // Fixed: Add features
            stmt.setBoolean(6, pricingPlan.isHighlighted());
            stmt.setInt(7, pricingPlan.getDisplayOrder());
            stmt.setTimestamp(8, Timestamp.valueOf(pricingPlan.getUpdatedAt()));
            stmt.setString(9, pricingPlan.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<String> getAllBillingFrequencies() {
        List<String> frequencies = new ArrayList<>();
        String query = "SELECT DISTINCT billing_frequency FROM pricing_plans";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                frequencies.add(rs.getString("billing_frequency"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frequencies;
    }


    // Get a pricing plan by ID
    public PricingPlan getPricingPlanById(String id) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM pricing_plans WHERE id = ?")) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPricingPlan(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing pricing plan
    public boolean updatePricingPlan(PricingPlan pricingPlan) {
        String query = "UPDATE pricing_plans SET name = ?, price = ?, original_price = ?, billing_frequency = ?, features = ?, is_highlighted = ?, display_order = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, pricingPlan.getName());
            stmt.setDouble(2, pricingPlan.getPrice());
            stmt.setDouble(3, pricingPlan.getOriginal_price());
            stmt.setString(4, pricingPlan.getBillingFrequency());
            stmt.setString(5, pricingPlan.getFeatures());
            stmt.setBoolean(6, pricingPlan.isHighlighted());
            stmt.setInt(7, pricingPlan.getDisplayOrder());
            stmt.setTimestamp(8, Timestamp.valueOf(pricingPlan.getUpdatedAt()));
            stmt.setString(9, pricingPlan.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a pricing plan by ID
    public boolean deletePricingPlanById(String id) {
        String query = "DELETE FROM pricing_plans WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Map ResultSet to PricingPlan object
    private PricingPlan mapPricingPlan(ResultSet rs) throws SQLException {
        PricingPlan plan = new PricingPlan();
        plan.setId(rs.getString("id"));
        plan.setName(rs.getString("name"));
        plan.setPrice(rs.getDouble("price"));
        plan.setOriginal_price(rs.getDouble("original_price"));
        plan.setBillingFrequency(rs.getString("billing_frequency"));
        plan.setFeatures(rs.getString("features"));
        plan.setHighlighted(rs.getBoolean("is_highlighted"));
        plan.setDisplayOrder(rs.getInt("display_order"));

        // Handling null timestamps and mapping them to LocalDateTime
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            plan.setCreatedAt(Timestamp.valueOf(createdAt.toLocalDateTime()).toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            plan.setUpdatedAt(Timestamp.valueOf(updatedAt.toLocalDateTime()).toLocalDateTime());
        }

        return plan;
    }

    // Search pricing plans by name or feature
    public List<PricingPlan> searchPricingPlans(String searchTerm) {
        List<PricingPlan> plans = new ArrayList<>();
        String query = "SELECT * FROM pricing_plans WHERE LOWER(name) LIKE ? OR LOWER(features) LIKE ? OR LOWER(billing_frequency) LIKE ? OR LOWER(price) LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);
            stmt.setString(3, likeTerm);
            stmt.setString(4, likeTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    plans.add(mapPricingPlan(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plans;
    }
}
