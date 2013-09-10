package sk.peterjurkovic.cpr.dao;

import sk.peterjurkovic.cpr.entities.CsnCategory;

public interface CsnCategoryDao extends BaseDao<CsnCategory, Long>{

	Long getMaxId();
	
	Long getCountOfCsnInCategory(Long categoryId);
	
	CsnCategory findBySearchCode(String searchCode);
}
