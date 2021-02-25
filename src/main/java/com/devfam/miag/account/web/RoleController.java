package com.devfam.miag.account.web;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devfam.miag.account.entities.Role;
import com.devfam.miag.account.services.RoleService;

@RestController
@RequestMapping("/role")

@CrossOrigin(origins = "*")


public class RoleController {
	
	@Autowired
	RoleService roleService;
	private final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	@PostMapping("/new")
	public Role addNewRole(@RequestBody Role role) {
		return roleService.addRole(role);
	}
	//la modification d'un role
	@PostMapping("/update/{id}")
	public String updateRole(@PathVariable Long id, @RequestBody Role role) {
		role.setIdRole(id);
		try {
				LOGGER.debug("La modification est bonne");
				roleService.updateRole(role);
				

				return "Modification effectué avec succes";
		}
		catch(Exception updateException) {
			LOGGER.debug("Queleques problemes rencontre au cours de la supprssion");
			return " La modification  a echoué";
		}
	}
	

	@PostMapping(value="/delete/{id}", produces="application/json")
	public boolean deleteRole(@PathVariable Long id) {
		boolean retour=false;
		LOGGER.debug("Suppresion d'un role ");
		retour = roleService.deleteRole(id);
		String message="";
		if(retour == true) {
			message="Suppression reussie";
			return true ;
		}else {
			message ="La suppression a echoue";
			return false ;
		}
	}
	
	@GetMapping("/all")
	public List<Role> getAllRoles(){
		return roleService.getAll();

	}
	

}
