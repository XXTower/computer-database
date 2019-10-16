package fr.excilys.databasecomputer.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;


public class CompanyMapper {

private static CompanyMapper instance;
	
	private CompanyMapper() {}
	
	public static CompanyMapper getInstance() {
		if(instance==null) {
			instance = new CompanyMapper();
		}
		return instance;
	}
	
	public Company SQLToComputer(ResultSet result) {
					
		int id = 0;
		String name = null;

		try {
			id = result.getInt("id") != 0 ? result.getInt("id"): null;
			name = result.getString("name")!= null ? result.getString("name"):null; 		
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}
		return new CompanyBuilder().id(id).name(name).build();
	}
}
