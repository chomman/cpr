package cz.nlfnorm.quasar.services;

import java.util.Map;

import org.joda.time.LocalDate;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.enums.LogStatus;

public interface AuditLogService{
	
	void create(AuditLog auditLog);
	
	void update(AuditLog auditLog);
	
	AuditLog getById(Long id);
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
	Long createNewToLoginedUser();
	
	LocalDate getEarliestPossibleDateForAuditLog();
	
	void updateAndSetChanged(AuditLog auditLog);
	
	void changeStatus(AuditLog auditLog, LogStatus newStatus, String comment);
		
	void setPendingStatus(AuditLog log, String withComment);
	
	void setRfusedStatus(AuditLog log, String withComment);
	
	void setApprovedStatus(AuditLog log, String withComment);
}
