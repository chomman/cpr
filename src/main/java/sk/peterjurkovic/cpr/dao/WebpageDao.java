package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Webpage;

public interface WebpageDao extends BaseDao<Webpage, Long>{
	
	
	List<Webpage> getPublicSection(Long sectionId);
	
}
