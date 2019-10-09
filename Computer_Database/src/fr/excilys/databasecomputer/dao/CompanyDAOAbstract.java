package fr.excilys.databasecomputer.dao;

import java.sql.Connection;
import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.entity.Company;

public abstract class CompanyDAOAbstract {

protected Connection connet = null;
	
	public CompanyDAOAbstract (Connection conn) {
		this.connet =conn;
	}
	
	public abstract ArrayList<Company> findAll();
	
	public abstract ArrayList<Company> findAll(int limite);
	
	public abstract int nbCompany();
}
