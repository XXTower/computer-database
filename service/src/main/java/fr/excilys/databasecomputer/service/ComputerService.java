package fr.excilys.databasecomputer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.excilys.databasecomputer.dao.implement.ComputerDAO;
import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.FailSaveComputer;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import fr.excilys.databasecomputer.mapper.ComputerMapper;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;
	private ComputerMapper computerMappeur;

	private ComputerService(ComputerDAO computerDAO, ComputerMapper computerMappeur) {
		this.computerDAO = computerDAO;
		this.computerMappeur = computerMappeur;
	}

	public void addComputer(Computer computer) throws FailSaveComputer {
		computerDAO.addComputer(computer);
	}

	public List<Computer> displayAllComputer() {
		return computerDAO.findAll();
	}

	public List<Computer> displayAllComputer(int limite, int offset, String order) {
		return computerDAO.findAll(limite, offset, order);
	}

	public Computer displayOneComputeur(int idComputer) throws SQLExceptionComputerNotFound {
		return computerDAO.find(idComputer);
	}

	public boolean deleteComputer(int idComputer) {
		return computerDAO.delete(idComputer);
	}

	public void updateComputer(Computer computer) throws FailSaveComputer {
		computerDAO.update(computer);
	}

	public long nbComputer() {
		return computerDAO.nbComputer();
	}

	public boolean deleteComputerByCompanyName(String companyName) {
		return computerDAO.deleteComputerByCompanyName(companyName);
	}

	public List<ComputerDTO> findComputerCompanyByName(String name, int limite, int offset, String order) {
		return computerDAO.findComputerByName(name, limite, offset, order).stream()
				.map(computer -> computerMappeur.toComputerDto(computer)).collect(Collectors.toList());
	}

	public long nbComputerCompanyFindByName(String name) {
		return computerDAO.nbComputerFindByName(name);
	}
}
