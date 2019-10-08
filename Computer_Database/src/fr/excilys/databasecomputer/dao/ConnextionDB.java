package fr.excilys.databasecomputer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnextionDB {

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?useSSL=false";
	private static String user = "admincdb";
	private static String password = "qwerty1234";
	private static Connection connect;
	
	public static Connection getInstance() {
		try {
			connect = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
	
	public static void closeConnection() {
		try {
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
