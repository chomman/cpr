package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.NotifiedBodyDao;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("notifiedBodyService")
@Transactional(propagation = Propagation.REQUIRED)
public class NotifiedBodyServiceImpl implements NotifiedBodyService {

	@Autowired
	private NotifiedBodyDao notifiedBodyDao;
	@Autowired
	private UserService userService;
	
//	private final Logger logger = Logger.getLogger(getClass());
	
	
	@Override
	public void createNotifiedBody(NotifiedBody notifiedBody) {
		notifiedBodyDao.save(notifiedBody);
	}

	@Override
	public void updateNotifiedBody(NotifiedBody notifiedBody) {
		notifiedBodyDao.update(notifiedBody);
	}

	@Override
	public void deleteNotifiedBody(NotifiedBody notifiedBody) {
		notifiedBodyDao.remove(notifiedBody);
	}

	@Override
	@Transactional(readOnly = true)
	public NotifiedBody getNotifiedBodyById(Long id) {
		return notifiedBodyDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public NotifiedBody getNotifiedBodyByCode(String code) {
		return notifiedBodyDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NotifiedBody> getAllNotifiedBodies() {
		return notifiedBodyDao.getAll();
	}
	
	
	@Override
	public void saveOrUpdateNotifiedBody(NotifiedBody notifiedBody) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(notifiedBody.getId() == null){
			notifiedBody.setCreatedBy(user);
			notifiedBody.setCreated(new DateTime());
			notifiedBodyDao.save(notifiedBody);
		}else{
			notifiedBody.setChangedBy(user);
			notifiedBody.setChanged(new DateTime());
			notifiedBodyDao.update(notifiedBody);
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public boolean canBeDeleted(NotifiedBody notifiedBody) {
		return notifiedBodyDao.canBeDeleted(notifiedBody);
	}

	
	@Override
	@Transactional(readOnly = true)
	public boolean isNotifiedBodyNameUniqe(String name, Long id) {
		return notifiedBodyDao.isNameUniqe( CodeUtils.toSeoUrl( name ) , id );
	}

	@Override
	public List<NotifiedBody> getNotifiedBodiesGroupedByCountry() {
		return notifiedBodyDao.getNotifiedBodiesGroupedByCountry();
	}
	
	
	
}
