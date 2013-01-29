package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.WebpageContentDao;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.services.WebpageContentService;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("webpageContentService")
@Transactional(propagation = Propagation.REQUIRED)
public class WebpageContentServiceImpl implements WebpageContentService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private WebpageContentDao webpageContentDao;
	
	@Override
	public void createWebpageContent(WebpageContent webpageContent) {
		webpageContentDao.save(webpageContent);
	}

	@Override
	public void updateWebpageContent(WebpageContent webpageContent) {
		webpageContentDao.update(webpageContent);
	}

	@Override
	public void deleteWebpageContent(WebpageContent webpageContent) {
		webpageContentDao.remove(webpageContent);
	}

	@Override
	@Transactional(readOnly = true)
	public WebpageContent getWebpageContentById(Long id) {
		return webpageContentDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public WebpageContent getWebpageContentByCode(String code) {
		return webpageContentDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WebpageContent> getAll() {
		return webpageContentDao.getAll();
	}

	@Override
	public void saveOrUpdate(WebpageContent webpageContent) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(webpageContent.getId() == null){
			webpageContent.setCreatedBy(user);
			webpageContent.setCreated(new DateTime());
			webpageContentDao.save(webpageContent);
		}else{
			webpageContent.setChangedBy(user);
			webpageContent.setChanged(new DateTime());
			webpageContentDao.update(webpageContent);
		}
	}

}
