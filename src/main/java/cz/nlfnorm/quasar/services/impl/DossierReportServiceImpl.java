package cz.nlfnorm.quasar.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.dao.DossierReportDao;
import cz.nlfnorm.quasar.dto.DossierReportCodeSumDto;
import cz.nlfnorm.quasar.entities.AuditLogItem;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.DossierReport;
import cz.nlfnorm.quasar.entities.DossierReportItem;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.entities.QuasarSettings;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.services.DossierReportService;
import cz.nlfnorm.utils.UserUtils;

/**
 * QUASAR service. Implementation of Documentation log business logic
 * 
 * @see {@link DossierReport}
 * @author Peter Jurkovic
 * @date Jul 17, 2014
 */
@Transactional
@Service("dossierReportService")
public class DossierReportServiceImpl extends LogServiceImpl implements DossierReportService{

	@Autowired
	private DossierReportDao documentationLogDao;
		
	private void create(final DossierReport log) {
		documentationLogDao.save(log);
	}

	@Override
	public void update(final DossierReport log) {
		documentationLogDao.update(log);	
	}

	@Override
	@Transactional(readOnly = true)
	public DossierReport getById(final Long id) {
		return documentationLogDao.getByID(id);
	}
	
	/**
	 * Retrieve page of documentation logs, based on given criteria. 
	 * 
	 * @param criteria - map of criteria, which should be applied
	 * @param page - number of page witch should be returned
	 * 
	 * @return page of documentation logs, based on given criteria
	 */
	@Override
	@Transactional(readOnly = true)
	public PageDto getPage(final Map<String, Object> criteria, final int pageNumber) {
		return documentationLogDao.getPage(validateCriteria(criteria), pageNumber);
	}
	
	/**
	 * Create new Dossier report to logged user (who invoked request).
	 * 
	 * @see {@link Auditor}
	 * 
	 * @return generated ID of created documentation log 
	 * @throws IllegalArgumentException if logged user is not Auditor user
	 */
	@Override
	public Long createNewToLoginedUser() {
		return createNew(UserUtils.getLoggedUser().getId());
	}
	
	/**
	 * Create new Dossier report to worker with given ID.
	 * 
	 * @return generated ID of created documentation log 
	 * @throws IllegalArgumentException if logged user is not Auditor user
	 */
	@Override
	public Long createNew(final Long auditorId) {
		final Auditor auditor = auditorService.getById(auditorId);
		Validate.notNull(auditor);
		final User user = UserUtils.getLoggedUser();
		if(!auditorId.equals(user.getId()) && !user.isQuasarAdmin()){
			throw new AccessDeniedException(user.toString());
		}
		DossierReport log = new DossierReport(auditor);
		log.setChangedBy(user);
		log.setCreatedBy(user);
		create(log);
		return log.getId();
	}

	/**
	 * Update given auditor's documentation log and set changed to current time.
	 * 
	 * @param log - auditor's documentation log which should be updated
	 * @throws IllegalArgumentException - if is given log is NULL
	 */
	@Override
	public void updateAndSetChanged(DossierReport log) {
		Validate.notNull(log);
		log.setChangedBy(UserUtils.getLoggedUser());
		log.setChanged(new LocalDateTime());
		update(log);
	}


	/**
	 * Set new status to given documentation log, if is given user authorized and statuses are different. 
	 * 
	 * @param newStatus - new status of given log
	 * @param log - Auditor's log
	 * @param withComment - user's comment (can be empty) 
	 * 
	 * @see {@link QuasarSettings}
	 * @see {@link LogStatus}
	 * @throws AccessDeniedException - if the logged hasn't rights to approval
	 * @throws IllegalArgumentException - if given new status is not implemented, or given log and new status are NULL
	 */
	@Override
	public void changeStatus(DossierReport auditLog, LogStatus newStatus, String comment) {
		Validate.notNull(auditLog);
		Validate.notNull(newStatus);
		if(!newStatus.equals(auditLog.getStatus())){
			if(newStatus.equals(LogStatus.PENDING)){
				setPendingStatus(auditLog, comment);
			}else if(newStatus.equals(LogStatus.REFUSED)){
				setRfusedStatus(auditLog, comment);
			}else if(newStatus.equals(LogStatus.APPROVED)){
				setApprovedStatus(auditLog, comment);
			}else{
				throw new IllegalArgumentException("Unknown dossier report status: " + newStatus);
			}
		}
	}
	
	/**
	 * Sets Documentation log status to "PENDING" and send notification email to main QUASAR admin
	 * 
	 * @param log - Auditor's log, which status should be changed to PENDING
	 * @param withComment - user's comment (can be empty)
	 * 
	 * @see {@link QuasarSettings}
	 * @see {@link LogStatus}
	 * @throws AccessDeniedException - if is logged user unauthorized
	 * @throws IllegalArgumentException - if given log is NULL
	 */
	@Override
	public void setPendingStatus(DossierReport log, String withComment) {
		super.setPendingStatus(log, withComment);
		updateAndSetChanged(log);
	}
	
	/**
	 * Sets Documentation log status to "REFUSED" and send notification email to given auditor.
	 * (if has given auditor defined other e-mail addresses, the copy of this e-mail will
	 * be forwarded to this addresses too.) 
	 * 
	 * @param log - Auditor's log, which status should be changed to REFUSED
	 * @param withComment - user's comment (can be empty)
	 * 
	 * @see {@link QuasarSettings}
	 * @see {@link LogStatus}
	 * @throws AccessDeniedException - if the logged hasn't rights to refuse
	 * @throws IllegalArgumentException - if given log is NULL
	 */
	@Override
	public void setRfusedStatus(DossierReport log, String withComment) {
		super.setRfusedStatus(log, withComment);
		updateAndSetChanged(log);
	}
	
	
	/**
	 * Sets Documentation log status to "APPROVED" and send notification email to given auditor.
	 * (if has given auditor defined other e-mail addresses, the copy of this e-mail will
	 * be forwarded to this addresses too.) 
	 * 
	 * @param log - Auditor's log, which status should be changed to APPROVED
	 * @param withComment - user's comment (can be empty) 
	 * 
	 * @see {@link QuasarSettings}
	 * @see {@link LogStatus}
	 * @throws AccessDeniedException - if the logged hasn't rights to approval
	 * @throws IllegalArgumentException - if given log is NULL
	 */
	@Override
	public void setApprovedStatus(DossierReport log, String withComment) {
		super.setApprovedStatus(log, withComment);	
		updateQualification(log);
		updateAndSetChanged(log);
	}

	
	/**
	 * Retrieves latest logged auditor's date of approved dossier report item.
	 * If logged user is not Auditor, or has not any approved audit log, will be returned NULL. 
	 * 
	 * <em>NOTE: Auditor can create Audit log item only with date which is equal or after this returned value.</em>  
	 * 
	 * @see {@link AuditLogItem}
	 * @see {@link Auditor}
	 * 
	 * @return Auditor's earliest possible date, which can be used for new Audit log item 
	 */
	@Override
	@Transactional(readOnly = true)
	public LocalDate getEarliestPossibleDateForLog(final Auditor auditor) {
		Validate.notNull(auditor);
		return  documentationLogDao.getEarliestPossibleDateForLog(auditor.getId());
	}
	
	
	
	/**
	 * Calculate NANDO codes occurrences and depends on Dossier report item type. If is type of Design Dossier (DD),
	 * increment count of DD for appropriate code. If is type of Technical File, increment TF. Result is saved in Map, 
	 * which key of Map si {@link NandoCode}, value of Map is {@link DossierReportCodeSumDto}.
	 * After calculation updates auditor total TF reviews and DD reviews. 
	 * 
	 * @param dossierReport - report
	 * @throws IllegalArgumentException - If given report is NULL, or report's status is NOT APPROVED
	 * @see {@link DossierReportCodeSumDto}
	 * @see {@link LogStatus}
	 */
	@Override
	public void updateQualification(final DossierReport dossierReport){
		Validate.notNull(dossierReport);
		Validate.notNull(dossierReport.getAuditor());
		validateApprovance(dossierReport);
		final Auditor auditor = dossierReport.getAuditor();
		final Map<NandoCode, DossierReportCodeSumDto> totals = getTotalsFor(dossierReport);
		for(Map.Entry<NandoCode, DossierReportCodeSumDto> entry : totals.entrySet()){
			auditor.incrementTfReviews( entry.getValue().getTfReviews());
			auditor.incrementDdReviews( entry.getValue().getDdReviews());
			auditorNandoCodeService.incrementProductAssessorRAndProductSpecialistTotals(
					entry.getKey().getId(), 
					auditor.getId(),
					entry.getValue().getTfReviews(), 
					entry.getValue().getDdReviews()
				);
		}
		auditorService.createOrUpdate(auditor);
	}

	
	/**
	 * Calculate sums of Design Dossiers (DD) and Technical Files (TF) for NANDO codes, which are contained in given 
	 * Dossier report. Result is saved into Map, which keys contains NANDO code and value contains 
	 * {@link DossierReportCodeSumDto} (number of DDs and TFs).
	 * 
	 * @param dossierReport - report, which should be calculated
	 * @return Map<NandoCode, DossierReportCodeSumDto> - sums
	 * @throws IllegalArgumentException - If given report is NULL
	 * @see {@link DossierReportCodeSumDto}
	 */
	@Override
	public Map<NandoCode, DossierReportCodeSumDto> getTotalsFor(final DossierReport dossierReport){
		Validate.notNull(dossierReport);
		final Map<NandoCode, DossierReportCodeSumDto> nandoCodes = new HashMap<>();
		for(final DossierReportItem item: dossierReport.getItems()){
			incrementNandoCodes(nandoCodes, item);
		}
		return nandoCodes;
	}
	
	private void incrementNandoCodes(final Map<NandoCode, DossierReportCodeSumDto> nandoCodes, final DossierReportItem item){
		for(final NandoCode code : item.getNandoCodes()){
			if(nandoCodes.containsKey(code)){
				final DossierReportCodeSumDto totals = nandoCodes.get(code);
				incrementValues(item, totals);
			}else{
				DossierReportCodeSumDto totals = new DossierReportCodeSumDto();
				incrementValues(item, totals);
				nandoCodes.put(code, totals);
			}
		}
	}
	
	private void incrementValues(final DossierReportItem item, final DossierReportCodeSumDto totals){
		if(item.isDesignDossier()){
			totals.incrementDdReviews();
		}else{
			totals.incrementTfReviews();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PageDto getPage(Map<String, Object> criteria) {
		return documentationLogDao.getPage(criteria);
	}
}
