package fr.excilys.databasecomputer.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@ComponentScan(basePackages = "fr.excilys.databasecomputer")
public class SpringConfiguration extends AbstractContextLoaderInitializer {

	 @Override
	 protected WebApplicationContext createRootApplicationContext() {
	 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	 rootContext.register(SpringConfiguration.class);
	 return rootContext;
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
