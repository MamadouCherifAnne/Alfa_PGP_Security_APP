package com.devfam.miag.account.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
public class Utilisateur implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idUser")
	private Long idUser;
	@Column(unique = true)
	private String username;
	private String nom;
	private String prenom;
	private String email;
	private String password;
	private String adresse;
	private boolean actif;
	private String telephone;
	private String company;
	//ceci est lidentifiant de lentreprise locataire
	
	
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "Utilisateur_roles")
	Collection<Role> roles = new ArrayList<>();
	

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Utilisateur_Profession",
	joinColumns = @JoinColumn(name="id_user"), inverseJoinColumns = @JoinColumn(name="numProfession"))
	@JsonSetter

	private List<Profession> professions;
	
	
	
	
	public Utilisateur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Utilisateur(String username,String nom, String prenom, String email, String password, String adresse, boolean actif,
			String telephone,String company, Collection<Role> roles, List<Profession> professions) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.adresse = adresse;
		this.actif = actif;
		this.telephone = telephone;
		//this.roles = roles;
		this.professions = professions;
		this.company =company;
	
	}

	// GETTERS AND SETTERS ......................................................................................................
	
	
	
	public Long getIdUser() {
		return idUser;
	}


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Collection<Role> getRole() {
		return roles;
	}

	public void setRole(Collection<Role> collection) {
		this.roles = collection;
	}

	public List<Profession> getProfessions() {
		return professions;
	}

	public void setProfessions(List<Profession> professions) {
		this.professions = professions;
	}

}
