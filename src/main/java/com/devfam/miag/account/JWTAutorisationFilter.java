package com.devfam.miag.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
public class JWTAutorisationFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// filetre du Key jwt par utilisateur en regardant header
		
	
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, "
				+ "Access-Control-Request-Method, Access-Control-Request-Headers,authorization,X-TenantID");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization,X-TenantID");
        response.addHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,PATCH");
        
        // Verifier dans le Header le id du Tenant en cours
        
		//dire a spring que si ya le header autorisation dans un requetes faut laisser lire
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		else if(request.getRequestURI().equals("/authenticate/login")) {
			filterChain.doFilter(request, response);
			return;
		}
		else {
			// S'il s'agit d'une autre type de requete on essaye d'interpreter la Key jwt
				String jwtToken = request.getHeader(ConstantDeSecurity.HEADER_STRING);
				System.out.println("Le TOKEN :"+jwtToken);
				// On regarde si la key represente pas le format de nos Constant de securites puis on bloque 
				if(jwtToken==null || !jwtToken.startsWith(ConstantDeSecurity.TOKEN_PREFIX)) {
					//Ici on essaye de setter le contexte DB
					try {
						
					}catch(Exception e){
						
					}
					filterChain.doFilter(request, response);
					return;
				}else {
				/*  On doit appliqer une erification sur le JWT*/
				JWTVerifier verifier =JWT.require(Algorithm.HMAC256(ConstantDeSecurity.SECRET)).build();
				String jwt =  jwtToken.substring(ConstantDeSecurity.TOKEN_PREFIX.length());
				DecodedJWT decodeJWT = verifier.verify(jwt);
				String username = decodeJWT.getSubject();
				
				System.out.println("TOKEN:"+jwt);
				String tenant= decodeJWT.getClaims().get("tenantID").toString();
				System.out.println("Le TENANT ID "+tenant);
				
				List<String> roles = decodeJWT.getClaims().get("roles").asList(String.class);
				System.out.println("Username ***"+username);
				System.out.println("Roles ***"+roles);
				Collection<GrantedAuthority> permissions = new ArrayList<>();
				roles.forEach(rol->{
					permissions.add(new SimpleGrantedAuthority(rol));
				});
				
				// Au cas ou il respecte notre format de KEY JWT 
				/*Claims claims=Jwts.parser()
						.setSigningKey(ConstantDeSecurity.SECRET)
						.parseClaimsJws(jwtToken.replace(ConstantDeSecurity .TOKEN_PREFIX, "")) // on enleve la partie Prefix
						.getBody();
				 // On recupere l'utilisateur
				String username =claims.getSubject();
				// On recupere les roles de l'utilisateur en cours
				//ArrayList<Map<String,String>> roles =(ArrayList<Map<String,String>>)claims.get("roles");
				//On decode le jwt
				
				Collection<GrantedAuthority> permissions = new ArrayList<>();
				roles.forEach(rol->{
					permissions.add(new SimpleGrantedAuthority(rol.get("authority")));
				});*/
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,permissions);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				filterChain.doFilter(request, response);
				}
			}
		
	}

}
