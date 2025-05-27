package com.lucy.repository;

import com.lucy.model.ContentBlock;
import com.lucy.model.User;
import com.lucy.util.DBUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ContentBlockRepository implements Serializable {

    private final Connection connection;

    public ContentBlockRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    public ContentBlock getContentBlockById(String id) {
        String query = "SELECT * FROM content_blocks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapContentBlock(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ContentBlock> getAllContentBlocks() {
        List<ContentBlock> blocks = new ArrayList<>();
        String query = "SELECT * FROM content_blocks";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                blocks.add(mapContentBlock(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blocks;
    }

    public boolean saveContentBlock(ContentBlock block) {
        String query = "INSERT INTO content_blocks (id, content) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, block.getId());
            stmt.setString(2, block.getContent());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateContentBlock(ContentBlock block) {
        String query = "UPDATE content_blocks SET content = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, block.getContent());
            stmt.setString(2, block.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteContentBlock(String id) {
        String query = "DELETE FROM content_blocks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ContentBlock mapContentBlock(ResultSet rs) throws SQLException {
        ContentBlock block = new ContentBlock();
        block.setId(rs.getString("id"));
        block.setContent(rs.getString("content"));
        return block;
    }

    public List<ContentBlock> searchBlocks(String searchTerm) {
        List<ContentBlock> blocks = new ArrayList<>();
        String query = "SELECT * FROM users WHERE LOWER(id) LIKE ? OR LOWER(content) LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    blocks.add(mapContentBlock(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blocks;
    }
}
