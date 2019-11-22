package fr.excilys.databasecomputer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.databasecomputer.dao.implement.CompanyDAO;
import fr.excilys.databasecomputer.dtos.CompanyDTO;
import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.mapper.CompanyMapper;

@Service
public class CompanyService {

	private CompanyDAO companyDAO;
	private CompanyMapper companyMapper;

	@Autowired
	private CompanyService(CompanyDAO companyDAO, CompanyMapper companyMapper) {
		this.companyDAO = companyDAO;
		this.companyMapper = companyMapper;
	}

	public List<CompanyDTO> displayAllCompany() {
		return companyDAO.findAll().stream().map(company -> companyMapper.toCompanyDTO(company))
				.collect(Collectors.toList());
	}

	public List<Company> displayAllCompany(int limite, int offset) {
		return companyDAO.findAll(limite, offset);
	}

	public long nbCompany() {
		return companyDAO.nbCompany();
	}

	public boolean deleteCopany(String companyName) {
		return companyDAO.deleteCompany(companyName);
	}
}