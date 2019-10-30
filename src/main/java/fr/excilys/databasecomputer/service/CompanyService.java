package fr.excilys.databasecomputer.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.databasecomputer.dao.implement.CompanyDAO;
import fr.excilys.databasecomputer.entity.Company;

@Service
public class CompanyService {
	@Autowired
	private CompanyDAO companyDAO;

	private CompanyService() { }


	public ArrayList<Company> displayAllCompany() {
		return companyDAO.findAll();
	}

	public ArrayList<Company> displayAllCompany(int limite, int offset) {
		return companyDAO.findAll(limite, offset);
	}

	public int nbCompany() {
		return companyDAO.nbCompany();
	}

	public boolean deleteCopany(String companyName) {
		return companyDAO.deleteCompany(companyName);
	}
}
