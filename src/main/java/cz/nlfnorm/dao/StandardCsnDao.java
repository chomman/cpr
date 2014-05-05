package cz.nlfnorm.dao;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.StandardCsn;


/**
 * Rozhranie implementujuce datovu vrstvu entity sk.peterjurkovic.cpr.entities.StandardCsn
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface StandardCsnDao extends BaseDao<StandardCsn, Long>{
	
	StandardCsn getByCatalogNo(String catalogNumber);
	
	List<StandardCsn> autocomplete(String term);
	
	PageDto getPage(int pageNumber, Map<String, Object> criteria);
	
	boolean isStandardCsnUnique(StandardCsn csn);
	
	void deleteStandardCsn(StandardCsn csn);
	
	List<StandardCsn> getChangedStandardCsn(LocalDate dateFrom, LocalDate dateTo, boolean enabledOnly);
}
