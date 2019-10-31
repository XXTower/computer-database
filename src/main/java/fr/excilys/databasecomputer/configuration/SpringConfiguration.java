package fr.excilys.databasecomputer.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@ComponentScan(basePackages = "fr.excilys.databasecomputer")
@PropertySource(value="classpath:database.properties")
public class SpringConfiguration extends AbstractContextLoaderInitializer {
	
	@Autowired
	private Environment env;

	 @Override
	 protected WebApplicationContext createRootApplicationContext() {
		 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		 rootContext.register(SpringConfiguration.class);
		 return rootContext;
	 }

	 @Bean
	 public DataSource getConnection() {
		 DriverManagerDataSource dataSource = new DriverManagerDataSource();
		 dataSource.setDriverClassName(env.getProperty("dataSource.driverClassName"));
		 dataSource.setUrl(env.getProperty("dataSource.jdbcUrl"));
		 dataSource.setUsername(env.getProperty("dataSource.user"));
		 dataSource.setPassword(env.getProperty("dataSource.password"));
		 return dataSource;
	 }

//	@Override
//    public void onStartup(ServletContext container) throws ServletException {
//      // Create the 'root' Spring application context
//      AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//      rootContext.register(SpringConfiguration.class);
//implements WebApplicationInitializer
//      // Manage the life cycle of the root application context
//      container.addListener(new ContextLoaderListener(rootContext));
//      ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(rootContext));
//      servlet.setLoadOnStartup(1);
//      servlet.addMapping("/");
//    }
//
//    @Bean
//    public ViewResolver internalResourceViewResolver() {
//      InternalResourceViewResolver bean = new InternalResourceViewResolver();
//      bean.setViewClass(JstlView.class);
//      bean.setPrefix("/WEB-INF/views/");
//      bean.setSuffix(".jsp");
//
//      return bean;
//    }

}
