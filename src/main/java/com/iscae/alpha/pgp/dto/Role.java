package com.iscae.alpha.pgp.dto;

public class Role {
	
	private Long  idRole;
	private String role;

	public Role() {
		super();
		
	}
	

	public Long getIdRole() {
		return idRole;
	}


	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public Role(String role) {
		super();
		this.role = role;
		
	}

}
