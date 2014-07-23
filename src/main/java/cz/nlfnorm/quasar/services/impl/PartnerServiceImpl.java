package cz.nlfnorm.quasar.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.dao.PartnerDao;
import cz.nlfnorm.quasar.entities.Partner;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.utils.UserUtils;

/**
 * QUASAR Component
 * 
 * Implementation of Partner interface
 * 
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
@Transactional
@Service("partnerService")
public class PartnerServiceImpl implements PartnerService{

	@Autowired
	private PartnerDao partnerDao;
	
	@Override
	public void update(final Partner partner) {
		partnerDao.update(partner);
	}

	@Override
	public void create(final Partner partner) {
		partnerDao.save(partner);
	}

	@Override
	public void delete(final Partner partner) {
		partnerDao.remove(partner);
	}

	@Override
	@Transactional(readOnly = true)
	public Partner getById(final Long id) {
		return partnerDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Partner> getAll() {
		return partnerDao.getAll();
	}

	@Override
	public void createOrUpdate(final Partner partner) {
		final User user = UserUtils.getLoggedUser();
		partner.setChangedBy(user);
		partner.setChanged(new LocalDateTime());
		if(partner.getId() == null){
			create(partner);
		}else{
			update(partner);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Partner> getPartnersByManager(User user) {
		return partnerDao.getPartnersByManager(user);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean isUserPartnerManager(User user){
		return partnerDao.isUserManager(user);
	}

	
	
	

}
