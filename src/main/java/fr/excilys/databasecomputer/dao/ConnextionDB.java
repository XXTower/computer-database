package fr.excilys.databasecomputer.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ConnextionDB {

	@Autowired
	private DataSource dataSource;
	private static Connection connect;

//	private static HikariConfig config = new HikariConfig("/database.properties");
//    private static HikariDataSource ds = new HikariDataSource(config);

	private ConnextionDB() { }

	public Connection getConnection() {
		try {
			connect = dataSource.getConnection();
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
