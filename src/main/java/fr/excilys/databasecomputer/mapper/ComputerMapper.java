package fr.excilys.databasecomputer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.entity.Computer.ComputerBuilder;
import fr.excilys.databasecomputer.exception.DateFormatExeption;

@Component
public class ComputerMapper implements RowMapper<Computer> {

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

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		int id = rs.getInt("computer.id") != 0 ? rs.getInt("computer.id") : null;
		String name = rs.getString("computer.name") != null ? rs.getString("computer.name") : null;
		LocalDate introduced = rs.getTimestamp("computer.introduced") != null ? rs.getDate("computer.introduced").toLocalDate() : null;
		LocalDate discontinued = rs.getTimestamp("computer.discontinued") != null ? rs.getDate("computer.discontinued").toLocalDate() : null;
		Company company = new CompanyBuilder().id(rs.getInt("company.id")).name(rs.getString("company.name")).build();
		return new ComputerBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued).company(company).build();
	}
}
