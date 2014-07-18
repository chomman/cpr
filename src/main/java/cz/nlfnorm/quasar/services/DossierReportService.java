package cz.nlfnorm.quasar.services;

import java.util.Map;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.entities.DossierReport;
import cz.nlfnorm.quasar.enums.LogStatus;

public interface DossierReportService extends PageableLogService<DossierReport> {
	
	void create(DossierReport dossierReport);
	
	void update(DossierReport dossierReport);
	
	DossierReport getById(Long id);
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
	Long createNewToLoginedUser();
	
	void updateAndSetChanged(DossierReport auditdossierReport);
	
	void changeStatus(DossierReport auditdossierReport, LogStatus newStatus, String comment);
		
	void setPendingStatus(DossierReport dossierReport, String withComment);
	
	void setRfusedStatus(DossierReport dossierReport, String withComment);
	
	void setApprovedStatus(DossierReport dossierReport, String withComment);
}
