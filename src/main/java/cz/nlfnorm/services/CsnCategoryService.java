package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.dto.CsnCategoryJsonDto;
import cz.nlfnorm.entities.CsnCategory;


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
	
	CsnCategory findByClassificationSymbol(String classificationSymbol);
	
	List<CsnCategory> getSubRootCategories();
	
	List<CsnCategoryJsonDto> getSubRootCategoriesInJsonFormat();
	
	List<CsnCategory> autocomplete(String term);
}
