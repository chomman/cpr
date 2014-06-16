package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.EacCodeDao;
import cz.nlfnorm.quasar.entities.Auditor;
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
		Query query = createQuery("from EacCode eac where eac.forQsAuditor and eac.enabled=true order by eac.code asc ");
		query.setCacheable(false);
		return query.list();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<EacCode> getAllNonAssociatedAuditorsEacCodes(final Auditor auditor) {
		StringBuilder hql = new StringBuilder("select code from EacCode code ");
		hql.append(" where code.forQsAuditor = true and not EXISTS( ")
			.append(" select 1 from AuditorEacCode ac ")
			.append(" where ac.id = code.id and ac.auditor.id = :id )");
		return createQuery(hql)
					.setCacheable(false)
					.setLong("id", auditor.getId())
					.list();
	}
}
