package cz.nlfnorm.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.NotifiedBodyDao;
import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.NotifiedBodyService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.utils.UserUtils;

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
			notifiedBody.setCreated(new LocalDateTime());
			notifiedBodyDao.save(notifiedBody);
		}else{
			notifiedBody.setChangedBy(user);
			notifiedBody.setChanged(new LocalDateTime());
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
	@Transactional(readOnly = true)
	public List<NotifiedBody> getNotifiedBodiesGroupedByCountry(Boolean enabled) {
		return notifiedBodyDao.getNotifiedBodiesGroupedByCountry(enabled);
	}
	

	
	@Override
	@Transactional(readOnly = true)
	public List<NotifiedBody> autocomplete(String term, final Boolean enabled) {
		if(StringUtils.isBlank(term)){
			return new ArrayList<NotifiedBody>();
		}
		term = term.toLowerCase();
		return notifiedBodyDao.autocomplete(term.toLowerCase(), enabled);
	}
	
	
	
}
