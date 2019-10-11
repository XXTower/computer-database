package fr.excilys.databasecomputer.dao.implement;

import java.sql.*;
import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Computer;

public class ComputerDAO {
	private final static String FIND_ALL = "SELECT cmt.id, cmt.name, cmt.introduced, cmt.discontinued, cmp.id, cmp.name "
			+ "FROM computer AS cmt LEFT JOIN  company AS cmp ON cmt.company_id = cmp.id ORDER BY cmt.id";
	private final static String FIND_ALL_LIMIT_OFFSET = "SELECT cmt.id, cmt.name, cmt.introduced, cmt.discontinued, cmp.id, cmp.name "
			+ "FROM computer AS cmt LEFT JOIN  company AS cmp ON cmp.company_id = cmp.id ORDER BY cmt.id LIMIT ? OFFSET ?";
	private final static String FIND_BY_ID ="SELECT cmt.id, cmt.name, cmt.introduced, cmt.discontinued, cmp.id, cmp.name "
			+ "FROM computer AS cmt LEFT JOIN  company AS cmp ON cmp.company_id = cmp.id WHERE cmt.id = ? ORDER BY cmt.id ";
	private final static String UPDATE ="UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	private final static String NB_COMPUTER ="SELECT COUNT(id) AS nbComputer FROM computer";
	private final static String DELETE_COMPUTER ="DELETE FROM computer WHERE id = ?";
	private final static String INSERT_COMPUTER ="INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,(SELECT id FROM company WHERE name LIKE ?))";
	private Connection conn;
	
	public ComputerDAO(){
	 this.conn = ConnextionDB.getInstance().getConnection();
	}
	
	public Computer find(int id) throws SQLException{
		Computer computer = new Computer();
		try(PreparedStatement stm = this.conn.prepareStatement(FIND_BY_ID);) {
			stm.setInt(1, id);
			ResultSet result = stm.executeQuery();
			if(result.next()) {
				computer.setId(result.getInt("cmt.id"));
				computer.setName(result.getString("cmt.name"));
//				computer.setIntroduced(result.getTimestamp("cmt.introduced"));
//				computer.setDiscontinued(result.getDate("cmt.discontinued"));
				computer.setCompany(new Company(result.getInt("cmp.id"),result.getString("cmp.name")));
			}else {
				
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn.close();
		}
		
		return computer;
	}

	public boolean update(Computer computer) throws SQLException {
		try(PreparedStatement stm = this.conn.prepareStatement(UPDATE);) {
			
			stm.setString(1, computer.getName());
			stm.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
			stm.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
			stm.setInt(4, computer.getCompany().getId());
			stm.setInt(5, computer.getId());
			int result = stm.executeUpdate();
			return result==1;
			
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn.close();
		}
		return false;
	}

	public boolean delete(int id) throws SQLException {
		try(PreparedStatement stm = this.conn.prepareStatement(DELETE_COMPUTER);) {
			
			stm.setInt(1, id);
			int result = stm.executeUpdate();
			return result!=0;
			
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn.close();
		}
		return false;
	}

	public ArrayList<Computer> findAll() throws SQLException {
		ArrayList<Computer> computers = new ArrayList<>();
		try(PreparedStatement stm = this.conn.prepareStatement(FIND_ALL);) {
			
			ResultSet result = stm.executeQuery();
			
			while(result.next()) {
				Computer computer = new Computer();
				computer.setId(result.getInt("cmt.id"));
				computer.setName(result.getString("cmt.name"));
//				computer.setIntroduced(result.getDate("cmt.introduced"));
//				computer.setDiscontinued(result.getDate("cmt.discontinued"));
				computer.setCompany(new Company(result.getInt("cmp.id"),result.getString("cmp.name")));
				computers.add(computer);
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn.close();
		}
		
		return computers;
	}
	
	public boolean addComputer(Computer computer) throws SQLException {
		try(PreparedStatement stm = this.conn.prepareStatement(INSERT_COMPUTER);) {
			
			stm.setString(1, computer.getName());
			stm.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
			stm.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
			stm.setString(4, computer.getCompany().getName());
			int result = stm.executeUpdate();
			return result==1;
			
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn.close();
		}
		return false;
	}

	public int nbComputer() throws SQLException {
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
			this.conn.close();
		}
		
		return 0;
	}

	public ArrayList<Computer> findAll(int limite, int offset) throws SQLException {
		ArrayList<Computer> computers = new ArrayList<>();
		try(PreparedStatement stm = this.conn.prepareStatement(FIND_ALL_LIMIT_OFFSET);) {
			
			stm.setInt(1, limite);
			stm.setInt(2, offset);
			ResultSet result = stm.executeQuery();
			
			while(result.next()) {
				Computer computer = new Computer();
				computer.setId(result.getInt("cmt.id"));
				computer.setName(result.getString("cmt.name"));
//				computer.setIntroduced(result.getTimestamp("cmt.introduced"));
//				computer.setDiscontinued(result.getTimestamp("cmt.discontinued"));
				computer.setCompany(new Company(result.getInt("cmp.id"),result.getString("cmp.name")));
				computers.add(computer);
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}finally {
			this.conn.close();
		}
		
		return computers;
	}
	
	
}
