package cz.nlfnorm.quasar.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.dao.EacCodeDao;
import cz.nlfnorm.quasar.entities.EacCode;
import cz.nlfnorm.quasar.services.EacCodeService;
import cz.nlfnorm.utils.UserUtils;

/**
 * Implementation of Eac code interface
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
@Service("eacCodeService")
@Transactional(propagation = Propagation.REQUIRED)
public class EacCodeServiceImpl implements EacCodeService {

	@Autowired
	private EacCodeDao eacCodeDao;
	
	@Override
	@Transactional(readOnly = true)
	public EacCode getById(final Long id) {
		return eacCodeDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public EacCode getByCode(final String code) {
		return eacCodeDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EacCode> getAll() {
		return eacCodeDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<EacCode> getAllForQsAuditor() {
		return eacCodeDao.getAllForQsAuditor();
	}

	@Override
	public void create(final EacCode eacCode) {
		eacCodeDao.save(eacCode);
	}

	@Override
	public void update(final EacCode eacCode) {
		eacCodeDao.update(eacCode);
	}

	@Override
	public void delete(final EacCode eacCode) {
		eacCodeDao.remove(eacCode);
	}

	@Override
	public void createOrUpdate(final EacCode eacCode) {
		final User user = UserUtils.getLoggedUser();
		eacCode.setChangedBy(user);
		eacCode.setChanged(new LocalDateTime());
		if(eacCode.getId() == null){
			create(eacCode);
		}else{
			update(eacCode);
		}
	}

}
