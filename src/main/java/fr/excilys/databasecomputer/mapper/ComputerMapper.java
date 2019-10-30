package fr.excilys.databasecomputer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.entity.Computer.ComputerBuilder;
import fr.excilys.databasecomputer.exception.DateFormatExeption;

@Component
public class ComputerMapper {

	private ComputerMapper() { }

	public Computer sqlToComputer(ResultSet result) {
		int id = 0;
		String name = null;
		LocalDate introduced = null, discontinued = null;
		Company company = null;
		try {
			id = result.getInt("computer.id") != 0 ? result.getInt("computer.id") : null;
			name = result.getString("computer.name") != null ? result.getString("computer.name") : null;
			introduced = result.getTimestamp("computer.introduced") != null ? result.getDate("computer.introduced").toLocalDate() : null;
			discontinued = result.getTimestamp("computer.discontinued") != null ? result.getDate("computer.discontinued").toLocalDate() : null;
			company = new CompanyBuilder().id(result.getInt("company.id")).name(result.getString("company.name")).build();
		} catch (SQLException se) {
			for (Throwable e : se) {
				System.err.println("Problèmes rencontrés: " + e);
			}
		}
		return new ComputerBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued).company(company).build();
	}

	public Computer computerDtoToComputer(ComputerDTO computerDto) throws DateFormatExeption {
		ComputerBuilder computer = new ComputerBuilder();
		try {
			computer.id(computerDto.getId())
			.name(computerDto.getName())
			.introduced(computerDto.getIntroduced() != "" ? LocalDate.parse(computerDto.getIntroduced()) : null)
			.discontinued(computerDto.getDiscontinued() != "" ? LocalDate.parse(computerDto.getDiscontinued()) : null)
			.company(new CompanyBuilder().name(computerDto.getCompany()).build());
		} catch (DateTimeParseException e) {
			throw new DateFormatExeption("Format date incorrect");
		}

		return computer.build();
	}
}
