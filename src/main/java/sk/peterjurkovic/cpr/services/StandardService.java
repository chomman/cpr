package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardCsn;
import sk.peterjurkovic.cpr.entities.StandardGroup;

public interface StandardService {
	
	void createStandard(Standard standard);
	
	void updateStandard(Standard standard);
	
	void deleteStandard(Standard standard);
	
	void mergeStandard(Standard standard);
	
	Standard getStandardById(Long id);
	
	Standard getStandardByCode(String code);
	
	List<Standard> getAllStandards();
	
	List<Standard> getStandardPage(int pageNumber, Map<String,Object> criteria);
	
	Long getCountOfStandards(Map<String,Object> criteria);
	
	boolean isStandardIdUnique(String standardId, Long id);
	
	void saveOrUpdate(Standard standard);
	
	void clearStandardTags(Standard standard);
	
	List<Standard> autocomplateSearch(String query, Boolean enabled);
	
	List<Standard> getLastEditedOrNewestStandards(int count, Boolean enabled);
	
	List<Standard> getStandardByStandardGroupForPublic(StandardGroup StandardGroup);
	
	List<Standard> getStandardsByTagName(String tagName);
	
	Standard getStandardByCsn(StandardCsn csn);
	
}
