package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
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
	
}
