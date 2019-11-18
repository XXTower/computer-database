package fr.excilys.databasecomputer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilter(digestAuthenticationFilter()) // register digest entry point
				.exceptionHandling().authenticationEntryPoint(digestEntryPoint())
				.and().authorizeRequests().anyRequest()
				.authenticated();

	}

	DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {

		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
		digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
		digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
		return digestAuthenticationFilter;
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() {
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		inMemoryUserDetailsManager.createUser(User.withUsername("admin").password("admin1").roles("ADMIN").build());
		return inMemoryUserDetailsManager;

	}

	@Bean
	DigestAuthenticationEntryPoint digestEntryPoint() {
		DigestAuthenticationEntryPoint bauth = new DigestAuthenticationEntryPoint();
		bauth.setRealmName("Digest WF Realm");
		bauth.setKey("MySecureKey");
		return bauth;
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.toString().equals(encodedPassword);
			}
		};
	}
}
