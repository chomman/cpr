package cz.nlfnorm.quasar.services;

import cz.nlfnorm.quasar.entities.AuditorExperience;

/**
 * QUASAR Component
 * 
 * @author Peter Jurkovic
 * @date Jun 18, 2014
 */
public interface AuditorExperienceService {
	
	AuditorExperience getById(Long id);
	
	void createOrUpdate(AuditorExperience auditorExperience);
}
