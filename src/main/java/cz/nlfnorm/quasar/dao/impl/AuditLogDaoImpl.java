package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.quasar.dao.AuditLogDao;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.Auditor;

@Repository("auditLogDao")
public class AuditLogDaoImpl extends AbstractLogDaoImpl<AuditLog> implements AuditLogDao{
	
	public AuditLogDaoImpl(){
		super(AuditLog.class);
	}

	@Override
	public AuditLog getByAuditLogItemId(final Long id) {
		return (AuditLog)createQuery("select log form AuditLog log join log.items item where item.id = :id")
				.setMaxResults(1)
				.setLong("id", id)
				.uniqueResult();
	}

	
	@Override
	public Double getAvgAuditorsRating(final Auditor auditor) {
		final String hql = 
				"SELECT avg(log.rating) FROM AuditLog log " +
				"WHERE log.rating IS NOT NULL AND log.auditor.id=:id ";
		
		return (Double)createQuery(hql)
				.setLong("id", auditor.getId())
				.setMaxResults(1)
				.uniqueResult();		
	}
	

}
