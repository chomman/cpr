package cz.nlfnorm.quasar.services;

import java.util.Map;

import org.joda.time.LocalDate;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.services.IdentifiableByLongService;

public interface AuditLogService extends IdentifiableByLongService<AuditLog>{
	
	void create(AuditLog auditLog);
	
	void update(AuditLog auditLog);
	
	AuditLog getById(Long id);
	
	AuditLog getByAuditLogItemId(Long id);
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
	Long createNewToLoginedUser();
	
	LocalDate getEarliestPossibleDateForAuditLog(Auditor auditor);
	
	void updateAndSetChanged(AuditLog auditLog);
	
	void changeStatus(AuditLog auditLog, LogStatus newStatus, String comment);
		
	void setPendingStatus(AuditLog log, String withComment);
	
	void setRfusedStatus(AuditLog log, String withComment);
	
	void setApprovedStatus(AuditLog log, String withComment);
	

}
