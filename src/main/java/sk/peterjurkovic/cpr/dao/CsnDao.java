package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnTerminology;

public interface CsnDao extends BaseDao<Csn, Long>{
	
	boolean isCsnIdUniqe(Long id, String name);
	
	List<Csn> getCsnByTerminology(String terminologyTitle);
	
	PageDto getCsnPage(final int pageNumber, final Map<String, Object> criteria);
	
	List<CsnTerminology> getTerminologyByCsnAndLang(Csn csn, String languageCode);
	
	void deleteAllTerminology(Long id);
	
	List<Csn> autocompleteByClassificationSymbol(String term);
	
	List<Csn> autocompleteByCsnId(String term);
	
	Csn getByClassificationSymbol(String cs);
	
}
