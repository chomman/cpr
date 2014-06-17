package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.FieldOfEducation;

public interface FieldOfEducationService {
	
	FieldOfEducation getById(Long id);
	
	List<FieldOfEducation> getAll();
	
	List<FieldOfEducation> getForActiveMedicalDevices();
	
	List<FieldOfEducation> getForNonActiveMedicalDevices();
	
}
