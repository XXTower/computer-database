package fr.excilys.databasecomputer.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class ConnextionDB {

	private static Connection connect;

	private static HikariConfig config = new HikariConfig("/database.properties");
    private static HikariDataSource ds = new HikariDataSource(config);

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

	public Connection disconnectDB() {
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
}
