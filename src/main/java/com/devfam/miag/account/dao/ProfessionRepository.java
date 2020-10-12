package com.devfam.miag.account.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devfam.miag.account.entities.Profession;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {

	public Optional<Profession> findByNumProfession(Long numProfession);
	

}
