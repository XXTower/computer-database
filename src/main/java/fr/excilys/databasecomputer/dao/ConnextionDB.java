package fr.excilys.databasecomputer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnextionDB {

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?useSSL=false";
	private static String user = "admincdb";
	private static String password = "qwerty1234";
	private static Connection connect;
	private static ConnextionDB instance;
	
	private ConnextionDB() {}
	
	public Connection getConnection() {
		try {
			connect = DriverManager.getConnection(url, user, password);
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}
		return connect;
	}
	
	public static ConnextionDB getInstance() {
		if (instance==null ) {
			instance = new ConnextionDB();
		}
		return instance;
	}
	
	public static Connection disconnectDB() {
		if(connect!=null) {
			try {
				connect.close();
				connect=null;
			} catch (SQLException se) {
				for(Throwable e : se) {
					System.err.println("Problèmes rencontrés: " + e);
				}
			}
		}
		return connect;
	}
	
}