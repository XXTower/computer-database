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

	public void displayAllCompany() {
		ArrayList<Company> companys = companyDAO.findAll();
		for (Company company : companys) {
			System.out.println(company.toString());
		}
	}

	public void displayAllCompany(int limite, int offset) {
		for (Company company : companyDAO.findAll(limite, offset)) {
			System.out.println(company.toString());
		}
	}

	public int nbCompany() {
		return companyDAO.nbCompany();
	}
}
