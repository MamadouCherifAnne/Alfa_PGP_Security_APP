package com.devfam.miag.account.locataire;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.devfam.miag.account.ConstantDeSecurity;
import com.devfam.miag.account.TenantContexte;

import javassist.NotFoundException;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
	private static Logger Log =LoggerFactory.getLogger(RequestInterceptor.class);
	
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        System.out.println("In preHandle we are Intercepting the Request");
        System.out.println("____________________________________________");
         String tenantID = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
         if( requestURI.equals("/authenticate/login")) {
        	 
        	 tenantID="alfapgpdefaultbd";
         }
         if(!requestURI.equals("/authenticate/login")) {
        	// S'il s'agit d'une autre type de requete on essaye d'interpreter la Key jwt
				String jwtToken = request.getHeader(ConstantDeSecurity.HEADER_STRING);
				System.out.println("Le TOKEN :"+jwtToken);
				// On regarde si la key represente pas le format de nos Constant de securites puis on bloque 
				if(jwtToken==null || !jwtToken.startsWith(ConstantDeSecurity.TOKEN_PREFIX)) {
					//Ici on essaye de setter le contexte DB
					tenantID=null;
				}else {
				/*  On doit appliqer une erification sur le JWT*/
				JWTVerifier verifier =JWT.require(Algorithm.HMAC256(ConstantDeSecurity.SECRET)).build();
				String jwt =  jwtToken.substring(ConstantDeSecurity.TOKEN_PREFIX.length());
				DecodedJWT decodeJWT = verifier.verify(jwt);
				String username = decodeJWT.getSubject();
				
				System.out.println("TOKEN:"+jwt);
				String tenant= decodeJWT.getClaims().get("tenantID").asString();
				System.out.println("Le TENANT ID "+tenant);
        	 tenantID=tenant;
         }
         }
        System.out.println("RequestURI::" + requestURI +" || Search for X-TenantID  :: " + tenantID);
        System.out.println("____________________________________________");
        if (tenantID == null) {
            response.getWriter().write("X-TenantID not present in the Request Header");
            response.setStatus(400);
            return false;
        }
        
        TenantContexte.setCurrentTenant(tenantID);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantContexte.clear();
    }
    
  

}