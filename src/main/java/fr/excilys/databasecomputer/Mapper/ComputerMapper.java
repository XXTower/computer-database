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
			id = result.getInt("computer.id") != 0 ? result.getInt("computer.id"): null;
			name = result.getString("computer.name")!= null ? result.getString("computer.name"):null; 		
			introduced = result.getTimestamp("computer.introduced") != null ? result.getDate("computer.introduced").toLocalDate() :null;
			discontinued = result.getTimestamp("computer.discontinued") != null ? result.getDate("computer.discontinued").toLocalDate() :null;
			company = new CompanyBuilder().id(result.getInt("company.id")).name(result.getString("company.name")).build();
		} catch (SQLException se) {
			for(Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}
		return new ComputerBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued).company(company).build();
	}
}