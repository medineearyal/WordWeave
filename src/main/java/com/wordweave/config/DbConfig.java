package com.wordweave.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DbConfig handles the configuration and connection setup for the MySQL database.
 */
public class DbConfig {
	private static final String DB_NAME = "wordweave";
	private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "admin@123";

	/**
	 * Establishes a connection to the database.
	 *
	 * @return Connection object for the database
	 * @throws SQLException           if a database access error occurs
	 * @throws ClassNotFoundException if the JDBC driver class is not found
	 */
	public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
}