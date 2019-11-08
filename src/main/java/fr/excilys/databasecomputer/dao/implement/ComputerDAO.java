package fr.excilys.databasecomputer.dao.implement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import fr.excilys.databasecomputer.mapper.ComputerMapper;

@Repository
public class ComputerDAO {
	private static final String FIND_ALL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.id";
	private static final String FIND_ALL_LIMIT_OFFSET = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY "
			+ "(CASE :order WHEN 'ASC' THEN computer.name END) ASC,(CASE :order WHEN 'DESC' THEN computer.name END) DESC "
			+ "LIMIT :limite OFFSET :offset";
	private static final String FIND_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = :id ORDER BY computer.id ";
	private static final String UPDATE = "UPDATE computer SET name=:name, introduced=:intoduced, discontinued=:discontinued, company_id=(SELECT id FROM company WHERE name LIKE :company) WHERE id=:id";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = :id";
	private static final String INSERT_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (:name,:intoduced,:discontinued,(SELECT id FROM company WHERE name LIKE :company))";
	private static final String DELETE_COMPUTER_NAME_COMPANY = "DELETE computer FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE :company";
	private static final String SEARCH_COMPUTER_COMPANY_BY_NAME = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE :name OR computer.name LIKE :name ORDER BY "
			+ "(CASE :order WHEN 'ASC' THEN computer.name END) ASC,(CASE :order WHEN 'DESC' THEN computer.name END) DESC "
			+ "LIMIT :limite OFFSET :offset";

	private ComputerMapper computerMapper;
	private NamedParameterJdbcTemplate jdbcTemplate;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ComputerDAO(DataSource dataSource, ComputerMapper computerMapper) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.computerMapper = computerMapper;
	}

	public Computer find(int id) throws SQLExceptionComputerNotFound {
		Computer computer = null;
		SqlParameterSource namedParameterSource = new MapSqlParameterSource("id", id);
		try {
			computer = jdbcTemplate.queryForObject(FIND_BY_ID, namedParameterSource, computerMapper);
		} catch (EmptyResultDataAccessException e) {
			throw new SQLExceptionComputerNotFound("Aucun ordinateur trouver a pour cette id");
		}
		return computer;
	}

	public boolean update(Computer computer) {
		SqlParameterSource namedParameterSource = new MapSqlParameterSource("name", computer.getName())
				.addValue("intoduced", computer.getIntroduced())
				.addValue("discontinued", computer.getDiscontinued())
				.addValue("company", computer.getCompany().getName()).addValue("id", computer.getId());
		int result = jdbcTemplate.update(UPDATE, namedParameterSource);
		return result == 1;
	}

	public boolean delete(int id) {
		SqlParameterSource namedParameterSource = new MapSqlParameterSource("id", id);
		int result = jdbcTemplate.update(DELETE_COMPUTER, namedParameterSource);
		return result != 0;
	}

	public List<Computer> findAll() {
		return jdbcTemplate.query(FIND_ALL, computerMapper);
	}

	public boolean addComputer(Computer computer) {
		SqlParameterSource namedParameterSource = new MapSqlParameterSource("name", computer.getName())
				.addValue("intoduced", computer.getIntroduced())
				.addValue("discontinued", computer.getDiscontinued())
				.addValue("company", computer.getCompany().getName());
		int result = jdbcTemplate.update(INSERT_COMPUTER, namedParameterSource);
		return result == 1;
	}

	public long nbComputer() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		criteriaQuery.select(builder.countDistinct(root));
		TypedQuery<Long> query = em.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	public List<Computer> findAll(int limite, int offset, String order) {
		SqlParameterSource namedParameterSource = new MapSqlParameterSource("order", order)
				.addValue("limite", limite)
				.addValue("offset", offset);
		return jdbcTemplate.query(FIND_ALL_LIMIT_OFFSET, namedParameterSource, computerMapper);
	}

	public boolean deleteComputerByCompanyName(String companyName) {
		SqlParameterSource namedParameterSource = new MapSqlParameterSource("company", companyName);
		int result = jdbcTemplate.update(DELETE_COMPUTER_NAME_COMPANY, namedParameterSource);
		return result != 0;
	}

	public List<Computer> findComputerByName(String name, int limite, int offset, String order) {
		SqlParameterSource namedParameterSource = new MapSqlParameterSource("name", "%" + name + "%")
				.addValue("order", order)
				.addValue("limite", limite)
				.addValue("offset", offset);
		return jdbcTemplate.query(SEARCH_COMPUTER_COMPANY_BY_NAME, namedParameterSource, computerMapper);
	}

	public long nbComputerFindByName(String name) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		Join<Computer, Company> company = root.join("company", JoinType.LEFT);
		Predicate nameComputer = builder.like(root.get("name"), "%" + name + "%");
		Predicate nameCompany = builder.like(company.get("name"), "%" + name + "%");
		criteriaQuery.select(builder.countDistinct(root)).where(builder.or(nameComputer, nameCompany));
		TypedQuery<Long> query = em.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
}
