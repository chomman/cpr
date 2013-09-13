package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.WebpageCategoryDao;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.WebpageCategory;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.services.WebpageCategoryService;
import sk.peterjurkovic.cpr.utils.UserUtils;


@Service("webpageCategoryService")
@Transactional(propagation = Propagation.REQUIRED)
public class WebpageCategoryServiceImpl implements WebpageCategoryService {

	@Autowired
	private UserService userService;
	@Autowired
	private WebpageCategoryDao categoryDao;
	
	@Override
	public void createWebpageCategory(WebpageCategory webpageCategory) {
		categoryDao.save(webpageCategory);
	}

	@Override
	public void updateWebpageCategory(WebpageCategory webpageCategory) {
		categoryDao.update(webpageCategory);
	}

	@Override
	public void deleteWebpageCategory(WebpageCategory webpageCategory) {
		categoryDao.remove(webpageCategory);
	}

	@Override
	@Transactional(readOnly = true)
	public WebpageCategory getWebpageCategoryById(Long id) {
		return categoryDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public WebpageCategory getWebpageCategoryByCode(String code) {
		return categoryDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WebpageCategory> getAll() {
		return categoryDao.getAll();
	}

	@Override
	public void saveOrUpdate(WebpageCategory webpageCategory) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(webpageCategory.getId() == null){
			webpageCategory.setCreatedBy(user);
			webpageCategory.setCreated(new LocalDateTime());
			categoryDao.save(webpageCategory);
		}else{
			webpageCategory.setChangedBy(user);
			webpageCategory.setChanged(new LocalDateTime());
			categoryDao.update(webpageCategory);
		}
	}

}
