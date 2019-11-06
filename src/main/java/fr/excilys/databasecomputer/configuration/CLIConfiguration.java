package fr.excilys.databasecomputer.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@ComponentScan(basePackages = {"fr.excilys.databasecomputer.dao.implement","fr.excilys.databasecomputer.Main",
		"fr.excilys.databasecomputer.mapper","fr.excilys.databasecomputer.service","fr.excilys.databasecomputer.validator",
		"fr.excilys.databasecomputer.pageable"})
@PropertySource(value = "classpath:database.properties")
public class CLIConfiguration {

	@Autowired
	private Environment env;

	@Bean
	 public DataSource getConnection() {
		 DriverManagerDataSource dataSource = new DriverManagerDataSource();
		 dataSource.setDriverClassName(env.getProperty("dataSource.driverClassName"));
		 dataSource.setUrl(env.getProperty("dataSource.jdbcUrl"));
		 dataSource.setUsername(env.getProperty("dataSource.user"));
		 dataSource.setPassword(env.getProperty("dataSource.password"));
		 return dataSource;
	 }
}
