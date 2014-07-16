package cz.nlfnorm.quasar.services;

import java.util.Map;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.entities.DocumentationLog;
import cz.nlfnorm.quasar.enums.LogStatus;

public interface DocumentatinLogService {
	
	void create(DocumentationLog log);
	
	void update(DocumentationLog log);
	
	DocumentationLog getById(Long id);
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
	Long createNewToLoginedUser();
	
	void updateAndSetChanged(DocumentationLog auditLog);
	
	void changeStatus(DocumentationLog auditLog, LogStatus newStatus, String comment);
		
	void setPendingStatus(DocumentationLog log, String withComment);
	
	void setRfusedStatus(DocumentationLog log, String withComment);
	
	void setApprovedStatus(DocumentationLog log, String withComment);
}
