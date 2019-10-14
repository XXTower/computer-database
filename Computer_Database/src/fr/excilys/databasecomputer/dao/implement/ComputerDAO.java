package fr.excilys.databasecomputer.dao.implement;

import java.sql.*;
import java.util.ArrayList;

import fr.excilys.databasecomputer.Mapper.ComputerMapper;
import fr.excilys.databasecomputer.Mapper.DateMapper;
import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;

public class ComputerDAO {
	private final static String FIND_ALL = "SELECT cmt.id, cmt.name, cmt.introduced, cmt.discontinued, cmp.id, cmp.name "
			+ "FROM computer AS cmt LEFT JOIN  company AS cmp ON cmt.company_id = cmp.id ORDER BY cmt.id";
	
	private final static String FIND_ALL_LIMIT_OFFSET = "SELECT cmt.id, cmt.name, cmt.introduced, cmt.discontinued, cmp.id, cmp.name "
			+ "FROM computer AS cmt LEFT JOIN  company AS cmp ON cmt.company_id = cmp.id ORDER BY cmt.id LIMIT ? OFFSET ?";
	
	private final static String FIND_BY_ID ="SELECT cmt.id, cmt.name, cmt.introduced, cmt.discontinued, cmp.id, cmp.name "
			+ "FROM computer AS cmt LEFT JOIN  company AS cmp ON cmt.company_id = cmp.id WHERE cmt.id = ? ORDER BY cmt.id ";
	
	private final static String UPDATE ="UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=(SELECT id FROM company WHERE name LIKE ?) WHERE id=?";
	
	private final static String NB_COMPUTER ="SELECT COUNT(id) AS nbComputer FROM computer";
	
	private final static String DELETE_COMPUTER ="DELETE FROM computer WHERE id = ?";
	
	private final static String INSERT_COMPUTER ="INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,(SELECT id FROM company WHERE name LIKE ?))";
	
	private Connection conn;
	private static ComputerDAO instance;
	
	private ComputerDAO(){	}
		
	public static ComputerDAO getInstance() {
		if(instance==null) {
			instance= new ComputerDAO();
		}
		return instance;
	}

	public Computer find(int id) throws SQLExceptionComputerNotFound {
		this.conn = ConnextionDB.getInstance().getConnection();
		try(PreparedStatement stm = this.conn.prepareStatement(FIND_BY_ID);) {
			stm.setInt(1, id);
			ComputerMapper computerMapper = new ComputerMapper();
			ResultSet result = stm.executeQuery();
			if(result.next()) {	
				return	computerMapper.SQLToComputer(result);
			}else {
				throw new SQLExceptionComputerNotFound("Aucun ordinateur trouver a pour cette id");
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn=ConnextionDB.disconnectDB();
		}
		
		return null;
	}

	public boolean update(Computer computer) {
		this.conn = ConnextionDB.getInstance().getConnection();
		try(PreparedStatement stm = this.conn.prepareStatement(UPDATE);) {
			
			stm.setString(1, computer.getName());
			stm.setDate(2, DateMapper.changeToDateSQL(computer.getIntroduced()));
			stm.setDate(3, DateMapper.changeToDateSQL(computer.getDiscontinued()));
			stm.setString(4, computer.getCompany().getName());
			stm.setInt(5, computer.getId());
			int result = stm.executeUpdate();
			return result==1;
			
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn=ConnextionDB.disconnectDB();
		}
		return false;
	}

	public boolean delete(int id) {
		this.conn = ConnextionDB.getInstance().getConnection();
		try(PreparedStatement stm = this.conn.prepareStatement(DELETE_COMPUTER);) {
			
			stm.setInt(1, id);
			int result = stm.executeUpdate();
			return result!=0;
			
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn=ConnextionDB.disconnectDB();
		}
		return false;
	}

	public ArrayList<Computer> findAll() {
		ArrayList<Computer> computers = new ArrayList<>();
		this.conn = ConnextionDB.getInstance().getConnection();
		try(PreparedStatement stm = this.conn.prepareStatement(FIND_ALL);) {
			ResultSet result = stm.executeQuery();
			ComputerMapper computerMapper = new ComputerMapper();
			while(result.next()) {	
				computers.add(computerMapper.SQLToComputer(result));
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn=ConnextionDB.disconnectDB();
		}
		
		return computers;
	}
	
	public boolean addComputer(Computer computer) {
		this.conn = ConnextionDB.getInstance().getConnection();
		try(PreparedStatement stm = this.conn.prepareStatement(INSERT_COMPUTER);) {
			
			stm.setString(1, computer.getName());
			stm.setDate(2, DateMapper.changeToDateSQL(computer.getIntroduced()));
			stm.setDate(3, DateMapper.changeToDateSQL(computer.getDiscontinued()));
			stm.setString(4, computer.getCompany().getName());
			int result = stm.executeUpdate();
			return result==1;
			
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn=ConnextionDB.disconnectDB();
		}
		return false;
	}

	public int nbComputer() {
		this.conn = ConnextionDB.getInstance().getConnection();
		try(PreparedStatement stm = this.conn.prepareStatement(NB_COMPUTER);) {
			
			ResultSet result = stm.executeQuery();
			if(result.first()) {
				return result.getInt("nbComputer");
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn=ConnextionDB.disconnectDB();
		}
		
		return 0;
	}

	public ArrayList<Computer> findAll(int limite, int offset) {
		ArrayList<Computer> computers = new ArrayList<>();
		this.conn = ConnextionDB.getInstance().getConnection();
		try(PreparedStatement stm = this.conn.prepareStatement(FIND_ALL_LIMIT_OFFSET);) {
			stm.setInt(1, limite);
			stm.setInt(2, offset);
			ResultSet result = stm.executeQuery();
			ComputerMapper computerMapper = new ComputerMapper();
			while(result.next()) {
				computers.add(computerMapper.SQLToComputer(result));
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn=ConnextionDB.disconnectDB();
		}
		
		return computers;
	}
	
	
}
