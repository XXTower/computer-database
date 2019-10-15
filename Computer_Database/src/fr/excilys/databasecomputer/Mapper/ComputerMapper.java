package fr.excilys.databasecomputer.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.entity.Computer.ComputerBuilder;

public class ComputerMapper {
	private static ComputerMapper instance;
	
	private ComputerMapper() {}
	
	public static ComputerMapper getInstance() {
		if(instance==null) {
			instance = new ComputerMapper();
		}
		return instance;
	}
	
	public Computer SQLToComputer(ResultSet result) {
					
		int id = 0;
		String name = null;
		LocalDate introduced = null,discontinued = null;
		Company company = null;
		try {
			id = result.getInt("cmt.id") != 0 ? result.getInt("cmt.id"): null;
			name = result.getString("cmt.name")!= null ? result.getString("cmt.name"):null; 		
			introduced = result.getTimestamp("cmt.introduced") != null ? result.getDate("cmt.introduced").toLocalDate() :null;
			discontinued = result.getTimestamp("cmt.discontinued") != null ? result.getDate("cmt.discontinued").toLocalDate() :null;
			company = new CompanyBuilder().id(result.getInt("cmp.id")).name(result.getString("cmp.name")).build();
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}
		return new ComputerBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued).company(company).build();
	}
}