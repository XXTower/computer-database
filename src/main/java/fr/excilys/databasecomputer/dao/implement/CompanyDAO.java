package fr.excilys.databasecomputer.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.mapper.CompanyMapper;

@Repository
public class CompanyDAO {
	@Autowired
	private CompanyMapper companyMapper;
	private Connection conn;
	private static final String FIND_ALL = "SELECT id, name FROM company ORDER BY id";
	private static final String FIND_ALL_LIMITE_OFFSET = "SELECT id, name FROM company ORDER BY id LIMIT ? OFFSET ?";
	private static final String NB_COMPANY = "SELECT COUNT(id) AS nbCompany FROM company";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE name LIKE ?";

	@Autowired
	private ConnextionDB connectionDB;

	private CompanyDAO() { }

	public ArrayList<Company> findAll() {
		this.conn = connectionDB.getConnection();
		ArrayList<Company> companys = new ArrayList<>();
		try (PreparedStatement stm = this.conn.prepareStatement(FIND_ALL);) {
			ResultSet result = stm.executeQuery();

			while (result.next()) {
				companys.add(companyMapper.sqlToComputer(result));
			}
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = connectionDB.disconnectDB();
		}
		return companys;
	}

	public ArrayList<Company> findAll(int limite, int offset) {
		this.conn = connectionDB.getConnection();
		ArrayList<Company> companys = new ArrayList<>();
		try (PreparedStatement stm = this.conn.prepareStatement(FIND_ALL_LIMITE_OFFSET);) {
			stm.setInt(1, limite);
			stm.setInt(2, offset);
			ResultSet result = stm.executeQuery();
			while (result.next()) {
				companys.add(companyMapper.sqlToComputer(result));
			}
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = connectionDB.disconnectDB();
		}
		return companys;
	}

	public int nbCompany() {
		this.conn = connectionDB.getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(NB_COMPANY);) {

			ResultSet result = stm.executeQuery();
			if (result.first()) {
				return result.getInt("nbCompany");
			}
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = connectionDB.disconnectDB();
		}
		return 0;
	}

	public boolean deleteCompany(String companyName) {
		this.conn = connectionDB.getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(DELETE_COMPANY);) {
			stm.setString(1, companyName);
			int result = stm.executeUpdate();
			return result != 0;
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = connectionDB.disconnectDB();
		}
		return false;
	}
}
