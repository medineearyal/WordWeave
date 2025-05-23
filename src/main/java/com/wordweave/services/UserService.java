package com.wordweave.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wordweave.config.DbConfig;
import com.wordweave.models.UserModel;
import com.wordweave.utils.PasswordUtil;

public class UserService {
	private Connection dbConn;
	private boolean isConnectionError = false;

	 /**
     * Initializes database connection on service creation.
     * Sets connection error flag if connection fails.
    */
	public UserService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	/**
	 * Validates the user credentials against the database records.
	 *
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the user credentials are valid, false otherwise; null if a
	 *         connection error occurs
	 */
	public Boolean loginUser(UserModel userModel) {
		if (isConnectionError) {
			return null;
		}

		String query = "SELECT username, password FROM user WHERE username = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, userModel.getUsername());
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				return validatePassword(result, userModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return false;
	}

	/**
     * Registers a new user into the database.
     *
     * @param userModel UserModel containing all user details.
     * @return true if registration succeeds, false if it fails, null if no DB connection.
     */
	public Boolean registerUser(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}
		String insertQuery = "INSERT INTO user (fullname, email, username, role_id, password, profile_picture) " + "VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {
			insertStmt.setString(1, userModel.getFullname());
			insertStmt.setString(2, userModel.getEmail());
			insertStmt.setString(3, userModel.getUsername());
			insertStmt.setInt(4, userModel.getRole_id());
			insertStmt.setString(5, userModel.getPassword());
			insertStmt.setString(6, userModel.getProfile_picture());

			return insertStmt.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error during user registration: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Validates the password retrieved from the database.
	 *
	 * @param result the ResultSet containing the username and password from the database
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the passwords match, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
		String dbUsername = result.getString("username");
		String dbPassword = result.getString("password");

		System.out.print(PasswordUtil.decrypt(dbPassword, dbUsername));

		return dbUsername.equals(userModel.getUsername())
				&& PasswordUtil.decrypt(dbPassword, dbUsername).equals(userModel.getPassword());
	}

	/**
     * Retrieves all users from the database.
     *
     * @return List of all UserModel objects.
     * @throws ClassNotFoundException if DB driver not found.
     */
	public List<UserModel> getAllUsers() throws ClassNotFoundException {
		List<UserModel> userList = new ArrayList<>();

		String sql = "SELECT user_id, fullname, email, username, role_id, profile_picture FROM user";

		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				UserModel user = new UserModel();
				user.setUser_id(rs.getInt("user_id"));
				user.setFullname(rs.getString("fullname"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setRole_id(rs.getInt("role_id"));
				user.setProfile_picture(rs.getString("profile_picture"));

				userList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userList;
	}
	
	/**
     * Retrieves all users filtered by role name.
     *
     * @param role the role name to filter users by.
     * @return List of UserModel objects matching the role.
     * @throws ClassNotFoundException if DB driver not found.
     */
	public List<UserModel> getAllUsers(String role) throws ClassNotFoundException {
	    List<UserModel> userList = new ArrayList<>();

	    String roleQuery = "SELECT role_id FROM roles WHERE name = ?";
	    String userQuery = "SELECT user_id, fullname, email, username, role_id, profile_picture FROM user WHERE role_id = ?";

	    try (Connection conn = DbConfig.getDbConnection();
	         PreparedStatement roleStmt = conn.prepareStatement(roleQuery)) {

	        roleStmt.setString(1, role);
	        try (ResultSet roleRs = roleStmt.executeQuery()) {
	            if (roleRs.next()) {
	                int roleId = roleRs.getInt("role_id");

	                try (PreparedStatement userStmt = conn.prepareStatement(userQuery)) {
	                    userStmt.setInt(1, roleId);
	                    try (ResultSet rs = userStmt.executeQuery()) {
	                        while (rs.next()) {
	                            UserModel user = new UserModel();
	                            user.setUser_id(rs.getInt("user_id"));
	                            user.setFullname(rs.getString("fullname"));
	                            user.setEmail(rs.getString("email"));
	                            user.setUsername(rs.getString("username"));
	                            user.setRole_id(rs.getInt("role_id"));
	                            user.setProfile_picture(rs.getString("profile_picture"));

	                            userList.add(user);
	                        }
	                    }
	                }

	            } else {
	                System.out.println("Role not found: " + role);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return userList;
	}

	/**
     * Retrieves a user by their user ID.
     *
     * @param id The user_id to look up.
     * @return UserModel object or null if not found.
     * @throws ClassNotFoundException if DB driver not found.
     */
	public UserModel getUserById(int id) throws ClassNotFoundException {
		UserModel user = null;

		String sql = "SELECT * FROM user WHERE user_id = ?";

		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				user = new UserModel();
				user.setUser_id(rs.getInt("user_id"));
				user.setFullname(rs.getString("fullname"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setRole_id(rs.getInt("role_id"));
				user.setProfile_picture(rs.getString("profile_picture"));
				user.setBio(rs.getString("bio"));
				user.setPassword(rs.getString("password"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	/**
     * Deletes a user by their user ID.
     *
     * @param id The user_id of the user to delete.
     * @return true if deletion was successful, false otherwise.
     * @throws ClassNotFoundException if DB driver not found.
     */
	public Boolean deleteUser(int id) throws ClassNotFoundException {
		String sql = "DELETE FROM user WHERE user_id = ?";
		boolean rowDeleted = false;

		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			int affectedRows = stmt.executeUpdate();

			rowDeleted = affectedRows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowDeleted;
	}

	/**
     * Updates an existing user's details.
     *
     * @param updatedUser UserModel containing updated fields.
     * @return true if update succeeded, false otherwise.
     * @throws ClassNotFoundException if DB driver not found.
     */
	public boolean updateUser(UserModel updatedUser) throws ClassNotFoundException {
	    String sql = "UPDATE user SET fullname = ?, email = ?, username = ?, role_id = ?, password = ?, profile_picture = ? WHERE user_id = ?";
	    boolean updated = false;

	    try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
	        stmt.setString(1, updatedUser.getFullname());
	        stmt.setString(2, updatedUser.getEmail());
	        stmt.setString(3, updatedUser.getUsername());
	        stmt.setInt(4, updatedUser.getRole_id());
	        stmt.setString(5, updatedUser.getPassword());
	        stmt.setString(6, updatedUser.getProfile_picture());
	        stmt.setInt(7, updatedUser.getUser_id());

	        int rowsAffected = stmt.executeUpdate();
	        updated = rowsAffected > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return updated;
	}
	
	/**
     * Retrieves a user by username.
     *
     * @param username The username to look up.
     * @return UserModel if found, else null.
     */
	public UserModel getUserByUsername(String username) {
		String sql = "SELECT * FROM user WHERE username = ?";
		
		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
	        stmt.setString(1, username);

	        ResultSet rs = stmt.executeQuery();
	        UserModel user = null;
	        if (rs.next()) {
				user = new UserModel();
				user.setUser_id(rs.getInt("user_id"));
				user.setFullname(rs.getString("fullname"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setRole_id(rs.getInt("role_id"));
				user.setProfile_picture(rs.getString("profile_picture"));
				user.setBio(rs.getString("bio"));
				user.setCreatedAt(rs.getDate("created_at"));
			}
	        return user;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	/**
     * Retrieves the most recent users up to a specified limit.
     *
     * @param limit maximum number of users to return.
     * @return List of most recent UserModel objects.
     */
	public List<UserModel> getMostRecentUsers(int limit) {
	    List<UserModel> recentUsers = new ArrayList<>();
	    
	    String query = "SELECT * FROM user ORDER BY created_at DESC LIMIT ?";
	    
	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        stmt.setInt(1, limit); 
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            UserModel user = new UserModel();
	            user.setUser_id(rs.getInt("user_id"));
	            user.setUsername(rs.getString("username"));
	            user.setPassword(rs.getString("password"));
	            user.setFullname(rs.getString("fullname"));
	            user.setEmail(rs.getString("email"));
	            user.setCreatedAt(rs.getDate("created_at")); 

	            recentUsers.add(user);
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return recentUsers;
	}
	
	/**
     * Counts the number of users with a specific role and approval status.
     *
     * @param roleName   The role name to filter by.
     * @param isApproved The approval status to filter by.
     * @return Count of approved users with the specified role.
     */
	public int getApprovedUsersCountByRole(String roleName, Boolean isApproved) {
	    int approvedUsersCount = 0;

	    String query = "SELECT COUNT(*) FROM user u " +
	                   "JOIN roles r ON u.role_id = r.role_id " +
	                   "WHERE r.name = ? AND u.is_approved = ?";
	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        stmt.setString(1, roleName);
	        stmt.setBoolean(2, isApproved);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            approvedUsersCount = rs.getInt(1);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return approvedUsersCount;
	}

}
