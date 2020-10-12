package com.devfam.miag.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.devfam.miag.account.entities.Utilisateur;
import com.devfam.miag.account.services.UtilisateurService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {
	private AuthenticationManager authenticationManager;
	private String companyUser="ja";
	@Autowired
	UtilisateurService userService;


	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
		super();
		this.authenticationManager = authenticationManager;
		this.userService=ctx.getBean(UtilisateurService.class);
	}
	
	// On ecrit la methode de filtre de l'authentification
	
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		//  Cette  s'execute lorsqu'il ya eu une demande de connexion 
		
		try {
			Utilisateur user = new ObjectMapper().readValue(request.getInputStream(), Utilisateur.class);
			Utilisateur u=userService.getUserByUsername(user.getUsername());
			if(u!=null) {
				companyUser=u.getCompany();
			
			}
			
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
					user.getPassword()));
			
		}
		catch(Exception e){
			System.out.println("Le nnn"+e.getMessage());
			System.out.println("Le nnn"+e.getCause());
			System.out.println("Le nnn"+e.getLocalizedMessage());
			//e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	
	}
	


	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// S
		User springUser =(User)authResult.getPrincipal();
		// companyUser =userService.verifCompanyByUser(springUser.getUsername());
		
		List<String> roles = new ArrayList<>();
		springUser.getAuthorities().forEach(rol->{
			roles.add(rol.getAuthority());
		});
		
		String jwt = JWT.create()
				.withIssuer(request.getRequestURI())
				.withSubject(springUser.getUsername())
				.withArrayClaim("roles", roles.toArray(new String[roles.size()]))
				.withClaim("tenantID", companyUser)
				.withExpiresAt(new Date(System.currentTimeMillis()+ConstantDeSecurity.EXPIRATION_TIME))
				.sign(Algorithm.HMAC256(ConstantDeSecurity.SECRET));
				
				
		response.addHeader(ConstantDeSecurity.HEADER_STRING, jwt);
		
		/*
		 * Avant a la video 3 de youssfi
		 * //String jwtToken =Jwts.builder()
		 * 			.setSubject(springUser.getUsername())
				.setExpiration(new Date(System.currentTimeMillis()+ConstantDeSecurity.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512,ConstantDeSecurity.SECRET)
				.claim("roles", springUser.getAuthorities())
				.compact();
				
		response.addHeader(ConstantDeSecurity.HEADER_STRING, ConstantDeSecurity.TOKEN_PREFIX+jwtToken);
		*/
		 
		
	}
	
	// La methode une fois que la connexion est autoriser
	
	
	



}
