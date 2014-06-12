package cz.nlfnorm.quasar.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.dao.AuditorDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.UserUtils;

/**
 * Implementation of Auditor service layer
 *  
 * @author Peter Jurkovic
 * @date Jun 12, 2014
 */
@Service("auditorService")
@Transactional(propagation = Propagation.REQUIRED)
public class AuditorServiceImpl implements AuditorService{
	
	@Autowired
	private AuditorDao auditorDao;
	@Autowired
	private UserService userService;
	
	@Override
	public void create(final Auditor auditor) {
		auditorDao.save(auditor);
	}

	@Override
	public void update(final Auditor auditor) {
		auditorDao.update(auditor);	
	}

	@Override
	public void delete(final Auditor auditor) {
		auditorDao.remove(auditor);
	}

	@Override
	@Transactional(readOnly = true)
	public Auditor getById(final Long id) {
		return auditorDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Auditor> getAll() {
		return auditorDao.getAll();
	}

	@Override
	public void createOrUpdate(final Auditor auditor) {
		final User user = UserUtils.getLoggedUser();
		auditor.setChanged(new LocalDateTime());
		auditor.setChangedBy(user);
		if(auditor.getId() == null){
			auditor.setCreated(auditor.getChanged());
			auditor.setCreatedBy(user);
			create(auditor);
		}else{
			update(auditor);
		}
	}

}
