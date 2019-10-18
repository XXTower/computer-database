package fr.excilys.databasecomputer.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.mapper.CompanyMapper;

public class CompanyDAO {
	private CompanyMapper companyMapper;
	private Connection conn;
	private static final String FIND_ALL = "SELECT id, name FROM company ORDER BY id";
	private static final String FIND_ALL_LIMITE_OFFSET = "SELECT id, name FROM company ORDER BY id LIMIT ? OFFSET ?";
	private static final String NB_COMPANY = "SELECT COUNT(id) AS nbCompany FROM company";
	private static CompanyDAO instance;

	private CompanyDAO() { }

	public static CompanyDAO getInstance() {
		if (instance == null) {
			instance = new CompanyDAO();
		}
		return instance;
	}

	public ArrayList<Company> findAll() {
		this.conn = ConnextionDB.getInstance().getConnection();
		ArrayList<Company> companys = new ArrayList<>();
		try (PreparedStatement stm = this.conn.prepareStatement(FIND_ALL);) {
			companyMapper = CompanyMapper.getInstance();
			ResultSet result = stm.executeQuery();

			while (result.next()) {
				companys.add(companyMapper.sqlToComputer(result));
			}
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = ConnextionDB.disconnectDB();
		}
		return companys;
	}

	public ArrayList<Company> findAll(int limite, int offset) {
		this.conn = ConnextionDB.getInstance().getConnection();
		ArrayList<Company> companys = new ArrayList<>();
		try (PreparedStatement stm = this.conn.prepareStatement(FIND_ALL_LIMITE_OFFSET);) {
			companyMapper = CompanyMapper.getInstance();
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
			this.conn = ConnextionDB.disconnectDB();
		}
		return companys;
	}

	public int nbCompany() {
		this.conn = ConnextionDB.getInstance().getConnection();
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
			this.conn = ConnextionDB.disconnectDB();
		}
		return 0;
	}
}
