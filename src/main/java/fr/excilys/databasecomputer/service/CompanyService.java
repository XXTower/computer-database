package fr.excilys.databasecomputer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.databasecomputer.dao.implement.CompanyDAO;
import fr.excilys.databasecomputer.entity.Company;

@Service
public class CompanyService {
	
	private CompanyDAO companyDAO;

	@Autowired
	private CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}


	public List<Company> displayAllCompany() {
		return companyDAO.findAll();
	}

	public List<Company> displayAllCompany(int limite, int offset) {
		return companyDAO.findAll(limite, offset);
	}

	public int nbCompany() {
		return companyDAO.nbCompany();
	}

	public boolean deleteCopany(String companyName) {
		return companyDAO.deleteCompany(companyName);
	}
}
