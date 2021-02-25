package com.devfam.miag.account.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

		
	private static final  Logger log =LoggerFactory.getLogger(UtilisateurServiceImplementation.class);
	@Override
	public Utilisateur addUser(Utilisateur user) {
		// Verification d'un utilisateur 

		Optional<Utilisateur> use=userRepository.findByUsername(user.getUsername());
		if(use.isPresent()) { 
			return null;
		}
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
			log.info("username oldUser "+oldUser.getUsername() +"  == "+user.getUsername());
			if(!user.getUsername().equals(oldUser.getUsername())) {
				Optional<Utilisateur> use=userRepository.findByUsername(user.getUsername());
				if(use.isPresent()) {
					return null;
					//throw new RuntimeException("Cet Utilisateur existe deja, essayer avec un autre username");
				}
				
			}
		
		// Set new Values
		if(oldUser!=null) {
			oldUser.setAdresse(user.getAdresse());
			oldUser.setEmail(user.getEmail());
			oldUser.setTelephone(user.getTelephone());
			oldUser.setPrenom(user.getPrenom());
			oldUser.setNom(user.getNom());
			oldUser.setUsername(user.getUsername());
			
			// actualiser la profession
			// Gerer le mot de passe
			if(user.getPassword() != null) {
				oldUser.setPassword((bCryptPasswordEncoder.encode(user.getPassword())));
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
		// Verifier si lutilisqteur existe dabord

		Utilisateur user = new Utilisateur();
		Optional<Utilisateur> verifUser = userRepository.findByIdUser(id);
		if(verifUser.isPresent()) {
			 userRepository.deleteById(id);
			 return true;
		}
			return false;
	}



	@Override
	public void addStatus(String username, String role) {
		//Accorder un nouveau role a un utilisateur
		Utilisateur user = this.getUserByUsername(username);
		
		Role roles= roleService.findByRole(role);
		if(user !=null && role!=null) {
			Collection<Role> listeRoles = new ArrayList<>();
			//log.info("les roles presntes dans user "+user.getRole().size());
				if(role.equals("USER")) {
					listeRoles.add(roles);
					user.setRole(listeRoles);
				}else {
				if(user.getRole() != null && !user.getRole().contains(roles)) {
					user.getRole().add(roles);
				}
				}
			
			//user.setRole(listeRoles);
		
			userRepository.save(user);
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
	@Override
	public void accordPrivilleges(String username, List<String> roles) {
		Utilisateur user = getUserByUsername(username);
		user.setRole(null);
		
		if(roles !=null && !roles.isEmpty()) {
			System.out.println(user+"////////////////////////////");
			user =userRepository.save(user);
			this.addStatus(username, "USER");
			// utiliser l'api
			for(String role:roles) {
				if(!role.equals("USER") )
					
						System.out.println("LE ROLE DE TOUR est "+role);
						this.addStatus(username, role);
					}
		}
	}



	@Override
	public Utilisateur addUserProprietaire(Utilisateur user) {
		// Verification d'un utilisateur 

				Optional<Utilisateur> use=userRepository.findByUsername(user.getUsername());
				if(use.isPresent()) { 
					return null;
				}
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
					Role role  = roleService.findByRole("SUPERADMIN");
					/*Collection<Role> roles =new ArrayList<>();
					roles.add(role);
					user.setRole(roles);*/
					user.getRole().add(role);
					
					Utilisateur newUser=userRepository.save(user);
					
					
					
					
					return newUser ;
	}
	



}
   