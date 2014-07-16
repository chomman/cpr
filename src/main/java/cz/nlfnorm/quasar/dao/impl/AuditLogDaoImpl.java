package cz.nlfnorm.quasar.dao.impl;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.quasar.dao.AuditLogDao;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.enums.LogStatus;

@Repository("auditLogDao")
public class AuditLogDaoImpl extends AbstractLogDaoImpl<AuditLog> implements AuditLogDao{
	
	public AuditLogDaoImpl(){
		super(AuditLog.class);
	}

	@Override
	public LocalDate getEarliestPossibleDateForAuditLog(final Long auditorId) {
		final String hql = "select max(ali.auditDate) from AuditLogItem ali " +
							" join ali.auditLog auditLog " +
							" join auditLog.auditor auditor " +
						   " where auditor.id=:auditorId AND auditLog.status = :status ";
		return (LocalDate) createQuery(hql)
						.setLong("auditorId", auditorId)
						.setInteger("status", LogStatus.APPROVED.getId())
						.setMaxResults(1)
						.uniqueResult();
	}

	@Override
	public AuditLog getByAuditLogItemId(final Long id) {
		return (AuditLog)createQuery("select log form AuditLog log join log.items item where item.id = :id")
				.setMaxResults(1)
				.setLong("id", id)
				.uniqueResult();
	}
	
}
