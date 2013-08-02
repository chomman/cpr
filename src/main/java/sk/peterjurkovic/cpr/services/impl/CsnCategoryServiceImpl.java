package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.CsnCategoryDao;
import sk.peterjurkovic.cpr.entities.CsnCategory;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.CsnCategoryService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;

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
	public CsnCategory getById(Long id) {
		return csnCategoryDao.getByID(id);
	}

	@Override
	public List<CsnCategory> getAll() {
		return csnCategoryDao.getAll();
	}

	@Override
	public void saveOrUpdate(CsnCategory category) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		if(category.getId() == null){
			category.setCreatedBy(user);
			category.setCreated(new DateTime());
			csnCategoryDao.save(category);
		}else{
			category.setChangedBy(user);
			category.setChanged(new DateTime());
			csnCategoryDao.update(category);
		}
	}
	
	
	
}
