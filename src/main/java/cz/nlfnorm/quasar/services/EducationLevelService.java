package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.EducationLevel;
import cz.nlfnorm.services.IdentifiableByLongService;

/**
 * QUASAT
 * 
 * Service interface for education level
 * 
 * @author Peter Jurkovic
 * @date Jun 18, 2014
 */
public interface EducationLevelService extends IdentifiableByLongService<EducationLevel>{
	
	List<EducationLevel> getAll();
}
