package cz.nlfnorm.services;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.entities.StandardCsn;
import cz.nlfnorm.entities.StandardGroup;

public interface StandardService {
	
	void createStandard(Standard standard);
	
	void updateStandard(Standard standard);
	
	void deleteStandard(Standard standard);
	
	void mergeStandard(Standard standard);
	
	void mergeAndSetChanged(Standard standard);
	
	Standard getStandardById(Long id);
	
	Standard getStandardByCode(String code);
	
	List<Standard> getAllStandards();
	
	List<Standard> getStandardPage(int pageNumber, Map<String,Object> criteria);
	
	List<Standard> getStandardPage(int pageNumber, Map<String,Object> criteria, int limit);
	
	Long getCountOfStandards(Map<String,Object> criteria);
	
	boolean isStandardIdUnique(String standardId, Long id);
	
	void saveOrUpdate(Standard standard);
	
	void clearStandardTags(Standard standard);
	
	List<Standard> autocomplateSearch(String query, Boolean enabled);
	
	List<Standard> getLastEditedOrNewestStandards(int count, Boolean enabled);
	
	List<Standard> getStandardByStandardGroupForPublic(StandardGroup StandardGroup);
	
	List<Standard> getStandardsByTagName(String tagName);
	
	List<Standard> getStandardsByCsn(StandardCsn csn);
	
	List<Standard> getStandardsByStandardGroupCode(String standardGroupCode);
	
	boolean updateReferencedStandard(Standard standard);
	
	List<Standard> getStandardsByNotifiedBody(NotifiedBody notifiedBody);
	
	void addStandardCsn(Standard standard, StandardCsn standardCsn);
	
	void removeStandardCsn(Standard standard, StandardCsn standardCsn);
	
	void removeReferences(Standard standard);
	
	boolean hasAssociatedNotifiedBody(NotifiedBody notifiedBody, Standard standard);
	
	void unassigenNotifiedBody(Long standardNotifiedBodyId);
	
	List<Standard> getChangedStanards(LocalDate dateFrom, LocalDate dateTo, Boolean enabledOnly);
}
