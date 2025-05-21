package com.wordweave.models;

import java.sql.Date;

/**
 * Represents a user in the system.
 * Contains personal info, credentials, role, profile details, and account status.
 */
public class UserModel {
	private int user_id;
	private String fullname;
	private String email;
	private String username;
	private String password;
	private int role_id;
	private String profile_picture;
	private String bio;
	private Date createdAt;
	private Boolean isApproved;
	
	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public UserModel() {
	}

	/**
     * Constructor with username and password for login.
     * @param username the username
     * @param password the password
    */
	public UserModel(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
     * Constructor with full user details including user ID.
     * @param user_id unique user ID
     * @param fullname full name of the user
     * @param email user's email address
     * @param username username for login
     * @param role_id role ID assigned to the user
     * @param password password for login
     * @param profile_picture profile picture path or URL
     */
	public UserModel(int user_id, String fullname, String email, String username, int role_id, String password, String profile_picture) {
		super();
		this.user_id = user_id;
		this.fullname = fullname;
		this.email = email;
		this.username = username;
		this.role_id = role_id;
		this.password = password;
		this.profile_picture = profile_picture;
	}

	/**
     * Constructor with user details (without user ID, for new users).
     * @param fullname full name of the user
     * @param email user's email address
     * @param username username for login
     * @param password password for login
     * @param role_id role ID assigned to the user
     * @param profile_picture profile picture path or URL
     */
	public UserModel(String fullname, String email, String username, String password, int role_id, String profile_picture) {
		super();
		this.fullname = fullname;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role_id = role_id;
		this.profile_picture = profile_picture;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getRole_id() {
		return role_id;  // Getter for role_id
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getProfile_picture() {
		return profile_picture;  // Getter for profile_picture
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;  // Setter for profile_picture
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
}
