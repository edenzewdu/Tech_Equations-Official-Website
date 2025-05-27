package com.lucy.repository;

import com.lucy.model.TeamMember;
import com.lucy.util.DBUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TeamMemberRepository implements Serializable {

    private final Connection connection;

    public TeamMemberRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    public TeamMember getTeamMemberById(String id) {
        String query = "SELECT * FROM team_members WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapTeamMember(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TeamMember> getAllTeamMembers() {
        List<TeamMember> members = new ArrayList<>();
        String query = "SELECT * FROM team_members";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                members.add(mapTeamMember(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public boolean saveTeamMember(TeamMember member) {
        String TeamMemberId = member.getId() != null ? member.getId() : UUID.randomUUID().toString();
        String query = "INSERT INTO team_members (id, name, role, image_url, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, TeamMemberId);
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getRole());
            stmt.setString(4, member.getImageUrl());
            stmt.setTimestamp(5, Timestamp.valueOf(member.getCreatedAt()));
            stmt.setTimestamp(6, Timestamp.valueOf(member.getUpdatedAt()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateTeamMember(TeamMember member) {
        String query = "UPDATE team_members SET name = ?, role = ?, image_url = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getRole());
            stmt.setString(3, member.getImageUrl());
            stmt.setTimestamp(4, Timestamp.valueOf(member.getUpdatedAt()));
            stmt.setString(5, member.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTeamMemberById(String id) {
        String query = "DELETE FROM team_members WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private TeamMember mapTeamMember(ResultSet rs) throws SQLException {
        TeamMember member = new TeamMember();
        member.setId(rs.getString("id"));
        member.setName(rs.getString("name"));
        member.setRole(rs.getString("role"));
        member.setImageUrl(rs.getString("image_url"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            member.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            member.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return member;
    }

    public List<TeamMember> searchTeamMembers(String searchTerm) {
        List<TeamMember> teamMembers = new ArrayList<>();
        String query = "SELECT * FROM team_members WHERE LOWER(name) LIKE ? OR LOWER(role) LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    teamMembers.add(mapTeamMember(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teamMembers;
    }
}
