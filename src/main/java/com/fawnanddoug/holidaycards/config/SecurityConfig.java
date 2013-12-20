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
				.usersByUsernameQuery("select name,password,true from user where name=?")
				.authoritiesByUsernameQuery("select u.name, r.authority from user u, role r where u.id = r.userid and u.name =?");
			
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
