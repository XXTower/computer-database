package fr.excilys.databasecomputer.dao.implement;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.mapper.CompanyMapper;

@Repository
public class CompanyDAO {
	private static final String FIND_ALL = "SELECT id, name FROM company ORDER BY id";
	private static final String FIND_ALL_LIMITE_OFFSET = "SELECT id, name FROM company ORDER BY id LIMIT :limite OFFSET :offset";
	private static final String NB_COMPANY = "SELECT COUNT(id) AS nbCompany FROM company";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE name LIKE :company";
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	CompanyMapper companyMapper;

	@Autowired
	private CompanyDAO(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Company> findAll() {
		SqlParameterSource namedParameterSource = new MapSqlParameterSource();
		return jdbcTemplate.query(FIND_ALL, namedParameterSource, companyMapper);
	}

	public List<Company> findAll(int limite, int offset) {
		SqlParameterSource namedParameterSource = new MapSqlParameterSource("limite", limite).addValue("offset", offset);
		return jdbcTemplate.query(FIND_ALL_LIMITE_OFFSET, namedParameterSource, companyMapper);
	}

	public int nbCompany() {
		SqlParameterSource namedParameterSource = new MapSqlParameterSource();
		return jdbcTemplate.queryForObject(NB_COMPANY, namedParameterSource, Integer.class);
	}

	public boolean deleteCompany(String companyName) {
		SqlParameterSource namedParameterSource = new MapSqlParameterSource("company", companyName);
		int result = jdbcTemplate.update(DELETE_COMPANY, namedParameterSource);
		return result != 0;
	}
}
