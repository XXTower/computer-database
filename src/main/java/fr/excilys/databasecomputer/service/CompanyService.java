package fr.excilys.databasecomputer.service;

import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.implement.CompanyDAO;
import fr.excilys.databasecomputer.entity.Company;

public class CompanyService {
	private CompanyDAO companyDAO;
	private static CompanyService instance;

	private CompanyService() {
		this.companyDAO = CompanyDAO.getInstance();
	}

	public static CompanyService getInstance() {
		if (instance == null) {
			instance = new CompanyService();
		}
		return instance;
	}

	public ArrayList<Company> displayAllCompany() {
		return companyDAO.findAll();
	}

	public ArrayList<Company> displayAllCompany(int limite, int offset) {
		return companyDAO.findAll(limite, offset);
	}

	public int nbCompany() {
		return companyDAO.nbCompany();
	}
}
