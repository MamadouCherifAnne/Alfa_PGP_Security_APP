package com.devfam.miag.account.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devfam.miag.account.dao.ProfessionRepository;
import com.devfam.miag.account.dao.UtilisateurRepository;
import com.devfam.miag.account.entities.Profession;
import com.devfam.miag.account.entities.Role;
import com.devfam.miag.account.entities.Utilisateur;



@Service
public class UtilisateurServiceImplementation implements UtilisateurService{
	@Autowired
	UtilisateurRepository userRepository;
	@Autowired
	ProfessionRepository profRepo;
	@Autowired
	RoleService roleService;
	@Autowired
	PasswordEncoder bCryptPasswordEncoder;

		
	
	@Override
	public Utilisateur addUser(Utilisateur user) {
		// Verification d'un utilisateur 

		Optional<Utilisateur> use=userRepository.findByUsername(user.getUsername());
		if(use.isPresent()) throw new RuntimeException("Cet Utilisateur existe deja, essayer avec un autre username");
		
		
			/*
			 *  verification que la liste des profession nest pas null puis actualiser la liste des utilisateur
			 *  de chacune de ces professions
			 */
			if(user.getProfessions() !=null) {
						List<Profession> listprof =new ArrayList<>();
						
						for(Profession p:user.getProfessions()) {
						listprof.add(profRepo.findByNumProfession(p.getNumProfession()).get());
						}
					user.setProfessions(listprof);	
			}
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			Role role  = roleService.findByRole("USER");
			/*Collection<Role> roles =new ArrayList<>();
			roles.add(role);
			user.setRole(roles);*/
			user.getRole().add(role);
			
			Utilisateur newUser=userRepository.save(user);
			
			
			
			
			return newUser ;
	
		
		}
		
	

	@Override
	public Utilisateur updateUser(Utilisateur user) {
		
		//Verifier si l'utilisateur existe puis changer les valuers a modifier

			Utilisateur oldUser= userRepository.getOne(user.getIdUser());
			
			// Verifier que au cours de la modification qu'il ne met pas un username existant
			if(user.getUsername() != oldUser.getUsername()) {
				Optional<Utilisateur> use=userRepository.findByUsername(user.getUsername());
				if(use.isPresent()) throw new RuntimeException("Cet Utilisateur existe deja, essayer avec un autre username");
				
			}
		
		// Set new Values
		if(oldUser!=null) {
			oldUser.setAdresse(user.getAdresse());
			oldUser.setEmail(user.getEmail());
			oldUser.setTelephone(user.getTelephone());
			oldUser.setPrenom(user.getPrenom());
			oldUser.setRole(user.getRole());
			
			// actualiser la profession
			if(user.getProfessions() !=null) {
				List<Profession> listprof =new ArrayList<>();
				
				for(Profession p:user.getProfessions()) {
				listprof.add(profRepo.findByNumProfession(p.getNumProfession()).get());
				}
			oldUser.setProfessions(listprof);	
			}
			
			
			////////////////////////////////////////////////////////////////////////////
			
			
			if(userRepository.findByNom(user.getNom())==null) {
			oldUser.setNom(user.getNom());

			}
		}
		

		return userRepository.save(oldUser);

	}
	

/*	@Override
	public boolean deleteUser(Long id) {
		
		// Verifier si lutilisqteur existe dabord

		Utilisateur user = new Utilisateur();
		Optional<Utilisateur> verifUser = userRepository.findByIdUser(id);
		if(verifUser != null) {
			 user = userRepository.getOne(id);
			
			// Supprimer l'utilisateur de la liste des utilisateur dans les profession avec lesquelles il etait liee
			if(user.getProfessions()!= null) {
				user.setProfessions(null);
			}
			// Suppression de L'utilisateur dans la liste de role avec aui il est en relation
			if(user.getRole() !=null) {
				user.getRole().getUsers().remove(user);
			}
			// Verification si l'utilisateur n'est pas affecte dans une tache
			List<AffectationUtilisateur> affectations =affectService.getAffectationsForUser(id);
			if(affectations == null) {
				userRepository.deleteById(id);
				return true;
				}
		}
			return false;
				
	}*/

	@Override
	public List<Utilisateur> getAllUsers() {
		
		return userRepository.findAll();
	}

	
	//Recherche d'un utilisateur par son nom
	@Override
	public Utilisateur getUserByName(String username) {
		Utilisateur user = null;
		if(userRepository.findByUsername(username).isPresent()) {
			user =userRepository.findByUsername(username).get();
		}

		return user;
	}



	@Override
	public Utilisateur getUserById(Long id) {
		Utilisateur user = new Utilisateur();
		Optional<Utilisateur> verifUser = userRepository.findByIdUser(id);
		if(verifUser.isPresent()) {
			 
			 return verifUser.get();
		}else {
			return null;
		}

	}



	@Override
	public boolean deleteUser(Long id) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void addStatus(String username, String role) {
		//Accorder un nouveau role a un utilisateur
		Utilisateur user = this.getUserByName(username);
		Role roles= roleService.findByRole(role);
		if(user !=null && role!=null) {
			user.getRole().add(roles);
			
			System.out.println();
		}
	}
	
	@Override
	public Utilisateur getUserByUsername(String username) {
		Utilisateur user = null;
		if(userRepository.findByUsername(username).isPresent()) {
			user =userRepository.findByUsername(username).get();
		}

		return user;
	}



	@Override
	public Utilisateur Login(Utilisateur user) {
		
		return user;
		
	}



}
   