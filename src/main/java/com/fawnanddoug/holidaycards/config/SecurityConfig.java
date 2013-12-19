package com.fawnanddoug.holidaycards.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 8)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment environment;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// In mysql case get the users from the DB
		if (environment.acceptsProfiles("mysql")) {
			auth.jdbcAuthentication()
				.authoritiesByUsernameQuery("select username,password, enabled from users where username=?")
				.usersByUsernameQuery("select u.username, ur.authority from users u, user_roles ur where u.user_id = ur.user_id and u.username =?");
			
		// else use in memory auth
		} else {
			auth.inMemoryAuthentication()
				.withUser("user").password("user").roles("USER")
				.and()
				.withUser("admin").password("admin").roles("USER", "ADMIN");
		}
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/fonts/**").permitAll()
				.antMatchers("/login/**").permitAll()
				.anyRequest().hasRole("USER")
			.and().formLogin()
				.loginPage("/login")
				.failureUrl("/login?error=UNKNOWN_CREDENTIALS")
				.permitAll()
			.and().logout()
					.logoutSuccessUrl("/login?logout=true");
			
	}
		
}
