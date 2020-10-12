package com.devfam.miag.account.services;

import java.util.List;

import org.apache.logging.log4j.message.Message;

import com.devfam.miag.account.entities.Utilisateur;


public interface UtilisateurService {
	// Ajout d'un utilisateur
	public Utilisateur addUser(Utilisateur user);
	
	// Modification du profil de l'utilisateur
	public Utilisateur updateUser(Utilisateur user);
	
	//Suppression  de l'utilisateur
	public boolean deleteUser(Long id);
	
	// Afficher les Utilisateurs
	public List<Utilisateur> getAllUsers();
	
	// Rechercher Un utilisateur par son Nom
	public Utilisateur getUserByName(String nom);

	
	// Recherche par identifiant
	public Utilisateur getUserById(Long id);
	
	// Ajputer un role a un utilisateur
	public void addStatus(String username,String role);

	public Utilisateur getUserByUsername(String username);
	
	// Service Login
	public Utilisateur Login(Utilisateur user);
	
}
