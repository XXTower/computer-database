package fr.excilys.databasecomputer.service;

import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.implement.ComputerDAO;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;

public class ComputerService {
	private static ComputerService instance;
	private ComputerDAO computerDAO;

	private ComputerService() {
		this.computerDAO = ComputerDAO.getInstance();
	}

	public static ComputerService getInstance() {
		if (instance == null) {
			instance = new ComputerService();
		}
		return instance;
	}

	public Boolean addComputer(Computer computer)  {
		return computerDAO.addComputer(computer);
	}

 	public ArrayList<Computer> displayAllComputer()  {
		return computerDAO.findAll();
	}

 	public ArrayList<Computer> displayAllComputer(int limite, int offset)  {
		return computerDAO.findAll(limite, offset);
	}

	public Computer displayOneComputeur(int idComputer) throws SQLExceptionComputerNotFound  {
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

	public boolean deleteComputerByCompanyName(String companyName) {
		return computerDAO.deleteComputerByCompanyName(companyName);
	}
}
