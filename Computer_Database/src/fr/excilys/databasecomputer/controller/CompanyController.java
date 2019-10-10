package fr.excilys.databasecomputer.controller;

import java.util.ArrayList;


import fr.excilys.databasecomputer.dao.CompanyDAOAbstract;
import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.dao.entity.Company;
import fr.excilys.databasecomputer.dao.implement.CompanyDAO;

public class CompanyController{

	public void displayAllCompany() {
		CompanyDAOAbstract companyDAO = new CompanyDAO(ConnextionDB.getInstance());
		ArrayList<Company> companys = companyDAO.findAll();
		for(Company company : companys) {
			System.out.println(company.toString());
		}	
	}
	
	public void displayAllCompany(int limite, int offset) {
		CompanyDAOAbstract companyDAO = new CompanyDAO(ConnextionDB.getInstance());
		ArrayList<Company> companys = companyDAO.findAll(limite,offset);
		for(Company company : companys) {
			System.out.println(company.toString());
		}	
	}
	
	public int nbCompany() {
		CompanyDAOAbstract companyDAO = new CompanyDAO(ConnextionDB.getInstance());
		return companyDAO.nbCompany();			
	}
}
