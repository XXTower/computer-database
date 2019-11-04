package fr.excilys.databasecomputer.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import fr.excilys.databasecomputer.mapper.ComputerMapper;
import fr.excilys.databasecomputer.mapper.DateMapper;

@Repository
public class ComputerDAO {
	private static final String FIND_ALL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.id";
	private static final String FIND_ALL_LIMIT_OFFSET = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY "
			+ "(CASE ? WHEN 'ASC' THEN computer.name END) ASC,(CASE ? WHEN 'DESC' THEN computer.name END) DESC "
			+ "LIMIT ? OFFSET ?";
	private static final String FIND_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? ORDER BY computer.id ";
	private static final String UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=(SELECT id FROM company WHERE name LIKE ?) WHERE id=?";
	private static final String NB_COMPUTER = "SELECT COUNT(id) AS nbComputer FROM computer";
	private static final String NB_COMPUTER_FIND_BY_NAME = "SELECT COUNT(computer.id) AS nbComputer FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE ? OR computer.name LIKE ?";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
	private static final String INSERT_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,(SELECT id FROM company WHERE name LIKE ?))";
	private static final String DELETE_COMPUTER_NAME_COMPANY = "DELETE computer FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE ?";
	private static final String SEARCH_COMPUTER_COMPANY_BY_NAME = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE ? OR computer.name LIKE ? ORDER BY "
			+ "(CASE ? WHEN 'ASC' THEN computer.name END) ASC,(CASE ? WHEN 'DESC' THEN computer.name END) DESC "
			+ "LIMIT ? OFFSET ?";
	private Connection conn;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private ConnextionDB connectionDB;
	@Autowired
	private DataSource dataSource;
	private ComputerDAO() {	}

	public Computer find(int id) throws SQLExceptionComputerNotFound {
		this.conn = connectionDB.getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(FIND_BY_ID);) {
			stm.setInt(1, id);
			ResultSet result = stm.executeQuery();
			if (result.next()) {
				return	computerMapper.sqlToComputer(result);
			} else {
				throw new SQLExceptionComputerNotFound("Aucun ordinateur trouver a pour cette id");
			}
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = connectionDB.disconnectDB();
		}
		return null;
	}

	public boolean update(Computer computer) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		int result = template.update(UPDATE, computer.getName(),DateMapper.changeToDateSQL(computer.getIntroduced()),
				DateMapper.changeToDateSQL(computer.getDiscontinued()),computer.getCompany().getName(),computer.getId() );
		return result == 1;
	}

	public boolean delete(int id) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		int result = template.update(DELETE_COMPUTER, id );
		return result != 0;
	}

	public ArrayList<Computer> findAll() {
		ArrayList<Computer> computers = new ArrayList<>();
		this.conn = connectionDB.getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(FIND_ALL);) {
			ResultSet result = stm.executeQuery();
			while (result.next()) {
				computers.add(computerMapper.sqlToComputer(result));
			}
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = connectionDB.disconnectDB();
		}
		return computers;
	}

	public boolean addComputer(Computer computer) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		int result = template.update(INSERT_COMPUTER, computer.getName(),DateMapper.changeToDateSQL(computer.getIntroduced()),
				DateMapper.changeToDateSQL(computer.getDiscontinued()), computer.getCompany().getName());
		return result == 1;
	}

	public int nbComputer() {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		return template.queryForObject(NB_COMPUTER, Integer.class);
	}

	public ArrayList<Computer> findAll(int limite, int offset, String order) {
		ArrayList<Computer> computers = new ArrayList<>();
		this.conn = connectionDB.getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(FIND_ALL_LIMIT_OFFSET);) {
			stm.setString(1, order);
			stm.setString(2, order);
			stm.setInt(3, limite);
			stm.setInt(4, offset);
			ResultSet result = stm.executeQuery();
			while (result.next()) {
				computers.add(computerMapper.sqlToComputer(result));
			}
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = connectionDB.disconnectDB();
		}
		return computers;
	}

	public boolean deleteComputerByCompanyName(String companyName) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		int result = template.update(DELETE_COMPUTER_NAME_COMPANY, companyName);
		return result != 0;
	}

	public ArrayList<Computer> findComputerByName(String name, int limite, int offset, String order) {
		ArrayList<Computer> computers = new ArrayList<>();
		this.conn = connectionDB.getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(SEARCH_COMPUTER_COMPANY_BY_NAME);) {
			stm.setString(1, "%" + name + "%");
			stm.setString(2, "%" + name + "%");
			stm.setString(3, order);
			stm.setString(4, order);
			stm.setInt(5, limite);
			stm.setInt(6, offset);
			ResultSet result = stm.executeQuery();
			while (result.next()) {
				computers.add(computerMapper.sqlToComputer(result));
			}
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = connectionDB.disconnectDB();
		}
		return computers;
	}

	public int nbComputerFindByName(String name) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		return template.queryForObject(NB_COMPUTER_FIND_BY_NAME, Integer.class,"%" + name + "%","%" + name + "%" );
	}
}
