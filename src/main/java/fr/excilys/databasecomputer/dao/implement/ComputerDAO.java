package fr.excilys.databasecomputer.dao.implement;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import fr.excilys.databasecomputer.mapper.ComputerMapper;
import fr.excilys.databasecomputer.mapper.DateMapper;

@Repository
public class ComputerDAO {
	private static final String FIND_ALL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.id";
	private static final String FIND_ALL_LIMIT_OFFSET = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY "
			+ "(CASE ? WHEN 'ASC' THEN computer.name END) ASC,(CASE ? WHEN 'DESC' THEN computer.name END) DESC "
			+ "LIMIT ? OFFSET ?";
	private static final String FIND_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? ORDER BY computer.id ";
	private static final String UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=(SELECT id FROM company WHERE name LIKE ?) WHERE id=?";
	private static final String NB_COMPUTER = "SELECT COUNT(id) AS nbComputer FROM computer";
	private static final String NB_COMPUTER_FIND_BY_NAME = "SELECT COUNT(computer.id) AS nbComputer FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE ? OR computer.name LIKE ?";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
	private static final String INSERT_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,(SELECT id FROM company WHERE name LIKE ?))";
	private static final String DELETE_COMPUTER_NAME_COMPANY = "DELETE computer FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE ?";
	private static final String SEARCH_COMPUTER_COMPANY_BY_NAME = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE ? OR computer.name LIKE ? ORDER BY "
			+ "(CASE ? WHEN 'ASC' THEN computer.name END) ASC,(CASE ? WHEN 'DESC' THEN computer.name END) DESC "
			+ "LIMIT ? OFFSET ?";
	@Autowired
	private ComputerMapper computerMapper;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ComputerDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Computer find(int id) throws SQLExceptionComputerNotFound {
		return jdbcTemplate.queryForObject(FIND_BY_ID, computerMapper, id);
	}

	public boolean update(Computer computer) {
		int result = jdbcTemplate.update(UPDATE, computer.getName(), DateMapper.changeToDateSQL(computer.getIntroduced()),
				DateMapper.changeToDateSQL(computer.getDiscontinued()), computer.getCompany().getName(), computer.getId());
		return result == 1;
	}

	public boolean delete(int id) {
		int result = jdbcTemplate.update(DELETE_COMPUTER, id);
		return result != 0;
	}

	public List<Computer> findAll() {
		return jdbcTemplate.query(FIND_ALL, computerMapper);
	}

	public boolean addComputer(Computer computer) {
		int result = jdbcTemplate.update(INSERT_COMPUTER, computer.getName(), DateMapper.changeToDateSQL(computer.getIntroduced()),
				DateMapper.changeToDateSQL(computer.getDiscontinued()), computer.getCompany().getName());
		return result == 1;
	}

	public int nbComputer() {
		return jdbcTemplate.queryForObject(NB_COMPUTER, Integer.class);
	}

	public List<Computer> findAll(int limite, int offset, String order) {
		return jdbcTemplate.query(FIND_ALL_LIMIT_OFFSET, computerMapper, order, order, limite, offset);
	}

	public boolean deleteComputerByCompanyName(String companyName) {
		int result = jdbcTemplate.update(DELETE_COMPUTER_NAME_COMPANY, companyName);
		return result != 0;
	}

	public List<Computer> findComputerByName(String name, int limite, int offset, String order) {
		return jdbcTemplate.query(SEARCH_COMPUTER_COMPANY_BY_NAME, computerMapper, "%" + name + "%", "%" + name + "%", order, order, limite, offset);
	}

	public int nbComputerFindByName(String name) {
		return jdbcTemplate.queryForObject(NB_COMPUTER_FIND_BY_NAME, Integer.class, "%" + name + "%", "%" + name + "%");
	}
}
