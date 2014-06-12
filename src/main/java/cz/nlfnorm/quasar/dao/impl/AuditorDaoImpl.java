package cz.nlfnorm.quasar.dao.impl;

import org.hibernate.Query;
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

	@Override
	public boolean isItcIdUniqe(Integer itcId, Long auditorId) {
		final Query query = createQuery("select count(*) from Auditor a where a.itcId=:itcId and a.id<>:auditorId ")
			.setCacheable(false)
			.setMaxResults(1)
			.setInteger("itcId", itcId)
			.setLong("auditorId", auditorId);
		return (Long)query.uniqueResult() == 0l ;
	}

}
