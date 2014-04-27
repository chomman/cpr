package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.PortalServiceDao;
import sk.peterjurkovic.cpr.entities.PortalService;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.PortalServiceService;
import sk.peterjurkovic.cpr.services.PortalUserService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Transactional(propagation = Propagation.REQUIRED)
@Service("portalServiceService")
public class PortalServiceServiceImpl implements PortalServiceService {

	@Autowired
	private PortalServiceDao portalServiceDao;
	@Autowired
	private UserService userService;
	@Autowired
	private PortalUserService portalUserService;
	
	
	@Override
	public void create(final PortalService portalService) {
		portalServiceDao.save(portalService);
	}
	
	@Override
	public void update(final PortalService portalService) {
		portalServiceDao.update(portalService);
	}
	
	@Override
	public void delete(PortalService portalService) {
		portalServiceDao.remove(portalService);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PortalService> getAll() {
		return portalServiceDao.getAll();
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public PortalService getById(final Long id) {
		return portalServiceDao.getByID(id);
	}

	
	@Override
	public void createOrUpdate(PortalService portalService) {
		User user = UserUtils.getLoggedUser();
		portalService.setChanged(new LocalDateTime());
		portalService.setChangedBy(user);
		if(portalService.getId() == null){
			portalService.setCreated(new LocalDateTime());
			portalService.setCreatedBy(user);
			create(portalService);
		}else{
			update(portalService);
		}
	}
	
	
	
}
