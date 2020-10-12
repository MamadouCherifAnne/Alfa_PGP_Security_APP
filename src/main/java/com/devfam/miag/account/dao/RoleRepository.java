package com.devfam.miag.account.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devfam.miag.account.entities.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
	
	//Recherche par role
	public  Optional<Role> findByRole(String role);
	

}
