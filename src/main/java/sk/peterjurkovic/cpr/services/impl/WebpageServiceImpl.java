package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.WebpageDao;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.utils.UserUtils;


@Service("webpageService")
@Transactional(propagation = Propagation.REQUIRED)
public class WebpageServiceImpl implements WebpageService{

	@Autowired
	private WebpageDao webpageDao;
	@Autowired
	private UserService userService;
	
	
	
	@Override
	public void createWebpage(Webpage webpage) {
		webpageDao.save(webpage);
	}
	
	@Override
	public void updateWebpage(Webpage webpage) {
		webpageDao.update(webpage);
	}
	
	@Override
	public void deleteWebpage(Webpage webpage) {
		webpageDao.remove(webpage);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Webpage getWebpageById(Long id) {
		return webpageDao.getByID(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Webpage getWebpageByCode(String code) {
		return webpageDao.getByCode(code);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getAll() {
		return webpageDao.getAll();
	}
	
	
	@Override
	public void saveOrUpdate(Webpage webpage) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		if(webpage.getId() == null){
			webpage.setCreatedBy(user);
			webpage.setCreated(new DateTime());
			webpageDao.save(webpage);
		}else{
			webpage.setChangedBy(user);
			webpage.setChanged(new DateTime());
			webpageDao.update(webpage);
		}
	}

	@Override
	public List<Webpage> getPublicSection(Long sectionId) {
		return webpageDao.getPublicSection(sectionId);
	}
	
}
