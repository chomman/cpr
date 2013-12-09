package sk.peterjurkovic.cpr.services;

import sk.peterjurkovic.cpr.entities.StandardGroupMandate;

public interface StandardGroupMandateService {
	
	void create(StandardGroupMandate standardGroupMandate);
	
	void delete(StandardGroupMandate standardGroupMandate);
	
	void update(StandardGroupMandate standardGroupMandate);
	
	StandardGroupMandate getById(Long id);
	

	
}
