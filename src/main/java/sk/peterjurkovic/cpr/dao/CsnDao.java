package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;

public interface CsnDao extends BaseDao<Csn, Long>{
	
	
	boolean isCsnIdUniqe(Long id, String name);
	
	
	List<Csn> getCsnByTerminology(String terminologyTitle);
	
	PageDto getCsnPage(final int pageNumber, final Map<String, Object> criteria);
	
	
}
