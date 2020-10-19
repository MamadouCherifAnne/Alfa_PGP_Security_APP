package com.devfam.miag.account.web;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devfam.miag.account.dao.DataSourceConfigRepository;
import com.devfam.miag.account.dao.ProfessionRepository;
import com.devfam.miag.account.dao.RoleRepository;
import com.devfam.miag.account.entities.Utilisateur;
import com.devfam.miag.account.services.AuthResponse;
import com.devfam.miag.account.services.MasterTenantService;
import com.devfam.miag.account.services.UtilisateurService;




@RestController
@RequestMapping("/utilisateur")

@CrossOrigin(origins = "*")

public class UtilisateurController {
	private static final  Logger log =LoggerFactory.getLogger(UtilisateurController.class);

	
	@Autowired
	LocalContainerEntityManagerFactoryBean  entityManagerFactory;
	@Autowired
	MasterTenantService masterTenantService;

	@Autowired

	UtilisateurService userService;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserDetailsService userdetails;
	@Autowired
	DataSourceConfigRepository master;
	
	@Autowired
	ProfessionRepository profRepo;
	

	
	@GetMapping("/all")
	 public ResponseEntity<?> getALlUser() throws Exception {
		//setTenant("devfamspringsecurity");
		

	  return ResponseEntity.ok(userService.getAllUsers());  /* master.findAll();*/ 
	}
	
	@PostMapping(value="/delete/{id}", consumes="application/json", produces="application/json")
	public boolean deleteUser(@PathVariable Long id) {
		boolean deleteResult=false;
		
		 deleteResult=userService.deleteUser(id);
		if(deleteResult==true) {
		return true;
	}else {
		return false;

		}
	}
	
	@GetMapping("/findUsername/{username}")
	public Utilisateur getUserByName(@PathVariable String username){
		
		return userService.getUserByUsername(username);
		
	}
	
	@GetMapping("/tryVerifUserCompany/{username}")
	public String verifCompanyByUser(@PathVariable String username){
		String name =username;
		String comp ="";
		Utilisateur u = userService.getUserByUsername(name);
		 if(u!=null) {
			 comp =u.getCompany();
		 }
		
		return comp ;
		
	}
	
	
	

	@GetMapping("/findUser/{id}")
	public Utilisateur getUserById(@PathVariable Long id){
		
		return userService.getUserById(id);
		
	}
	
	@PostMapping(value="/new", consumes={"application/json"})
	public Utilisateur addUser(@RequestBody Utilisateur user) {
	
		return userService.addUser(user);

	}
	

	
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable Long id,@RequestBody Utilisateur user) {
		try {

		user.setIdUser(id);
		userService.updateUser(user);
		return "Succes";
		}catch(Exception e) {
			return e.getMessage()+"La modification n'a pa pu être effectués";
			
		}
	}
	
	
	@PostMapping(value="/addPrivilleges/{username}",consumes= {"application/json"})
	public  ResponseEntity<?> accorderPrivilleges(@PathVariable String username,@RequestBody List<String> roles) {
		log.info("La liste des roles eest vide ou non"+roles.size());
		userService.accordPrivilleges(username, roles);
		return  ResponseEntity.ok("Les privillèges ont été modifié");
	}
}
