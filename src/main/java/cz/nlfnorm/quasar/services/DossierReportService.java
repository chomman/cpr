package cz.nlfnorm.quasar.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.joda.time.LocalDate;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.dto.DossierReportCodeSumDto;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.DossierReport;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.enums.LogStatus;

public interface DossierReportService extends PageableLogService<DossierReport> {
		
	Long createNew(Long auditorId);
	
	void update(DossierReport dossierReport);
	
	DossierReport getById(Long id);
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
	Long createNewToLoginedUser();
	
	void updateAndSetChanged(DossierReport auditdossierReport);
	
	void changeStatus(DossierReport auditdossierReport, LogStatus newStatus, String comment);
		
	void setPendingStatus(DossierReport dossierReport, String withComment);
	
	void setRfusedStatus(DossierReport dossierReport, String withComment);
	
	void setApprovedStatus(DossierReport dossierReport, String withComment);
	
	LocalDate getEarliestPossibleDateForLog(Auditor auditor);
	
	Map<NandoCode, DossierReportCodeSumDto> getTotalsFor(DossierReport dossierReport);
	
	void updateQualification(DossierReport dossierReport);
	
	void processImport(Long dossierReportId, InputStream is) throws IOException;
}
