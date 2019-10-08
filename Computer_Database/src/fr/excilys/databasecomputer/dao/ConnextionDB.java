package fr.excilys.databasecomputer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnextionDB {

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db";
	private static String user = "admincdb";
	private static String password = "qwerty1234";
	private static Connection connect;
	
	//retour une nouvelle instance de BDD si n'a pas déja été crer 
	public static Connection getInstance() {
		if(connect == null) {
			try {
				connect = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connect;
	}
}
