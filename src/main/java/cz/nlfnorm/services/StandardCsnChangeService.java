package cz.nlfnorm.services;

import cz.nlfnorm.entities.StandardCsnChange;

public interface StandardCsnChangeService {
	
	StandardCsnChange getById(Long id);
	
	void delete(StandardCsnChange csn);
	
}
