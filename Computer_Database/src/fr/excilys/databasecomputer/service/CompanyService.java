package fr.excilys.databasecomputer.service;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.implement.CompanyDAO;
import fr.excilys.databasecomputer.entity.Company;

public class CompanyService{
	
	private CompanyDAO companyDAO;

	public CompanyService() {
		this.companyDAO=CompanyDAO.getInstance();
	}
	public void displayAllCompany() throws SQLException {
		ArrayList<Company> companys = companyDAO.findAll();
		for(Company company : companys) {
			System.out.println(company.toString());
		}	
	}
	
	public void displayAllCompany(int limite, int offset) throws SQLException {
		for(Company company : companyDAO.findAll(limite,offset)) {
			System.out.println(company.toString());
		}	
	}
	
	public int nbCompany() throws SQLException {
		return companyDAO.nbCompany();			
	}
}
