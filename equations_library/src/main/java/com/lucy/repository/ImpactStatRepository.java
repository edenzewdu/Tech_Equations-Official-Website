package com.lucy.repository;

import com.lucy.model.ImpactStat;
import com.lucy.util.DBUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ImpactStatRepository implements Serializable {

    private Connection connection;

    public ImpactStatRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    // Fetch ImpactStat by id
    public ImpactStat getImpactStatById(String id) {
        try {
            String query = "SELECT * FROM impact_stats WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapImpactStat(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save new ImpactStat
    public boolean saveImpactStat(ImpactStat stat) {
        try {
            String ImpactStatsId = stat.getId() != null ? stat.getId() : UUID.randomUUID().toString();

            String query = "INSERT INTO impact_stats (id, label, value, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, ImpactStatsId);
            stmt.setString(2, stat.getLabel());
            stmt.setInt(3, stat.getValue());
            stmt.setTimestamp(4, Timestamp.valueOf(stat.getCreatedAt()));
            stmt.setTimestamp(5, Timestamp.valueOf(stat.getUpdatedAt()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update existing ImpactStat
    public boolean updateImpactStat(ImpactStat stat) {
        String query = "UPDATE impact_stats SET label = ?, value = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, stat.getLabel());
            stmt.setInt(2, stat.getValue());
            stmt.setTimestamp(3, Timestamp.valueOf(stat.getUpdatedAt()));
            stmt.setString(4, stat.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete ImpactStat by id
    public boolean deleteImpactStatById(String id) {
        String query = "DELETE FROM impact_stats WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all ImpactStats
    public List<ImpactStat> getAllImpactStats() {
        List<ImpactStat> stats = new ArrayList<>();
        String query = "SELECT * FROM impact_stats";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                stats.add(mapImpactStat(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    private ImpactStat mapImpactStat(ResultSet rs) throws SQLException {
        ImpactStat stat = new ImpactStat();
        stat.setId(rs.getString("id"));
        stat.setLabel(rs.getString("label"));
        stat.setValue(rs.getInt("value"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            stat.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            stat.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return stat;
    }

    public List<ImpactStat> searchImpactStat(String searchTerm) {
        List<ImpactStat> stats = new ArrayList<>();
        String query = "SELECT * FROM impact_stats WHERE LOWER(label) LIKE ? OR value LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    stats.add(mapImpactStat(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }
}
