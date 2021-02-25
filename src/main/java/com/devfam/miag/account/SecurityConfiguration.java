package com.devfam.miag.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	@Bean
	     public AuthenticationManager authenticationManagerBean() throws Exception {
	         return super.authenticationManagerBean();
	 }

	// Methode qui permet de bloquer l'acces qu'en cas d'authentification
@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // Desactive le cle csrf token
		//http.formLogin();
		
		// Utiliser l'authentification par un Key JWT pas par session 
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Les privileges sur l'accees aux services
		http.authorizeRequests().antMatchers("/authenticate/login").permitAll();
		http.authorizeRequests().antMatchers("/utilisateur/all/**").permitAll();//hasAuthority("USER");
		http.authorizeRequests().antMatchers("/utilisateur/newLocataire/**").permitAll();
		http.authorizeRequests().antMatchers("/utilisateur/new/**").permitAll();
		http.authorizeRequests().antMatchers("/role/all/**").permitAll();
		http.authorizeRequests().antMatchers("/utilisateur/update/**").permitAll();
		http.authorizeRequests().antMatchers("/utilisateur/findUserserByUsername/**").hasAuthority("USER");
		http.authorizeRequests().anyRequest().authenticated();
		//http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
		http.addFilterBefore(new JWTAutorisationFilter(), UsernamePasswordAuthenticationFilter.class);
	};
	


	@Bean
	public BCryptPasswordEncoder crypterMDP() {
		return new BCryptPasswordEncoder();
	}
	
}
