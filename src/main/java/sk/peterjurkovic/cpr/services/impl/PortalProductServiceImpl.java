package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.PortalProductDao;
import sk.peterjurkovic.cpr.entities.PortalProduct;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.PortalProductService;
import sk.peterjurkovic.cpr.services.PortalUserService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Transactional(propagation = Propagation.REQUIRED)
@Service("portalProductService")
public class PortalProductServiceImpl implements PortalProductService {

	@Autowired
	private PortalProductDao portalProductDao;
	@Autowired
	private UserService userService;
	@Autowired
	private PortalUserService portalUserService;
	
	
	@Override
	public void create(final PortalProduct portalService) {
		portalProductDao.save(portalService);
	}
	
	@Override
	public void update(final PortalProduct portalService) {
		portalProductDao.update(portalService);
	}
	
	@Override
	public void delete(PortalProduct portalService) {
		portalProductDao.remove(portalService);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PortalProduct> getAll() {
		return portalProductDao.getAll();
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public PortalProduct getById(final Long id) {
		return portalProductDao.getByID(id);
	}

	
	@Override
	public void createOrUpdate(PortalProduct portalService) {
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

	@Override
	@Transactional(readOnly = true)
	public List<PortalProduct> getAllNotDeleted(final boolean publishedOnly) {
		return portalProductDao.getAllNotDeleted(publishedOnly);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PortalProduct> getAllOnlinePublications(boolean publishedOnly) {
		return portalProductDao.getAllOnlinePublications(publishedOnly);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PortalProduct> getAllRegistrations(boolean publishedOnly) {
		return portalProductDao.getAllRegistrations(publishedOnly);
	}
	
	
	
}
