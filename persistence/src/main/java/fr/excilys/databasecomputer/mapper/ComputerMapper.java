package fr.excilys.databasecomputer.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.entity.Computer.ComputerBuilder;
import fr.excilys.databasecomputer.exception.DateFormatExeption;

@Component
public class ComputerMapper {

	private CompanyMapper companyMapper;

	public ComputerMapper(CompanyMapper companyMapper) {
		this.companyMapper = companyMapper;
	}

	public Computer toComputer(ComputerDTO computerDto) throws DateFormatExeption {
		ComputerBuilder computer = new ComputerBuilder();
		try {
			computer.id(computerDto.getId()).name(computerDto.getName())
					.introduced(computerDto.getIntroduced() != null && computerDto.getIntroduced() != "" ? LocalDate.parse(computerDto.getIntroduced()) : null)
					.discontinued(
							computerDto.getDiscontinued() != null && computerDto.getIntroduced() != "" ? LocalDate.parse(computerDto.getDiscontinued()) : null)
					.company(computerDto.getCompanyDTO() == null || computerDto.getCompanyDTO().getId() == 0 ? null
							: new CompanyBuilder().id(computerDto.getCompanyDTO().getId()).build());
		} catch (DateTimeParseException e) {
			throw new DateFormatExeption("Format date incorrect");
		}

		return computer.build();
	}

	public ComputerDTO toComputerDto(Computer computer) {
		ComputerDTO computerDto = new ComputerDTO();
		computerDto.setId(computer.getId());
		computerDto.setName(computer.getName());
		computerDto.setIntroduced(computer.getIntroduced() == null ? "" : computer.getIntroduced().toString());
		computerDto.setDiscontinued(computer.getDiscontinued() == null ? "" : computer.getDiscontinued().toString());
		computerDto.setCompanyDTO(companyMapper.toCompanyDTO(computer.getCompany()));
		return computerDto;
	}

}
