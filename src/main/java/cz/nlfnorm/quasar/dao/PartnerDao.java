package cz.nlfnorm.quasar.dao;

import java.util.List;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.entities.Partner;

/**
 * QUASAR Component
 * 
 * DAO interface for partner entity
 *  
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
public interface PartnerDao  extends BaseDao<Partner, Long>{

	List<Partner> getPartnersByManager(User user);
	
	boolean isUserManager(User user);
	
}
