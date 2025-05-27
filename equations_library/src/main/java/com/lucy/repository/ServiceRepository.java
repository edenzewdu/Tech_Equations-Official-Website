package com.lucy.repository;

import com.lucy.model.Service;
import com.lucy.util.DBUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named("serviceRepository")
@ApplicationScoped
public class ServiceRepository {

    private final Connection connection;

    public ServiceRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    public boolean saveService(Service service) {
        String id = service.getId() != null ? service.getId() : UUID.randomUUID().toString();
        String query = "INSERT INTO services (id, name, description, img, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.setString(2, service.getName());
            stmt.setString(3, service.getDescription());
            stmt.setString(4, service.getImg());
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        String query = "SELECT * FROM services";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                services.add(mapService(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    public Service getServiceById(String id) {
        String query = "SELECT * FROM services WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapService(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateService(Service service) {
        String query = "UPDATE services SET name = ?, description = ?, img = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, service.getName());
            stmt.setString(2, service.getDescription());
            stmt.setString(3, service.getImg());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(5, service.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteServiceById(String id) {
        String query = "DELETE FROM services WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Service mapService(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getString("id"));
        service.setName(rs.getString("name"));
        service.setDescription(rs.getString("description"));
        service.setImg(rs.getString("img"));
        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");
        if (created != null) service.setCreatedAt(created.toLocalDateTime());
        if (updated != null) service.setUpdatedAt(updated.toLocalDateTime());
        return service;
    }
    public List<Service> searchServices(String searchTerm) {
        List<Service> services = new ArrayList<>();
        String query = "SELECT * FROM services WHERE LOWER(name) LIKE ? OR LOWER(description) LIKE ? OR LOWER(img) LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);
            stmt.setString(2, likeTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    services.add(mapService(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return services;
    }
}
