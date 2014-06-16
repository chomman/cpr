package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.Experience;

public interface ExperienceService {
	
	Experience getById(Long id);
	
	List<Experience> getAll();
	
	List<Experience> getAllExcept(Auditor auditor);
	
}
