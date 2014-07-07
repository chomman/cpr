package cz.nlfnorm.quasar.dao;

import java.util.Map;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.entities.AuditLog;

public interface AuditLogDao extends BaseDao<AuditLog, Long>{
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
}