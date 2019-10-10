package fr.excilys.databasecomputer.dao.implement;

import java.sql.*;
import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Computer;

public class ComputerDAO {
	
	private Connection conn;
	
	public ComputerDAO(){
	 this.conn = ConnextionDB.getInstance().getConnection();
	}
	
	public Computer find(int id) throws SQLException{
		Computer computer = new Computer();
		try {
			PreparedStatement stm = this.conn.prepareStatement("Select * from computer "
					+ "left join  company on computer.company_id = company.id "
					+ "Where computer.id = ?");
			stm.setInt(1, id);
			ResultSet result = stm.executeQuery();
			System.out.print(result.next());
			if(result.next()) {
				computer.setId(result.getInt("computer.id"));
				computer.setName(result.getString("computer.name"));
				computer.setIntroduced(result.getDate("computer.introduced"));
				computer.setDiscontinued(result.getDate("computer.discontinued"));
				computer.setCompany(new Company(result.getInt("company.id"),result.getString("company.name")));
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
		try {
			PreparedStatement stm = this.conn.prepareStatement("Update computer set name=?, introduced=?, discontinued=?, company_id=? Where id=?");
			stm.setString(1, computer.getName());
			stm.setDate(2, computer.getIntroduced());
			stm.setDate(3, computer.getDiscontinued());
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
		try {
			PreparedStatement stm = this.conn.prepareStatement("Delete from computer where id = ?");
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
		try {
			PreparedStatement stm = this.conn.prepareStatement("Select * from computer "
					+ "left join  company on computer.company_id = company.id Order by computer.id");
			ResultSet result = stm.executeQuery();
			
			while(result.next()) {
				Computer computer = new Computer();
				computer.setId(result.getInt("computer.id"));
				computer.setName(result.getString("computer.name"));
				computer.setIntroduced(result.getDate("computer.introduced"));
				computer.setDiscontinued(result.getDate("computer.discontinued"));
				computer.setCompany(new Company(result.getInt("company.id"),result.getString("company.name")));
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
		try {
			PreparedStatement stm = this.conn.prepareStatement("Insert Into computer (name,introduced,discontinued,company_id) Values (?,?,?,(Select id from company where name like ?))");
			stm.setString(1, computer.getName());
			stm.setDate(2, computer.getIntroduced());
			stm.setDate(3, computer.getDiscontinued());
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
		try {
			PreparedStatement stm = this.conn.prepareStatement("Select count(*) as nbComputer from computer");
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
		try {
			PreparedStatement stm = this.conn.prepareStatement("Select * from computer "
					+ "left join  company on computer.company_id = company.id Order by computer.id Limit ? offset ?");
			stm.setInt(1, limite);
			stm.setInt(2, offset);
			ResultSet result = stm.executeQuery();
			
			while(result.next()) {
				Computer computer = new Computer();
				computer.setId(result.getInt("computer.id"));
				computer.setName(result.getString("computer.name"));
				computer.setIntroduced(result.getDate("computer.introduced"));
				computer.setDiscontinued(result.getDate("computer.discontinued"));
				computer.setCompany(new Company(result.getInt("company.id"),result.getString("company.name")));
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
