package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;

public interface RequirementService {
	

	void createRequirement(Requirement requirement);
	
	void updateRequirement(Requirement requirement);
	
	void deleteRequirement(Requirement requirement);
	
	Requirement getRequirementById(Long id);
	
	Requirement getRequirementByCode(String code);
	
	List<Requirement> getAllRequirements();
	
	List<Requirement> getRequirementsByCountryAndStandard(Country country, Standard standard);
	
	void saveOrUpdateRequirement(Requirement requirement);
	
	

}
