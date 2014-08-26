package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.entities.Regulation;
import cz.nlfnorm.entities.StandardCategory;

public interface StandardCategoryDao extends BaseDao<StandardCategory, Long>{
	
	List<Regulation> getAllUnassignedRegulationFor(StandardCategory standardCategory);
	
	List<StandardCategory> getAllOrderByName();
	
}
