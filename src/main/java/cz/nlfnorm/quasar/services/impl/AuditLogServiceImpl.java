package cz.nlfnorm.quasar.services.impl;

import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.dao.AuditLogDao;
import cz.nlfnorm.quasar.dto.AuditLogCodeSumDto;
import cz.nlfnorm.quasar.dto.AuditLogTotalsDto;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.AuditLogItem;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorEacCode;
import cz.nlfnorm.quasar.entities.AuditorNandoCode;
import cz.nlfnorm.quasar.entities.EacCode;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.entities.QuasarSettings;
import cz.nlfnorm.quasar.enums.AuditLogItemType;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.services.AuditorEacCodeService;
import cz.nlfnorm.quasar.services.AuditorNandoCodeService;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.utils.UserUtils;

/**
 * Implementation of AuditLog business logic
 * 
 * @see {@link AuditLog}
 * @author Peter Jurkovic
 * @date Jul 15, 2014
 */
@Transactional
@Service("auditLogService")
public class AuditLogServiceImpl extends LogServiceImpl implements AuditLogService {

	@Autowired
	private AuditLogDao auditLogDao;
	@Autowired
	private AuditorService auditorService;
	@Autowired
	private AuditorEacCodeService auditorEacCodeService;
	@Autowired
	private AuditorNandoCodeService auditorNandoCodeService;
	
	
	/**
	 * Create new auditor's audit log
	 * 
	 * @param auditLog - auditor's audit log which should be created
	 * @throws IllegalArgumentException - if is given audit log NULL
	 */
	@Override
	public void create(final AuditLog auditLog) {
		Validate.notNull(auditLog);
		auditLogDao.save(auditLog);
	}

	/**
	 * Updates given audit log 
	 */
	@Override
	public void update(final AuditLog auditLog) {
		Validate.notNull(auditLog);
		auditLogDao.update(auditLog);
	}

	
	@Override
	@Transactional(readOnly = true)
	public AuditLog getById(final Long id) {
		return auditLogDao.getByID(id);
	}

	/**
	 * Retrieve page of audit logs, based on given criteria. 
	 * 
	 * @param criteria - map of criteria, which should be applied
	 * @param page - number of page witch should be returned
	 * 
	 * @return page of audit logs, based on given criteria
	 */
	@Override
	@Transactional(readOnly = true)
	public PageDto getPage(final Map<String, Object> criteria, final int pageNumber) {
		return auditLogDao.getPage(validateCriteria(criteria), pageNumber);
	}
	
	
	
	/**
	 * Create new audit log to logged user, who invoked request.
	 * 
	 * @see {@link Auditor}
	 * 
	 * @return id of created audit log
	 * @throws IllegalArgumentException if logged user is not Auditor user
	 */
	@Override
	public Long createNewToLoginedUser() {
		final Auditor auditor = auditorService.getById(UserUtils.getLoggedUser().getId());
		Validate.notNull(auditor);
		AuditLog auditLog = new AuditLog(auditor);
		auditLog.setChangedBy(auditor);
		create(auditLog);
		return auditLog.getId();
	}

	/**
	 * Retrieves latest logged auditor's date of approved audit log item.
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
	public LocalDate getEarliestPossibleDateForAuditLog(final Auditor auditor) {
		Validate.notNull(auditor);
		return  auditLogDao.getEarliestPossibleDateForAuditLog(auditor.getId());
	}
	

	/**
	 * Update given auditor's audit log and set changed to current time.
	 * 
	 * @param auditLog - auditor's audit log which should be updated
	 * @throws IllegalArgumentException - if is given audit log is NULL
	 */
	@Override
	public void updateAndSetChanged(final AuditLog auditLog) {
		Validate.notNull(auditLog);
		auditLog.setChangedBy(UserUtils.getLoggedUser());
		auditLog.setChanged(new LocalDateTime());
		update(auditLog);
	}

	
	/**
	 * Change to given audit log status, if is given user authorized and statuses are different. 
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
	public void changeStatus(final AuditLog auditLog, final LogStatus newStatus, final String comment) {
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
				throw new IllegalArgumentException("Unknown Audit log status: " + newStatus);
			}
		}
	}
	
	/**
	 * Sets Audit log status to "PENDING" and send notification email to main QUASAR admin
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
	public void setPendingStatus(final AuditLog log, final String withComment) {
		super.setPendingStatus(log, withComment);
		updateAndSetChanged(log);
	}

	
	/**
	 * Sets Audit log status to "REFUSED" and send notification email to given auditor.
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
	public void setRfusedStatus(final AuditLog log, final String withComment) {
		super.setRfusedStatus(log, withComment);
		updateAndSetChanged(log);
	}
	
	
	/**
	 * Sets Audit log status to "APPROVED" and send notification email to given auditor.
	 * (if has given auditor defined other e-mail addresses, the copy of this e-mail will
	 * be forwarded to this addresses too.)  Update auditor's qualification based on given Log.
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
	public void setApprovedStatus(final AuditLog log, final String withComment) {
		super.setApprovedStatus(log, withComment);
		updateQualification(log);
		updateAndSetChanged(log);
	}

	
	/**
	 * Return Audit log by Audit Log item.
	 * 
	 * @param id =  Audit log item ID
	 * @see {@link AuditLogItem}
	 * @return AuditLog by Audit log item ID, or NULL if not exists
	 */
	@Override
	@Transactional(readOnly = true)
	public AuditLog getByAuditLogItemId(final Long id) {
		if(id == null){
			return null;
		}
		return auditLogDao.getByAuditLogItemId(id);
	}

	
	/**
	 * Updates Auditors Qs Auditor and Product Assessor-A of given audit log. 
	 * 
	 * Compute totals of all EAC codes and NANDO code, which contains given audit log 
	 * and increments totals to corresponding Auditors codes. Simultaneously also increments
	 * number or audit days and audits to Auditor.
	 * 
	 * @param auditLog
	 * @throws IllegalArgumentException - If is given audit log null, or audit log is not approved
	 * 
	 * @see {@link AuditorNandoCode}
	 * @see {@link AuditorEacCode}
	 */
	@Override
	public void updateQualification(final AuditLog auditLog){
		Validate.notNull(auditLog);
		Validate.notNull(auditLog.getAuditor());
		if(auditLog.getStatus() == null || !auditLog.getStatus().equals(LogStatus.APPROVED)){
			throw new IllegalArgumentException("Audit log status is not Approved, " + auditLog);
		}
		final Auditor auditor = auditLog.getAuditor();
		final AuditLogTotalsDto totals = getTotalsFor(auditLog);
		updateQsAuditorQualification(auditor, totals);
		updateProductAssessorAQualification(auditor, totals);
		auditor.incrementAuditDays(totals.getAuditDays());
		auditor.incrementAudits(totals.getAudits());
		auditorService.createOrUpdate(auditor);
	}
	
	
	/**
	 * Calculate EAC and NANDOCO occurrences and merge them into DTO object. Calculate sum of 
	 * audit days a audits too. Items are summed into two categories:
	 *  
	 * <ul>
	 *  <li>ISO 13485 - a voluntary classification</li>
	 *  <li>MDD/AIMD/IVD - NB audits</li>
	 * </ul>   
	 *  
	 * @see {@link AuditLogCodeSumDto}
	 * @see {@link AuditLogTotalsDto}
	 * 
	 * @param auditLog - audit log 
	 * @return totals - DTO which contains counted totals of NANDO, EAC code occurrences, audit days and audits
	 * @throws IllegalArgumentException - If given audit log is NULL
	 */
	@Override
	public AuditLogTotalsDto getTotalsFor(final AuditLog auditLog){
		Validate.notNull(auditLog);
		final AuditLogTotalsDto totals = new AuditLogTotalsDto();
		for(final AuditLogItem item: auditLog.getItems()){
			incrementEacCodes(totals.getEacCodes(), item);
			incrementNandoCodes(totals.getNandoCodes(), item);
			totals.incrementAudits();
			totals.incrementAuditDays(item.getDurationInDays());
		}
		return totals;
	}
	
	private void updateQsAuditorQualification(final Auditor auditor, final AuditLogTotalsDto totals){
		for(Map.Entry<EacCode, AuditLogCodeSumDto> entry : totals.getEacCodes().entrySet()){
			auditorEacCodeService.incrementAuditorEacCodeTotals(
					entry.getKey().getId(), 
					auditor.getId(), 
					entry.getValue().getNumberOfNbAudits(), 
					entry.getValue().getNumberOfIso13485Audits()
					);
		}
	}
	
	private void updateProductAssessorAQualification(final Auditor auditor, final AuditLogTotalsDto totals){
		for(Map.Entry<NandoCode, AuditLogCodeSumDto> entry : totals.getNandoCodes().entrySet()){
			auditorNandoCodeService.incrementAuditorNandoCodeTotals(
					entry.getKey().getId(), 
					auditor.getId(),
					entry.getValue().getNumberOfNbAudits(), 
					entry.getValue().getNumberOfIso13485Audits()
				);
		}
	}
	
			
	private void incrementNandoCodes(final Map<NandoCode, AuditLogCodeSumDto> nandoCodes, final AuditLogItem item){
		for(final NandoCode code : item.getNandoCodes()){
			if(nandoCodes.containsKey(code)){
				final AuditLogCodeSumDto totals = nandoCodes.get(code);
				incrementValues(item, totals);
			}else{
				AuditLogCodeSumDto totals = new AuditLogCodeSumDto();
				incrementValues(item, totals);
				nandoCodes.put(code, totals);
			}
		}
	}
	
	private void incrementEacCodes(final Map<EacCode, AuditLogCodeSumDto> eacCodes, final AuditLogItem item){
		for(final EacCode code : item.getEacCodes()){
			if(eacCodes.containsKey(code)){
				final AuditLogCodeSumDto totals = eacCodes.get(code);
				incrementValues(item, totals);
			}else{
				AuditLogCodeSumDto totals = new AuditLogCodeSumDto();
				incrementValues(item, totals);
				eacCodes.put(code, totals);
			}
		}
	}
	
	private void incrementValues(final AuditLogItem item, final AuditLogCodeSumDto totals){
		if(item.getType().equals(AuditLogItemType.ISO13485)){
			totals.incrementIso13484Audits();
		}else{
			totals.incrementNbAudits();
		}
	}
	
	
}
