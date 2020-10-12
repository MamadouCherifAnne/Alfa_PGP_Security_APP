package com.devfam.miag.account.web;


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

import com.devfam.miag.account.TenantContexte;
import com.devfam.miag.account.dao.DataSourceConfigRepository;
import com.devfam.miag.account.dao.ProfessionRepository;
import com.devfam.miag.account.dao.RoleRepository;
import com.devfam.miag.account.entities.Utilisateur;
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
	ProfessionRepository profRepo;;	@Autowired
	

	
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
	
	/*
	 * // Afficher les taches a realiser par un seul utilisateur
	 * 
	 * @GetMapping(value="/tacheToRealise/{idUser}") public List<Tache>
	 * getTacheToRealiseForUser(@PathVariable Long idUser){ List<Tache> taches =
	 * userService.TacheToRealise(idUser); return taches; }
	 * 
	 * 
	 * // Afficher les message envoyes recu pour un utilisateur
	 * 
	 * @GetMapping(value="/boiteEnvoi/{idUser}") public List<Message>
	 * getSenddeMessages(@PathVariable Long idUser){ return
	 * userService.getAllSendedMessageFromUser(idUser); }
	 * 
	 * // Afficher les message recu pour un utilisateur
	 * 
	 * @GetMapping(value="/boiteReception/{idUser}") public List<Message>
	 * getRecivedMessages(@PathVariable Long idUser){ return
	 * userService.getAllRecivedMessageFromUser(idUser); }
	 * 
	 */
	
}
