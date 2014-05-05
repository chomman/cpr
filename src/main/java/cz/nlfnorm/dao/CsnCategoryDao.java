package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.entities.CsnCategory;

public interface CsnCategoryDao extends BaseDao<CsnCategory, Long>{

	Long getMaxId();
	
	Long getCountOfCsnInCategory(Long categoryId);
	
	CsnCategory findBySearchCode(String searchCode);
	
	List<CsnCategory> getSubRootCategories();
	
	List<CsnCategory> autocomplete(String term);
}
