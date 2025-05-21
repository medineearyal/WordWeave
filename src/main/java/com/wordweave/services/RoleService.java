package com.wordweave.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wordweave.config.DbConfig;
import com.wordweave.models.RoleModel;

public class RoleService {

	private Connection dbConn;

	/**
     * Constructor initializes the database connection.
     * Catches and prints exceptions if connection fails.
     */
	public RoleService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Fetches all roles from the database.
	 *
	 * @return a list of RoleModel objects
	 */
	public List<RoleModel> getAllRoles() {
		List<RoleModel> roleList = new ArrayList<>();
		String sql = "SELECT role_id, name FROM roles"; // Assuming the table is `roles` and has these columns

		try (PreparedStatement stmt = dbConn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				RoleModel role = new RoleModel();
				role.setRole_id(rs.getInt("role_id"));
				role.setName(rs.getString("name"));
				roleList.add(role);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return roleList;
	}

	/**
     * Retrieves a role by its name.
     *
     * @param name The name of the role to fetch.
     * @return RoleModel if found, otherwise null.
     */
	public RoleModel getRole(String name) {
		RoleModel role = null;
		String sql = "SELECT role_id, name FROM roles WHERE name = ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				role = new RoleModel();
				role.setRole_id(rs.getInt("role_id"));
				role.setName(rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return role;
	}

	/**
     * Retrieves the role associated with a specific user ID.
     *
     * @param userId The ID of the user whose role is fetched.
     * @return RoleModel of the user's role, or null if not found.
     */
	public RoleModel getUserRole(int userId) {
		RoleModel role = null;
		String sql = "SELECT r.role_id, r.name FROM roles r " + "JOIN user u ON r.role_id = u.role_id "
				+ "WHERE u.user_id = ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				role = new RoleModel();
				role.setRole_id(rs.getInt("role_id"));
				role.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return role;
	}
}
