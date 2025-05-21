package com.wordweave.models;

/**
 * Represents a user role in the system.
 * Each role typically defines a set of permissions or access rights.
 */
public class RoleModel {
    private int role_id;
    private String name;

	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
