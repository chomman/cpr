package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.AuditLogItemDao;
import cz.nlfnorm.quasar.entities.AuditLogItem;

@Repository("auditLogItemDao")
public class AuditLogItemDaoImpl extends BaseDaoImpl<AuditLogItem, Long> implements AuditLogItemDao{
	
	public AuditLogItemDaoImpl(){
		super(AuditLogItem.class);
	}
}
