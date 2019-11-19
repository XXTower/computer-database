package fr.excilys.databasecomputer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;

@Component
public class CompanyMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		int id = rs.getInt("id") != 0 ? rs.getInt("id") : null;
		String name = rs.getString("name") != null ? rs.getString("name") : null;
		return new CompanyBuilder().id(id).name(name).build();
	}
}
