package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Standard;

public interface StandardService {
	
	void createStandard(Standard standard);
	
	void updateStandard(Standard standard);
	
	void deleteStandard(Standard standard);
	
	Standard getStandardById(Long id);
	
	Standard getStandardByCode(String code);
	
	List<Standard> getAllStandards();
	
}
