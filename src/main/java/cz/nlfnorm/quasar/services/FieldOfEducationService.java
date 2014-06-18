package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.FieldOfEducation;
import cz.nlfnorm.services.IdentifiableByLongService;

public interface FieldOfEducationService extends IdentifiableByLongService<FieldOfEducation>{
	
	FieldOfEducation getById(Long id);
	
	List<FieldOfEducation> getAll();
	
	List<FieldOfEducation> getForActiveMedicalDevices();
	
	List<FieldOfEducation> getForNonActiveMedicalDevices();
	
	void createOrUpdate(FieldOfEducation fieldOfEducation);
	
	void remove(FieldOfEducation fieldOfEducation);
	
}
