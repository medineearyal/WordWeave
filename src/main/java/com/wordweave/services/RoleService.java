package com.wordweave.services;

import com.wordweave.config.DbConfig;
import com.wordweave.models.RoleModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
