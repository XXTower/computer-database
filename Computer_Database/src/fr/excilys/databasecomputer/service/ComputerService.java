package fr.excilys.databasecomputer.service;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.implement.ComputerDAO;
import fr.excilys.databasecomputer.entity.Computer;

public class ComputerService {
	ComputerDAO computerDAO;
	
	public ComputerService() {
		this.computerDAO = ComputerDAO.getInstance();
	}

	public Boolean addComputer(Computer computer) throws SQLException {
		return computerDAO.addComputer(computer);
	}
	
 	public void displayAllComputer() throws SQLException {
		
		ArrayList<Computer> computers = computerDAO.findAll();
		for(Computer computer : computers) {
			System.out.println(computer.toString());
		}
	}
 	
 	public void displayAllComputer(int limite, int offset) throws SQLException {
		
		ArrayList<Computer> computers = computerDAO.findAll(limite,offset);
		for(Computer computer : computers) {
			System.out.println(computer.toString());
		}
	}
	
	public Computer displayOneComputeur(int idComputer) throws SQLException {
		return computerDAO.find(idComputer);
	}
	
	public boolean deleteComputer(int idComputer) throws SQLException {		
		return computerDAO.delete(idComputer);
	}
	
	public Boolean updateComputer(Computer computer) throws SQLException {
		return computerDAO.update(computer);
	}
	
	public int nbComputer() throws SQLException {
		return computerDAO.nbComputer();
	}
	
	
}
