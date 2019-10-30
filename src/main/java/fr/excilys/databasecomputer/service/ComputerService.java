package fr.excilys.databasecomputer.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.databasecomputer.dao.implement.ComputerDAO;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;

@Service
public class ComputerService {
	@Autowired
	private ComputerDAO computerDAO;

	private ComputerService() { }

	public Boolean addComputer(Computer computer)  {
		return computerDAO.addComputer(computer);
	}

 	public ArrayList<Computer> displayAllComputer()  {
		return computerDAO.findAll();
	}

 	public ArrayList<Computer> displayAllComputer(int limite, int offset, String order)  {
		return computerDAO.findAll(limite, offset, order);
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

	public ArrayList<Computer> findComputerCompanyByName(String name, int limite, int offset, String order)  {
		return computerDAO.findComputerByName(name, limite, offset, order);
	}

	public int nbComputerCompanyFindByName(String name) {
		return computerDAO.nbComputerFindByName(name);
	}
}
