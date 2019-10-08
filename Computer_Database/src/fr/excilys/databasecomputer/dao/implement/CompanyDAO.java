package fr.excilys.databasecomputer.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.excilys.databasecomputer.dao.CompanyDAOAbstract;
import fr.excilys.databasecomputer.dao.entity.Company;

public class CompanyDAO extends CompanyDAOAbstract{

	public CompanyDAO(Connection conn){
		super(conn);
	}
	
	public ArrayList<Company> findAll() {
		ArrayList<Company> companys = new ArrayList<>();
		try {
			PreparedStatement stm = this.connet.prepareStatement("Select * from company Order by id");
			ResultSet result = stm.executeQuery();
			
			while(result.next()) {
				Company company =new Company();
				company.setId(result.getInt("id"));
				company.setName(result.getString("name"));
				companys.add(company);
			}
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}
		
		return companys;
	}
}
