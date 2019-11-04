package fr.excilys.databasecomputer.dao.implement;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.mapper.CompanyMapper;

@Repository
public class CompanyDAO {
	private static final String FIND_ALL = "SELECT id, name FROM company ORDER BY id";
	private static final String FIND_ALL_LIMITE_OFFSET = "SELECT id, name FROM company ORDER BY id LIMIT ? OFFSET ?";
	private static final String NB_COMPANY = "SELECT COUNT(id) AS nbCompany FROM company";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE name LIKE ?";
	private JdbcTemplate jdbcTemplate;

	@Autowired
	CompanyMapper companyMapper;

	@Autowired
	private CompanyDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Company> findAll() {
		return jdbcTemplate.query(FIND_ALL, companyMapper);
	}

	public List<Company> findAll(int limite, int offset) {
		return jdbcTemplate.query(FIND_ALL_LIMITE_OFFSET, companyMapper, limite, offset);
	}

	public int nbCompany() {
		return jdbcTemplate.queryForObject(NB_COMPANY, Integer.class);
	}

	public boolean deleteCompany(String companyName) {
		int result = jdbcTemplate.update(DELETE_COMPANY, companyName);
		return result != 0;
	}
}
