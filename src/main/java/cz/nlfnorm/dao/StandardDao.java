package cz.nlfnorm.dao;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.entities.StandardCsn;
import cz.nlfnorm.entities.StandardGroup;

/**
 * Rozhranie datovej vrstvy entity sk.peterjurkovic.cpr.entities.Standard
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface StandardDao extends BaseDao<Standard, Long> {
	
	
	List<Standard> getStandardPage( Map<String, Object> criteria, int pageNumber, int limit);
		
	Long getCountOfSdandards(Map<String, Object> criteria);
	
	boolean isStandardIdUnique(String standardId, Long id);
	
	List<Standard> autocomplateSearch(String query, Boolean enabled);
	
	List<Standard> getLastEditedOrNewestStandards(int count, final Boolean enabled);
	
	List<Standard> getStandardByStandardGroupForPublic(StandardGroup StandardGroup);
		
	List<Standard> getStandardsByCsn(StandardCsn csn);
	
	List<Standard> getStandardsByStandardGroupCode(String standardGroupCode);
	
	List<Standard> getStandardsByNotifiedBody(NotifiedBody notifiedBody);
	
	List<Standard> getStandardsByReplaceStandard(Standard standard);
	
	void unassignNotifiedBody(Long standardNotifiedBodyId);
	
	List<Standard> getChangedStanards(LocalDate dateFrom, LocalDate dateTo, Boolean enabledOnly);
	
	List<Standard> getStandardsForSitemap();
}
