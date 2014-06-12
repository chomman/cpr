package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.AuditorDao;
import cz.nlfnorm.quasar.entities.Auditor;

/**
 * QUASAR
 * 
 * Hibernate implementation of Auditor DAO interface
 * 
 * @author Peter Jurkovic
 * @date Jun 12, 2014
 */
@Repository("auditorDao")
public class AuditorDaoImpl extends BaseDaoImpl<Auditor, Long> implements AuditorDao{

	public AuditorDaoImpl() {
		super(Auditor.class);
	}

}
