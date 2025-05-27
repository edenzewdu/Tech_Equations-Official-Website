package com.lucy.repository;

import com.lucy.model.Partner;
import com.lucy.util.DBUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PartnerRepository implements Serializable {

    private Connection connection;

    public PartnerRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    // Fetch partner by name
    public Partner getPartnerByName(String name) {
        try {
            String query = "SELECT * FROM partners WHERE name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPartner(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save new partner to the database
    public boolean savePartner(Partner partner) {
        try {
            String partnerId = partner.getId() != null ? partner.getId() : UUID.randomUUID().toString();

            String query = "INSERT INTO partners (id, name, logo_url, website_url, display_order, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, partnerId);
            stmt.setString(2, partner.getName());
            stmt.setString(3, partner.getLogoUrl());
            stmt.setString(4, partner.getWebsiteUrl());
            stmt.setInt(5, partner.getDisplayOrder());
            stmt.setTimestamp(6, Timestamp.valueOf(partner.getCreatedAt()));
            stmt.setTimestamp(7, Timestamp.valueOf(partner.getUpdatedAt()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all partners
    public List<Partner> getAllPartners() {
        List<Partner> partners = new ArrayList<>();
        String query = "SELECT * FROM partners ORDER BY display_order";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                partners.add(mapPartner(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partners;
    }

    // Get partner by ID
    public Partner getPartnerById(String id) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM partners WHERE id = ?")) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPartner(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update existing partner
    public boolean updatePartner(Partner partner) {
        String query = "UPDATE partners SET name = ?, logo_url = ?, website_url = ?, display_order = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, partner.getName());
            stmt.setString(2, partner.getLogoUrl());
            stmt.setString(3, partner.getWebsiteUrl());
            stmt.setInt(4, partner.getDisplayOrder());
            stmt.setTimestamp(5, Timestamp.valueOf(partner.getUpdatedAt()));
            stmt.setString(6, partner.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete partner by ID
    public boolean deletePartnerById(String id) {
        String query = "DELETE FROM partners WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to Partner object
    private Partner mapPartner(ResultSet rs) throws SQLException {
        Partner partner = new Partner();
        partner.setId(rs.getString("id"));
        partner.setName(rs.getString("name"));
        partner.setLogoUrl(rs.getString("logo_url"));
        partner.setWebsiteUrl(rs.getString("website_url"));
        partner.setDisplayOrder(rs.getInt("display_order"));
        partner.setCreatedAt(Timestamp.valueOf(rs.getTimestamp("created_at").toLocalDateTime()));
        partner.setUpdatedAt(Timestamp.valueOf(rs.getTimestamp("updated_at").toLocalDateTime()));
        return partner;
    }

    // Search partners by name or website
    public List<Partner> searchPartners(String searchTerm) {
        List<Partner> partners = new ArrayList<>();
        String query = "SELECT * FROM partners WHERE LOWER(name) LIKE ? OR LOWER(website_url) LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    partners.add(mapPartner(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partners;
    }
}
