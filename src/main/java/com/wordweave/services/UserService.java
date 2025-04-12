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
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT username, password FROM user WHERE username = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, userModel.getUsername());
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				System.out.println(result);
				return validatePassword(result, userModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return false;
	}

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
	 * @param result       the ResultSet containing the username and password from
	 *                     the database
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
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

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
}
