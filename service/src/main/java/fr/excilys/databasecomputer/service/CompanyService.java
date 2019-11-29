package fr.excilys.databasecomputer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.databasecomputer.dao.implement.CompanyDAO;
import fr.excilys.databasecomputer.dao.implement.ComputerDAO;
import fr.excilys.databasecomputer.dtos.CompanyDTO;
import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;
import fr.excilys.databasecomputer.exception.FailSaveComputer;
import fr.excilys.databasecomputer.mapper.CompanyMapper;

@Service
public class CompanyService {

	private CompanyDAO companyDAO;
	private CompanyMapper companyMapper;
	private ComputerDAO computerDAO;

	CompanyService(CompanyDAO companyDAO, CompanyMapper companyMapper, ComputerDAO computerDAO) {
		this.companyDAO = companyDAO;
		this.companyMapper = companyMapper;
		this.computerDAO = computerDAO;
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

	public boolean deleteCompany(String companyName) {
		return companyDAO.deleteCompany(companyName);
	}

	public void addCompany(String name) throws FailSaveComputer {
		companyDAO.addCompany(new CompanyBuilder().name(name).build());
	}

	public void update(CompanyDTO companyDTO) throws FailSaveComputer {
		companyDAO.update(companyMapper.toCompany(companyDTO));
	}
	
	@Transactional
	public void deleteCompany(CompanyDTO companyDTO) {
		computerDAO.deleteComputerByCompanyId(companyMapper.toCompany(companyDTO));
		companyDAO.deleteCompanyById(companyDTO.getId());
	}
}
