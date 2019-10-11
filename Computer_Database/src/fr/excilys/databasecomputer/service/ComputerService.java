package fr.excilys.databasecomputer.service;

import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.implement.ComputerDAO;
import fr.excilys.databasecomputer.entity.Computer;

public class ComputerService {
	ComputerDAO computerDAO;
	
	public ComputerService() {
		this.computerDAO = ComputerDAO.getInstance();
	}

	public Boolean addComputer(Computer computer)  {
		return computerDAO.addComputer(computer);
	}
	
 	public void displayAllComputer()  {
		
		ArrayList<Computer> computers = computerDAO.findAll();
		for(Computer computer : computers) {
			System.out.println(computer.toString());
		}
	}
 	
 	public void displayAllComputer(int limite, int offset)  {
		
		ArrayList<Computer> computers = computerDAO.findAll(limite,offset);
		for(Computer computer : computers) {
			System.out.println(computer.toString());
		}
	}
	
	public Computer displayOneComputeur(int idComputer)  {
		return computerDAO.find(idComputer);
	}
	
	public boolean deleteComputer(int idComputer)  {		
		return computerDAO.delete(idComputer);
	}
	
	public Boolean updateComputer(Computer computer)  {
		return computerDAO.update(computer);
	}
	
	public int nbComputer()  {
		return computerDAO.nbComputer();
	}
	
	
}
