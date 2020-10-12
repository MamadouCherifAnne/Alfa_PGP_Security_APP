
package com.devfam.miag.account.services;


import java.util.List;

import com.devfam.miag.account.entities.Profession;

public interface ProfessionService {
	//Ajout profession
	Profession addProfession(Profession profession);
	
	//Update Profession
	Profession updateProfession(Profession profession);
	
	//Suppression profession
	boolean deleteProfession(Long idProfession);
	
	//Recherche par id
	Profession findProfessionById(Long idProfession);
	
	//Afficher toutes les professions
	public List<Profession> getAllProfession();

}
