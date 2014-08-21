package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.Regulation;
import cz.nlfnorm.entities.StandardCategory;

public interface StandardCategoryService extends IdentifiableByLongService<StandardCategory>{
	
	void create(StandardCategory standardCategory);
	
	void update(StandardCategory standardCategory);
	
	List<StandardCategory> getAll();
	
	void createOrUpdate(StandardCategory standardCategory);
	
	List<Regulation> getAllUnassignedRegulationFor(StandardCategory standardCategory);
	
}
