package cz.nlfnorm.quasar.dao;

import java.util.Map;

import org.joda.time.LocalDate;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.Auditor;

public interface AuditLogDao extends BaseDao<AuditLog, Long>{
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
	AuditLog getByAuditLogItemId(Long id);
	
	LocalDate getEarliestPossibleDateForLog(Long auditorId);
	
	Double getAvgAuditorsRating(Auditor auditor);
}
