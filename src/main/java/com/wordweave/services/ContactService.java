package com.wordweave.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wordweave.config.DbConfig;
import com.wordweave.models.ContactModel;

public class ContactService {
	private Connection connection;
	private boolean isConnectionError = false;
	/**
     * Constructor initializes the database connection.
     * If connection fails, sets isConnectionError to true and prints the stack trace.
     */
	public ContactService() {
		try {
			connection = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	 /**
     * Creates a new contact message record in the database.
     *
     * @param contact ContactModel containing the details to insert.
     * @return true if insert was successful, false otherwise.
     */
	public boolean createContact(ContactModel contact) {
	    String query = "INSERT INTO contact_message (author_id, sender_email, message_text, sender_name, message_date) VALUES (?, ?, ?, ?, ?)";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setInt(1, contact.getAuthorId());
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

	/**
     * Retrieves all contact messages from the database.
     *
     * @return List of ContactModel objects representing all contacts.
    */
	public List<ContactModel> getAllContacts() {
	    List<ContactModel> contacts = new ArrayList<>();
	    String query = "SELECT * FROM contact_message";
	    try (Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {
	        while (rs.next()) {
	            ContactModel contact = new ContactModel();
	            contact.setMessageId(rs.getInt("message_id"));
	            contact.setAuthorId(rs.getInt("author_id"));
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

	/**
     * Retrieves a single contact message by its ID.
     *
     * @param id The message_id of the contact message to retrieve.
     * @return ContactModel object if found, otherwise null.
     */
	public ContactModel getContactById(int id) {
	    ContactModel contact = null;
	    String query = "SELECT * FROM contact_message WHERE message_id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setLong(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                contact = new ContactModel();
	                contact.setMessageId(rs.getInt("message_id"));
	                contact.setAuthorId(rs.getInt("author_id"));
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
	
	/**
     * Retrieves all contact messages for a specific author by username.
     *
     * @param author The username of the author to filter by.
     * @return List of ContactModel objects for the given author.
     * @throws SQLException if a database access error occurs.
     */
	public List<ContactModel> getAllContactsForAuthor(String author) throws SQLException {
        String author_id_query = "SELECT user_id FROM user WHERE username = ?";
        String query = "SELECT * FROM contact_message WHERE author_id = ?";
        
        List<ContactModel> contacts = new ArrayList<>();

        try (PreparedStatement authorStmt = connection.prepareStatement(author_id_query)) {
            authorStmt.setString(1, author);
            ResultSet authorResult = authorStmt.executeQuery();

            if (authorResult.next()) {
                int authorId = authorResult.getInt("user_id");

                try (PreparedStatement contactStmt = connection.prepareStatement(query)) {
                    contactStmt.setInt(1, authorId);
                    ResultSet contactResult = contactStmt.executeQuery();
                    
                    while (contactResult.next()) {
                        int id = contactResult.getInt("message_id");
                        String senderName = contactResult.getString("sender_name");
                        String senderEmail = contactResult.getString("sender_email");
                        String messageText = contactResult.getString("message_text");
                        
                        Timestamp timestamp = contactResult.getTimestamp("message_date");
                        LocalDateTime messageDate = timestamp.toLocalDateTime();

                        ContactModel contact = new ContactModel(id, senderEmail, messageText, senderName, messageDate);
                        contacts.add(contact);
                    }
                }
            }
        }

        return contacts;
    }

	/**
     * Updates an existing contact message in the database.
     *
     * @param contact ContactModel containing updated details (must include message_id).
     * @return true if update was successful, false otherwise.
     */
	public boolean updateContact(ContactModel contact) {
	    String query = "UPDATE contact_message SET sender_email = ?, message_text = ?, sender_name = ?, message_date = ? WHERE message_id = ?";
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

	/**
     * Deletes a contact message from the database by its ID.
     *
     * @param id The message_id of the contact message to delete.
     * @return true if deletion was successful, false otherwise.
     */
	public boolean deleteContact(int id) {
	    String query = "DELETE FROM contact_message WHERE message_id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setLong(1, id);
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
