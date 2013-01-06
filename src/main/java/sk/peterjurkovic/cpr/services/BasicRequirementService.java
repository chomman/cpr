package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.BasicRequirement;


public interface  BasicRequirementService {
	
	void createBasicRequirement(BasicRequirement basicRequirement);
	
	void updateBasicRequirement(BasicRequirement basicRequirement);
	
	void deleteBasicRequirement(BasicRequirement basicRequirement);
	
	BasicRequirement getBasicRequirementById(Long id);
	
	List<BasicRequirement> getAllBasicRequirements();
	
	BasicRequirement getBasicRequirementByCode(String code);
	
	void saveOrUpdateBasicRequirement(BasicRequirement basicRequirement);
}
