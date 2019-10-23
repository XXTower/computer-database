package fr.excilys.databasecomputer.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnextionDB {

	private static Connection connect;
	private static ConnextionDB instance;
	
	private static HikariConfig config = new HikariConfig("/database.properties");
    private static HikariDataSource ds = new HikariDataSource( config );

	private ConnextionDB() { }

	public Connection getConnection() {
		try {
			connect = ds.getConnection();
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}

		}
		return connect;
	}

	public static ConnextionDB getInstance() {
		if (instance == null) {
			instance = new ConnextionDB();
		}
		return instance;
	}

	public static Connection disconnectDB() {
		if (connect != null) {
			try {
				connect.close();
				connect = null;
			} catch (SQLException se) {
				for (Throwable e : se) {
					System.err.println("Problèmes rencontrés: " + e);
				}
			}
		}
		return connect;
	}

	public void testURL() {
//		ConnextionDB.url = "jdbc:h2:mem:computer-database-db;INIT=RUNSCRIPT FROM '~/Documents/SQLH2/1-SCHEMA.sql'";
	}

}
