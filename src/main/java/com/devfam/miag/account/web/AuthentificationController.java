package com.devfam.miag.account.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import com.devfam.miag.account.TenantContexte;
import com.devfam.miag.account.entities.DataSourceConfig;
import com.devfam.miag.account.entities.Utilisateur;
import com.devfam.miag.account.locataire.UserLocataireInformation;
import com.devfam.miag.account.services.AuthResponse;
import com.devfam.miag.account.services.JWTUTIL;
import com.devfam.miag.account.services.MasterTenantService;
import com.devfam.miag.account.services.RoleService;
import com.devfam.miag.account.services.UtilisateurService;
import com.sun.istack.NotNull;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "*")

public class AuthentificationController {
	private static Logger log =LoggerFactory.getLogger(AuthentificationController.class);


	@Autowired
	MasterTenantService masterTenantService;
	@Autowired
	UtilisateurService userService;
	@Autowired
	RoleService roleService;
	
	private AbstractRoutingDataSource multiTenantDataSource;
	@Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUTIL jwtTokenUtil;
	    
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationController.class);
    private Map<String, String> mapValue = new HashMap<>();
    private Map<String, String> userDbMap = new HashMap<>();
	
	
	
	@PostMapping(value="/login")
    public ResponseEntity<?> userAuthentification(@RequestBody @NotNull Utilisateur user) throws AuthenticationException, Exception {
        LOGGER.info("userLogin() method call...");
        
        if(null == user.getUsername() || user.getUsername().isEmpty()){
            return new ResponseEntity<>("Les donnes sont vides ", HttpStatus.BAD_REQUEST);
        }
        Utilisateur user1 = userService.getUserByUsername(user.getUsername());
        //set database parameter
        DataSourceConfig masterTenant = masterTenantService.getByTenantId(user1.getCompany());
        if(null == masterTenant || !masterTenant.isInitialize()){
            throw new RuntimeException("Please contact service provider.");
        }
        
        System.out.println("MASTER TENANT ####"+masterTenant.getName());
        //TenantContexte.setCurrentTenant(masterTenant.getName());
        
       // loadCurrentDatabaseInstance(masterTenant.getName(), user1.getUsername());
            
        System.out.println("LE CONTEXTE"+TenantContexte.getCurrentTenant()+"###"+masterTenant.getName());
        
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user1.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails.getUsername(),user1.getCompany(),(Collection<SimpleGrantedAuthority>) userDetails.getAuthorities());
        
       // setMetaDataAfterLogin();
        return ResponseEntity.ok(new AuthResponse(userDetails.getUsername(),token));

}
	  private void loadCurrentDatabaseInstance(String databaseName, String userName) {
	        TenantContexte.setCurrentTenant((databaseName));
	        mapValue.put(userName, databaseName);
	    }

	  @Bean(name = "userTenantInfo")
	    @ApplicationScope
	    public UserLocataireInformation setMetaDataAfterLogin() {
		  UserLocataireInformation tenantInformation = new UserLocataireInformation();
	        if (mapValue.size() > 0) {
	            for (String key : mapValue.keySet()) {
	                if (null == userDbMap.get(key)) {
	                    //Here Assign putAll due to all time one come.
	                    userDbMap.putAll(mapValue);
	                } else {
	                    userDbMap.put(key, mapValue.get(key));
	                }
	            }
	            mapValue = new HashMap<>();
	        }
	        tenantInformation.setMap(userDbMap);
	        return tenantInformation;
	    }
  }


