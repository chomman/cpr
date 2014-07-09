package cz.nlfnorm.quasar.services;

import cz.nlfnorm.quasar.entities.AuditLogItem;

public interface AuditLogItemService {
	
	void create(AuditLogItem auditLogItem);
	
	void update(AuditLogItem auditLogItem);
	
	void delete(AuditLogItem auditLogItem);
	
	AuditLogItem getById(long id);
	
	void createOrUpdate(AuditLogItem auditLogItem);
	
}
