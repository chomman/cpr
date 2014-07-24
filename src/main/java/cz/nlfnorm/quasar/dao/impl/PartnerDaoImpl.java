package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.dao.PartnerDao;
import cz.nlfnorm.quasar.entities.Partner;

/**
 * QUASAR
 * 
 * Hibernate implementation of Partner DAO interface
 * 
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
@Repository("partnerDao")
public class PartnerDaoImpl extends BaseDaoImpl<Partner, Long> implements PartnerDao{

	public PartnerDaoImpl(){
		super(Partner.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Partner> getPartnersByManager(final User user) {
		return createQuery("select p from Partner p where p.manager.id = :user ")
				.setLong("user", user.getId())
				.list();
		
	}

	@Override
	public boolean isUserManager(final User user) {
		final Integer result = ((Integer) 
				createQuery("select count(*) form Partner p where p.manager.id = :user ")
				.setLong("user", user.getId())
				.setMaxResults(1)
				.uniqueResult());
		return result > 0;
	}
	
}
