package fr.excilys.databasecomputer.mapper;

import org.springframework.stereotype.Component;

import fr.excilys.databasecomputer.dtos.CompanyDTO;
import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;

@Component
public class CompanyMapper {

	public CompanyDTO toCompanyDTO(Company company) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());
		return companyDTO;
	}

	public Company toCompany(CompanyDTO companyDTO) {
		return new CompanyBuilder().id(companyDTO.getId()).name(companyDTO.getName()).build();
	}
}
