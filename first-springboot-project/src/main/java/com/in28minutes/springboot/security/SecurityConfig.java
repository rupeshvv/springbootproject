package com.in28minutes.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	//Authentication user --> roles
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 	// enable in memory based authentication with a user named
		  	// &quot;user&quot; and &quot;admin&quot;
		
		 auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance()).withUser("user1").password("secret1")
			.roles("USER").and().withUser("admin1").password("secret1")
			.roles("USER", "ADMIN");
	 }
	
		//Authorization role--access 
	 
	 protected void configure(HttpSecurity http) throws Exception {
			
			http.httpBasic().and().authorizeRequests()
			         .antMatchers("/surveys/**").hasRole("USER")
			         .antMatchers("/users/**").hasRole("USER")
			         .antMatchers("/**").hasRole("ADMIN")
					.and().csrf().disable()
				   .headers().frameOptions().disable()
				;
		}
}
