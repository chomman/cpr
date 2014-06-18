package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.Experience;
import cz.nlfnorm.services.IdentifiableByLongService;

public interface ExperienceService extends IdentifiableByLongService<Experience>{
	
	Experience getById(Long id);
	
	List<Experience> getAll();
	
	List<Experience> getAllExcept(Auditor auditor);
	
}
