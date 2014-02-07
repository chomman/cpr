package sk.peterjurkovic.cpr.services;

import sk.peterjurkovic.cpr.entities.StandardCsnChange;

public interface StandardCsnChangeService {
	
	StandardCsnChange getById(Long id);
	
	void delete(StandardCsnChange csn);
	
}
