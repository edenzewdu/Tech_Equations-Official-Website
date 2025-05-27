package com.lucy.repository;

import com.lucy.model.Contact;
import com.lucy.model.User;
import com.lucy.util.DBUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ContactRepository implements Serializable {

    private Connection connection;

    public ContactRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    public boolean saveContact(Contact contact) {
        String query = "INSERT INTO contacts (name, email, subject, message, is_read, submitted_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getEmail());
            stmt.setString(3, contact.getSubject());
            stmt.setString(4, contact.getMessage());
            stmt.setBoolean(5, contact.getRead());
            stmt.setTimestamp(6, Timestamp.valueOf(contact.getSubmittedAt()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                contacts.add(mapContact(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public Contact getContactById(String id) {
        String query = "SELECT * FROM contacts WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapContact(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean deleteContactById(String id) {
        String query = "DELETE FROM contacts WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean markAsRead(String id) {
        String query = "UPDATE contacts SET is_read = TRUE WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Contact mapContact(ResultSet rs) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getString("id"));
        contact.setName(rs.getString("name"));
        contact.setEmail(rs.getString("email"));
        contact.setSubject(rs.getString("subject"));
        contact.setMessage(rs.getString("message"));
        contact.setRead(rs.getBoolean("is_read"));
        contact.setSubmittedAt(rs.getTimestamp("submitted_at").toLocalDateTime());

        return contact;
    }

    public long getContactCountToday() {
        String query = "SELECT COUNT(*) FROM contacts WHERE DATE(submitted_at) = CURDATE()";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Contact> searchContact(String searchTerm) {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE LOWER(name) LIKE ? OR LOWER(email) LIKE ? OR LOWER(message) LIKE ? OR LOWER(subject) LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);
            stmt.setString(3, likeTerm);
            stmt.setString(4, likeTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    contacts.add(mapContact(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }
}
