package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.quasar.dao.AuditLogDao;
import cz.nlfnorm.quasar.entities.AuditLog;

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
	
}
