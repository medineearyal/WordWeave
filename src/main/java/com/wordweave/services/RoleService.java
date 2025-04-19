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
	
	public RoleModel getUserRole(int userId) {
	    RoleModel role = null;
	    String sql = "SELECT r.role_id, r.name FROM roles r " +
	                 "JOIN user u ON r.role_id = u.role_id " +
	                 "WHERE u.user_id = ?";

	    try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);  // Set the userId parameter
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            // Create and populate RoleModel object
	            role = new RoleModel();
	            role.setRole_id(rs.getInt("role_id"));
	            role.setName(rs.getString("name"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return role;  // Return the role or null if no role found
	}
}
