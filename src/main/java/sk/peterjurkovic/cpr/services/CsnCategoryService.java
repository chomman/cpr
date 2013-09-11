package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.CsnCategory;


public interface CsnCategoryService {
	
	void createCategory(CsnCategory category);
	
	void updateCategory(CsnCategory category);
	
	void deleteCategory(CsnCategory category);
	
	CsnCategory getById(Long id);
	
	CsnCategory getByCode(String code);
	
	List<CsnCategory> getAll();
	
	void saveOrUpdate(CsnCategory category);
	
	String getUniqeCsnCategoryCode(String name);
	
	boolean isCsnCategoryEmpty(CsnCategory category);
	
	CsnCategory findBySearchCode(String searchCode);
	
	List<CsnCategory> getSubRootCategories();
}
