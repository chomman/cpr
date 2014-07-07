package cz.nlfnorm.quasar.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.quasar.dao.AuditLogItemDao;
import cz.nlfnorm.quasar.entities.AuditLogItem;
import cz.nlfnorm.quasar.services.AuditLogItemService;

@Transactional
@Service("auditLogItemService")
public class AuditLogItemServiceImpl implements AuditLogItemService {
	
	@Autowired
	private AuditLogItemDao auditLogItemDao;
	
	@Override
	public void create(final AuditLogItem auditLogItem) {
		auditLogItemDao.save(auditLogItem);
	}

	@Override
	public void update(final AuditLogItem auditLogItem) {
		auditLogItemDao.update(auditLogItem);
	}

	@Override
	public void delete(final AuditLogItem auditLogItem) {
		auditLogItemDao.remove(auditLogItem);
	}

	@Override
	@Transactional(readOnly = true)
	public AuditLogItem getById(final long id) {
		return auditLogItemDao.getByID(id);
	}

}
