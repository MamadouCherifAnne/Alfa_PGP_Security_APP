package com.devfam.miag.account.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.devfam.miag.account.ConstantDeSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Md. Amran Hossain
 */
@Component
public class JWTUTIL implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(ConstantDeSecurity.SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(String userName, String tenantOrClientId,Collection<SimpleGrantedAuthority> roles) {
        return doGenerateToken(userName,tenantOrClientId,roles);
    }

    private String doGenerateToken(String subject, String tenantOrClientId, Collection<SimpleGrantedAuthority> roles) {
    	List<String> permissions = new ArrayList<>();
    	
		roles.forEach(rol->{
			permissions.add((rol.getAuthority()));
		});
        Claims claims = Jwts.claims().setSubject(subject).setAudience(tenantOrClientId);
        claims.put("roles", Arrays.asList(new SimpleGrantedAuthority("USER")));
        String jwt = JWT.create()
				.withSubject(subject)
				.withArrayClaim("roles", permissions.toArray(new String[permissions.size()]))
				.withClaim("tenantID", tenantOrClientId)
				.withExpiresAt(new Date(System.currentTimeMillis()+ConstantDeSecurity.EXPIRATION_TIME))
				.sign(Algorithm.HMAC256(ConstantDeSecurity.SECRET));

        return jwt;  /*Jwts.builder()
                .setClaims(claims)
                .setIssuer("system")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ConstantDeSecurity.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, ConstantDeSecurity.SECRET)
                .compact();*/
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}