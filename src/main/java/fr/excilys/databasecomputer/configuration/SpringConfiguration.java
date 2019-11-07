package fr.excilys.databasecomputer.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"fr.excilys.databasecomputer.dao.implement", "fr.excilys.databasecomputer.servlet",
		"fr.excilys.databasecomputer.mapper", "fr.excilys.databasecomputer.service", "fr.excilys.databasecomputer.validator",
		"fr.excilys.databasecomputer.pageable"})
@PropertySource(value = "classpath:database.properties")
public class SpringConfiguration implements WebApplicationInitializer {

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

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(SpringConfiguration.class, SpringMvcConfiguration.class);
		webContext.setServletContext(servletContext);
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dynamicServlet", new DispatcherServlet(webContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}

}
