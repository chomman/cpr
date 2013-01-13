package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.Standard;

public interface StandardService {
	
	void createStandard(Standard standard);
	
	void updateStandard(Standard standard);
	
	void deleteStandard(Standard standard);
	
	Standard getStandardById(Long id);
	
	Standard getStandardByCode(String code);
	
	List<Standard> getAllStandards();
	
	List<Standard> getStandardPage(int pageNumber, Map<String,Object> criteria);
	
	Long getCountOfStandards(Map<String,Object> criteria);
	
	boolean isStandardIdUnique(String standardId, Long id);
	
	void saveOrUpdate(Standard standard);
	
	void clearStandardTags(Standard standard);
}
