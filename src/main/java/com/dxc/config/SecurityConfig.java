package com.dxc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService cutomUserDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] methodSecured = {"/users/*", "/swagger-ui.html"};
		http.csrf().disable().authorizeRequests().antMatchers("/","/login/authenticate").permitAll()
		.antMatchers(methodSecured).authenticated()
		.and().formLogin().loginPage("/login").defaultSuccessUrl("/swagger-ui.html").failureUrl("/login?error=true").permitAll()
		.and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout").logoutSuccessUrl("/login");
		
//		SecurityConfigurer securityConfigurerAdapter = new AuthTokenConfig(cutomUserDetailsService);
		
		super.configure(http);
	}
}
