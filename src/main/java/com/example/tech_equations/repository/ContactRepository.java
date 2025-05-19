//package com.example.tech_equations.repository;
//
//import com.example.tech_equations.model.Contact;
//import com.example.tech_equations.util.DBUtil;
//import jakarta.enterprise.context.ApplicationScoped;
//
//import java.io.Serializable;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@ApplicationScoped
//public class ContactRepository implements Serializable {
//
//    private Connection connection;
//
//    public ContactRepository() {
//        this.connection = DBUtil.getConnection();
//        if (this.connection == null) {
//            throw new IllegalStateException("Failed to establish DB connection");
//        }
//    }
//
//    public boolean saveContact(Contact contact) {
//        try {
//            String contactId = contact.getId() != null ? contact.getId() : UUID.randomUUID().toString();
//
//            String query = "INSERT INTO contacts (id, name, email, subject, message, is_read, submitted_at, user_id) " +
//                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//            PreparedStatement stmt = connection.prepareStatement(query);
//
//            stmt.setString(1, contactId);
//            stmt.setString(2, contact.getName());
//            stmt.setString(3, contact.getEmail());
//            stmt.setString(4, contact.getSubject());
//            stmt.setString(5, contact.getMessage());
//            stmt.setBoolean(6, contact.isRead());
//            stmt.setTimestamp(7, Timestamp.valueOf(contact.getSubmittedAt()));
//            stmt.setString(8, contact.getId());
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public List<Contact> getAllContacts() {
//        List<Contact> contacts = new ArrayList<>();
//        String query = "SELECT * FROM contacts ORDER BY submitted_at DESC";
//        try (PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                contacts.add(mapContact(rs));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return contacts;
//    }
//
//    public Contact getContactById(String id) {
//        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM contacts WHERE id = ?")) {
//            stmt.setString(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return mapContact(rs);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public boolean markAsRead(String id) {
//        String query = "UPDATE contacts SET is_read = TRUE WHERE id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, id);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean deleteContactById(String id) {
//        String query = "DELETE FROM contacts WHERE id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, id);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public long getContactCountToday() {
//        String query = "SELECT COUNT(*) FROM contacts WHERE DATE(submitted_at) = CURDATE()";
//        try (PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            if (rs.next()) return rs.getLong(1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    private Contact mapContact(ResultSet rs) throws SQLException {
//        Contact contact = new Contact();
//        contact.setId(rs.getString("id"));
//        contact.setName(rs.getString("name"));
//        contact.setEmail(rs.getString("email"));
//        contact.setSubject(rs.getString("subject"));
//        contact.setMessage(rs.getString("message"));
//        contact.setRead(rs.getBoolean("is_read"));
//        contact.setSubmittedAt(rs.getTimestamp("submitted_at").toLocalDateTime());
//        contact.setUserId(rs.getString("user_id"));
//        return contact;
//    }
//}
