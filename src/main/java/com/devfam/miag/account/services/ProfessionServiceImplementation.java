package com.devfam.miag.account.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devfam.miag.account.dao.ProfessionRepository;
import com.devfam.miag.account.entities.Profession;

@Service
public class ProfessionServiceImplementation implements ProfessionService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProfessionServiceImplementation.class);
	@Autowired
	ProfessionRepository profRepo;
	
	@PersistenceContext
    public EntityManager entityManager;

	@Override
	public Profession addProfession(Profession profession) {
		
		return profRepo.save(profession);
		
	}

	@Override
	public Profession updateProfession(Profession profession) {
		// TODO Auto-generated method stub
		Profession p=profRepo.getOne(profession.getNumProfession());
		p.setTitreProfession(profession.getTitreProfession());
		
		return profRepo.save(p);
	}

	@Override
	public boolean deleteProfession(Long idProfession) {
		boolean message =false;
		// Recuperer la profession correspondante
		Optional<Profession> verifProfession=profRepo.findByNumProfession(idProfession);
		Profession prof =new Profession();
		if(verifProfession.isPresent()) {
			
			
			prof=verifProfession.get();
				if(prof.getUtilisateurs().isEmpty()) {
					System.out.println("#######"+verifProfession.get());
					profRepo.deleteById(idProfession);
					message=true;
			}
				LOGGER.info("la suppression ne peut se faire car la profession est lie avec"
						+ " des utilsateurs donc on evite des incoherences");
			//profRepo.deleteById(idProfession);
			
		}
		return message;
		
	}

	@Override
	public Profession findProfessionById(Long idProfession) {
		// TODO Auto-generated method stub
		Optional<Profession> verifProf = profRepo.findByNumProfession(idProfession);
		if(verifProf.isPresent()) {
			Profession prof = new Profession();
			prof = profRepo.getOne(idProfession);
			return prof;
		}else {
			return null;
		}
	}

	@Override
	public List<Profession> getAllProfession() {
		// TODO Auto-generated method stub
		return profRepo.findAll();
	}

}
