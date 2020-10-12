package com.devfam.miag.account.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devfam.miag.account.entities.Profession;
import com.devfam.miag.account.services.ProfessionService;

@RestController
@RequestMapping("/profession")
@CrossOrigin(origins = "http://localhost:4200")
public class ProfessionController {
	@Autowired
	ProfessionService profService;
	
	//.................Ajout d'une Profession .....................................................................
	//................................................................................................
	
	@PostMapping(value="/add", consumes={"application/json"})
	public Profession addProfession(@RequestBody Profession profession){
		return profService.addProfession(profession);
		
	}
	
	//.................Modification d'une Profession .....................................................................
	//................................................................................................
	@PostMapping(value="/update/{idProf}", consumes= {"application/json"})
	public String updateProfession(@RequestBody Profession prof, @PathVariable Long idProf) {
		prof.setNumProfession((idProf));
		Profession verifProf =profService.updateProfession(prof);
		if(verifProf!=null) {
		return "Succesfuly";}
		else {
			return "Echec de modification";
		}
	}
	//.................Suppression d'une Profession .....................................................................
	//................................................................................................
	@PostMapping(value="/delete/{id}")
	public String deleteProfession(@PathVariable Long id){
		 boolean retour =false;
		
		 retour =profService.deleteProfession(id);
		 if(retour ==true) {
		 return "Suucess";}
		 else {
			 return "la suppression ne peut pas etre effectue";
		 }
		
	}
	
	//.................Afficher toutes les professions .....................................................................
	//................................................................................................
	@GetMapping(value="/all")
	public List<Profession> getAllProfessions(){
		return profService.getAllProfession();
	}
	

}
