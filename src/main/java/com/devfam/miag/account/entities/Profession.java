package com.devfam.miag.account.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
public class Profession implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numProfession;
	private String titreProfession;
	
	@JsonBackReference(value="user-profession")
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Utilisateur_Profession",
	joinColumns = @JoinColumn(name="numProfession"), inverseJoinColumns = @JoinColumn(name="id_user"))
	@JsonSetter

	private List<Utilisateur> utilisateurs;

	public Profession() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Profession(String titreProfession, List<Utilisateur> utilisateurs) {
		super();
		this.titreProfession = titreProfession;
		this.utilisateurs = utilisateurs;
	}

	public Long getNumProfession() {

		return numProfession;
	}

	public void setNumProfession(Long numProfession) {
		this.numProfession = numProfession;

	}

	public String getTitreProfession() {
		return titreProfession;
	}

	public void setTitreProfession(String titreProfession) {
		this.titreProfession = titreProfession;
	}

	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}
	
	
}
