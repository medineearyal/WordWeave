package com.wordweave.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.wordweave.config.DbConfig;
import com.wordweave.models.ContactModel;

public class ContactService {
	private Connection connection;
	private boolean isConnectionError = false;
	private String getAllBlogsQuery = "SELECT b.*, u.fullname AS author_name FROM blog b JOIN user u ON b.author_id = u.user_id WHERE is_draft = 0;";

	public ContactService() {
		try {
			connection = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	
	public boolean createContact(ContactModel contact) {
	    String query = "INSERT INTO contacts (author_id, sender_email, message_text, sender_name, message_date) VALUES (?, ?, ?, ?, ?)";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setLong(1, contact.getAuthorId());
	        stmt.setString(2, contact.getSenderEmail());
	        stmt.setString(3, contact.getMessageText());
	        stmt.setString(4, contact.getSenderName());
	        stmt.setTimestamp(5, Timestamp.valueOf(contact.getMessageDate()));
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	// Retrieve all contacts
	public List<ContactModel> getAllContacts() {
	    List<ContactModel> contacts = new ArrayList<>();
	    String query = "SELECT * FROM contacts";
	    try (Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {
	        while (rs.next()) {
	            ContactModel contact = new ContactModel();
	            contact.setMessageId(rs.getLong("message_id"));
	            contact.setAuthorId(rs.getLong("author_id"));
	            contact.setSenderEmail(rs.getString("sender_email"));
	            contact.setMessageText(rs.getString("message_text"));
	            contact.setSenderName(rs.getString("sender_name"));
	            contact.setMessageDate(rs.getTimestamp("message_date").toLocalDateTime());
	            contacts.add(contact);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return contacts;
	}

	// Retrieve a contact by its ID
	public ContactModel getContactById(Long id) {
	    ContactModel contact = null;
	    String query = "SELECT * FROM contacts WHERE message_id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setLong(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                contact = new ContactModel();
	                contact.setMessageId(rs.getLong("message_id"));
	                contact.setAuthorId(rs.getLong("author_id"));
	                contact.setSenderEmail(rs.getString("sender_email"));
	                contact.setMessageText(rs.getString("message_text"));
	                contact.setSenderName(rs.getString("sender_name"));
	                contact.setMessageDate(rs.getTimestamp("message_date").toLocalDateTime());
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return contact;
	}

	// Update a contact
	public boolean updateContact(ContactModel contact) {
	    String query = "UPDATE contacts SET sender_email = ?, message_text = ?, sender_name = ?, message_date = ? WHERE message_id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, contact.getSenderEmail());
	        stmt.setString(2, contact.getMessageText());
	        stmt.setString(3, contact.getSenderName());
	        stmt.setTimestamp(4, Timestamp.valueOf(contact.getMessageDate()));
	        stmt.setLong(5, contact.getMessageId());
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	// Delete a contact
	public boolean deleteContact(Long id) {
	    String query = "DELETE FROM contacts WHERE message_id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setLong(1, id);
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
