package sk.peterjurkovic.cpr.dao;

import sk.peterjurkovic.cpr.entities.CsnTerminology;

public interface CsnTerminologyDao extends BaseDao<CsnTerminology, Long>{
	
	boolean isTitleUniqe(Long csnId, Long terminologyId, String title);
	
}
