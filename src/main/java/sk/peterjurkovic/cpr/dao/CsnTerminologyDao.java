package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.CsnTerminology;

public interface CsnTerminologyDao extends BaseDao<CsnTerminology, Long>{
	
	
	boolean isTitleUniqe(Long csnId, Long terminologyId, String title);
	
	List<CsnTerminology> searchInTerminology(String term);
	
	PageDto getCsnTerminologyPage(int currentPage, Map<String, Object> criteria);
	
}
