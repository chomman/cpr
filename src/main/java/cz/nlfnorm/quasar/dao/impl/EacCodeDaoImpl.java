package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.EacCodeDao;
import cz.nlfnorm.quasar.entities.EacCode;

/**
 * QUASAR component
 * 
 * Hibernate implementation of EacCodeDao interface
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
@Repository("eacCodeDao")
public class EacCodeDaoImpl extends BaseDaoImpl<EacCode, Long> implements EacCodeDao{
	
	public EacCodeDaoImpl(){
		super(EacCode.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EacCode> getAllForQsAuditor() {
		StringBuilder hql = new StringBuilder("from EacCode eac ");
		hql.append(" where eac.forQsAuditor and eac.enabled=true order by eac.code asc ");
		Query query = createQuery(hql);
		query.setCacheable(false);
		return query.list();
	}
}
