package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardCsn;
import sk.peterjurkovic.cpr.entities.StandardGroup;

/**
 * Rozhranie datovej vrstvy entity sk.peterjurkovic.cpr.entities.Standard
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface StandardDao extends BaseDao<Standard, Long> {
	
	
	List<Standard> getStandardPage( Map<String, Object> criteria, int pageNumber, int limit);
		
	Long getCountOfSdandards(Map<String, Object> criteria);
	
	void clearStandardTags(Standard standard);
	
	boolean isStandardIdUnique(String standardId, Long id);
	
	List<Standard> autocomplateSearch(String query, Boolean enabled);
	
	List<Standard> getLastEditedOrNewestStandards(int count, final Boolean enabled);
	
	List<Standard> getStandardByStandardGroupForPublic(StandardGroup StandardGroup);
	
	List<Standard> getStandardsByTagName(String tagName);
	
	List<Standard> getStandardsByCsn(StandardCsn csn);
	
	List<Standard> getStandardsByStandardGroupCode(String standardGroupCode);
	
	List<Standard> getStandardsByNotifiedBody(NotifiedBody notifiedBody);
	
	List<Standard> getStandardsByReplaceStandard(Standard standard);
	
	void unassignNotifiedBody(Long standardNotifiedBodyId);
	
	List<Standard> getChangedStanards(LocalDate dateFrom, LocalDate dateTo, Boolean enabledOnly);
}
