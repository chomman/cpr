package cz.nlfnorm.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.CsnCategoryDao;
import cz.nlfnorm.dto.CsnCategoryJsonDto;
import cz.nlfnorm.entities.CsnCategory;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.CsnCategoryService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.utils.UserUtils;

@Service("csnCategoryService")
@Transactional(propagation = Propagation.REQUIRED)
public class CsnCategoryServiceImpl implements CsnCategoryService{
	
	@Autowired
	private CsnCategoryDao csnCategoryDao;
	
	@Autowired
	private UserService userService;

	@Override
	public void createCategory(CsnCategory category) {
		csnCategoryDao.save(category);
	}

	@Override
	public void updateCategory(CsnCategory category) {
		csnCategoryDao.update(category);
	}

	@Override
	public void deleteCategory(CsnCategory category) {
		csnCategoryDao.remove(category);
	}

	@Override
	@Transactional(readOnly = true)
	public CsnCategory getById(Long id) {
		return csnCategoryDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CsnCategory> getAll() {
		return csnCategoryDao.getAll();
	}

	@Override
	public void saveOrUpdate(CsnCategory category) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		if(category.getId() == null){
			category.setCreatedBy(user);
			category.setCreated(new LocalDateTime());
			
			csnCategoryDao.save(category);
		}else{
			category.setChangedBy(user);
			category.setChanged(new LocalDateTime());
			csnCategoryDao.update(category);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getUniqeCsnCategoryCode(String name){
		Validate.notNull(name);
		name = CodeUtils.toSeoUrl(name);
		if(csnCategoryDao.getByCode(name) == null){
			return name;
		}else{
			return name + "-" + csnCategoryDao.getMaxId()+1;
		}
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean isCsnCategoryEmpty(CsnCategory category){
		Validate.notNull(category);
		return (csnCategoryDao.getCountOfCsnInCategory(category.getId()) == 0l);
	}

	
	@Override
	@Transactional(readOnly = true)
	public CsnCategory getByCode(String code) {
		return csnCategoryDao.getByCode(code);
	}

	
	@Override
	@Transactional(readOnly = true)
	public CsnCategory findBySearchCode(String searchCode) {
		return csnCategoryDao.findBySearchCode(searchCode);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CsnCategory> getSubRootCategories() {
		List<CsnCategory> list = csnCategoryDao.getSubRootCategories();
		if(list == null){
			list = new ArrayList<CsnCategory>();
		}
		return list;
	}
	

	@Override
	@Transactional(readOnly = true)
	public List<CsnCategoryJsonDto> getSubRootCategoriesInJsonFormat() {
		List<CsnCategory> categoryList = csnCategoryDao.getSubRootCategories();
		List<CsnCategoryJsonDto> jsonlist = new ArrayList<CsnCategoryJsonDto>();
		for(CsnCategory category : categoryList){
			jsonlist.add(category.toJsonDto());
		}
		return jsonlist;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CsnCategory> autocomplete(String term) {
		if(StringUtils.isNotBlank(term)){
			return csnCategoryDao.autocomplete(term);
		}
		return new ArrayList<CsnCategory>();
	}

	@Override
	@Transactional(readOnly = true)
	public CsnCategory findByClassificationSymbol(String classificationSymbol) {
		CsnCategory category = null;
		if(StringUtils.isNotBlank(classificationSymbol) && classificationSymbol.length() > 3){
			String searchCode =  classificationSymbol.substring(0, 4);
			category = csnCategoryDao.findBySearchCode(searchCode);
		}
		return category;
	}
	
	
}
