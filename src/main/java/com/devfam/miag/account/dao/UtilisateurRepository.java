package com.devfam.miag.account.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devfam.miag.account.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{

	public Utilisateur findByNom(String nom);
	public Optional<Utilisateur> findByUsername(String username);
	public Optional<Utilisateur> findByIdUser(Long idUser);
}
