package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Csn;

public interface CsnDao extends BaseDao<Csn, Long>{
	
	
	boolean isCsnIdUniqe(Long id, String name);
	
	
	List<Csn> getCsnByTerminology(String terminologyTitle);
	
	
}
