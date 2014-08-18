package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.StandardCategory;

public interface StandardCategoryService extends IdentifiableByLongService<StandardCategory>{
	
	void create(StandardCategory standardCategory);
	
	void update(StandardCategory standardCategory);
	
	List<StandardCategory> getAll();
	
}
