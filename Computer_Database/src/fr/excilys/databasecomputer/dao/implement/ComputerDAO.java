package fr.excilys.databasecomputer.dao.implement;

import java.sql.*;
import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.ComputerDAOAbstract;
import fr.excilys.databasecomputer.dao.entity.Company;
import fr.excilys.databasecomputer.dao.entity.Computer;

public class ComputerDAO extends ComputerDAOAbstract {
	
	public ComputerDAO(Connection conn){
		super(conn);
	}
	
	public Computer find(int id) {
		Computer computer = new Computer();
		try {
			PreparedStatement stm = this.connet.prepareStatement("Select * from computer "
					+ "left join  company on computer.company_id = company.id "
					+ "Where computer.id = ?");
			stm.setInt(1, id);
			ResultSet result = stm.executeQuery();
			if(result.first()) {
				computer.setId(result.getInt("computer.id"));
				computer.setName(result.getString("computer.name"));
				computer.setIntroduced(result.getDate("computer.introduced"));
				computer.setDiscontinued(result.getDate("computer.discontinued"));
				computer.setCompany(new Company(result.getInt("company.id"),result.getString("company.name")));
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}
		
		return computer;
	}

	public boolean update(Computer computer) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<Computer> findAll() {
		ArrayList<Computer> computers = new ArrayList<>();
		try {
			PreparedStatement stm = this.connet.prepareStatement("Select * from computer "
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
		}
		
		return computers;
	}
	
	
}