package fr.excilys.databasecomputer.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.excilys.databasecomputer.Mapper.ComputerMapper;
import fr.excilys.databasecomputer.Mapper.DateMapper;
import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;

public class ComputerDAO {
	private static final String FIND_ALL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.id";
	private static final String FIND_ALL_LIMIT_OFFSET = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.id LIMIT ? OFFSET ?";
	private static final String FIND_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? ORDER BY computer.id ";
	private static final String UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=(SELECT id FROM company WHERE name LIKE ?) WHERE id=?";
	private static final String NB_COMPUTER = "SELECT COUNT(id) AS nbComputer FROM computer";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
	private static final String INSERT_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,(SELECT id FROM company WHERE name LIKE ?))";

	private Connection conn;
	private static ComputerDAO instance;
	private ComputerMapper computerMapper;

	private ComputerDAO() {	}

	public static ComputerDAO getInstance() {
		if (instance == null) {
			instance = new ComputerDAO();
		}
		return instance;
	}

	public Computer find(int id) throws SQLExceptionComputerNotFound {
		this.conn = ConnextionDB.getInstance().getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(FIND_BY_ID);) {
			stm.setInt(1, id);
			computerMapper = ComputerMapper.getInstance();
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
			this.conn = ConnextionDB.disconnectDB();
		}
		return null;
	}

	public boolean update(Computer computer) {
		this.conn = ConnextionDB.getInstance().getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(UPDATE);) {
			stm.setString(1, computer.getName());
			stm.setDate(2, DateMapper.changeToDateSQL(computer.getIntroduced()));
			stm.setDate(3, DateMapper.changeToDateSQL(computer.getDiscontinued()));
			stm.setString(4, computer.getCompany().getName());
			stm.setInt(5, computer.getId());
			int result = stm.executeUpdate();
			return result == 1;
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = ConnextionDB.disconnectDB();
		}
		return false;
	}

	public boolean delete(int id) {
		this.conn = ConnextionDB.getInstance().getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(DELETE_COMPUTER);) {
			stm.setInt(1, id);
			int result = stm.executeUpdate();
			return result != 0;
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = ConnextionDB.disconnectDB();
		}
		return false;
	}

	public ArrayList<Computer> findAll() {
		ArrayList<Computer> computers = new ArrayList<>();
		this.conn = ConnextionDB.getInstance().getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(FIND_ALL);) {
			ResultSet result = stm.executeQuery();
			computerMapper = ComputerMapper.getInstance();
			while (result.next()) {
				computers.add(computerMapper.sqlToComputer(result));
			}
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = ConnextionDB.disconnectDB();
		}
		return computers;
	}

	public boolean addComputer(Computer computer) {
		this.conn = ConnextionDB.getInstance().getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(INSERT_COMPUTER);) {
			stm.setString(1, computer.getName());
			stm.setDate(2, DateMapper.changeToDateSQL(computer.getIntroduced()));
			stm.setDate(3, DateMapper.changeToDateSQL(computer.getDiscontinued()));
			stm.setString(4, computer.getCompany().getName());
			int result = stm.executeUpdate();
			return result == 1;
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = ConnextionDB.disconnectDB();
		}
		return false;
	}

	public int nbComputer() {
		this.conn = ConnextionDB.getInstance().getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(NB_COMPUTER);) {
			ResultSet result = stm.executeQuery();
			if (result.first()) {
				return result.getInt("nbComputer");
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

	public ArrayList<Computer> findAll(int limite, int offset) {
		ArrayList<Computer> computers = new ArrayList<>();
		this.conn = ConnextionDB.getInstance().getConnection();
		try (PreparedStatement stm = this.conn.prepareStatement(FIND_ALL_LIMIT_OFFSET);) {
			stm.setInt(1, limite);
			stm.setInt(2, offset);
			ResultSet result = stm.executeQuery();
			computerMapper = ComputerMapper.getInstance();
			while (result.next()) {
				computers.add(computerMapper.sqlToComputer(result));
			}
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		} finally {
			this.conn = ConnextionDB.disconnectDB();
		}
		return computers;
	}
}
