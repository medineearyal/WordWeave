package com.wordweave.models;

import java.time.LocalDateTime;

/**
 * Represents a contact message sent by a user or visitor.
 */
public class ContactModel {
    private int messageId;
    private int authorId;
    private String senderEmail;
    private String messageText;
    private String senderName;
    private LocalDateTime messageDate;
    
    public ContactModel() {
    };
    
    /**
     * Constructor without messageId and messageDate.
     * Useful when creating a new message before it is saved (and ID/date assigned).
     * 
     * @param authorId    ID of the author who will receive the message
     * @param senderEmail Email of the sender
     * @param messageText Text content of the message
     * @param senderName  Name of the sender
     */
    public ContactModel(int authorId, String senderEmail, String messageText, String senderName) {
		super();
		this.authorId = authorId;
		this.senderEmail = senderEmail;
		this.messageText = messageText;
		this.senderName = senderName;
	}

    /**
     * Full constructor including messageId and messageDate.
     * Useful when loading an existing message from the database.
     * 
     * @param messageId   Unique message ID
     * @param senderEmail Email of the sender
     * @param messageText Text content of the message
     * @param senderName  Name of the sender
     * @param messageDate Date and time the message was sent
     */
	public ContactModel(int messageId, String senderEmail, String messageText, String senderName,
			LocalDateTime messageDate) {
		super();
		this.messageId = messageId;
		this.senderEmail = senderEmail;
		this.messageText = messageText;
		this.senderName = senderName;
		this.messageDate = messageDate;
	}

	// Getters and Setters
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public LocalDateTime getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(LocalDateTime messageDate) {
        this.messageDate = messageDate;
    }
}
