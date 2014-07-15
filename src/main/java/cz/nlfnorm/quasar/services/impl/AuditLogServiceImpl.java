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
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.dao.AuditLogDao;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.AuditLogItem;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.QuasarSettings;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.utils.ParseUtils;
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
	
	private Map<String, Object> validateCriteria(final Map<String, Object> criteria){
		if(criteria.size() > 0){
			criteria.put(AuditorFilter.AUDITOR, ParseUtils.parseLongFromStringObject(criteria.get(AuditorFilter.AUDITOR)));
			criteria.put(AuditorFilter.PARNTER, ParseUtils.parseLongFromStringObject(criteria.get(AuditorFilter.PARNTER)));
			criteria.put(AuditorFilter.DATE_FROM, ParseUtils.parseDateTimeFromStringObject(criteria.get(AuditorFilter.DATE_FROM)));
			criteria.put(AuditorFilter.DATE_TO, ParseUtils.parseDateTimeFromStringObject(criteria.get(AuditorFilter.DATE_TO)));
			criteria.put(AuditorFilter.STATUS, ParseUtils.parseIntFromStringObject(criteria.get(AuditorFilter.STATUS)));
		}
		return criteria;
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
	 * @throws IllegalArgumentException - if is given audit log NULL
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
	 * @param log - Auditor's log, which status should be changed to APPROVED
	 * @param withComment - user's comment (can be empty) 
	 * 
	 * @see {@link QuasarSettings}
	 * @see {@link LogStatus}
	 * @throws AccessDeniedException - if the logged hasn't rights to approval
	 * @throws IllegalArgumentException - if given new status is not implemented, or given log and new status are NULL
	 */
	@Override
	public void changeStatus(final AuditLog auditLog, final LogStatus newStatus, final String comment) {
		super.setLogStatus(newStatus, auditLog, comment);
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
		// TODO implementation 
		setPendingStatus(log, withComment);
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
		setRfusedStatus(log, withComment);
		updateAndSetChanged(log);
	}
	
	
	/**
	 * Sets Audti log status to "APPROVED" and send notification email to given auditor.
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
	public void setApprovedStatus(final AuditLog log, final String withComment) {
		setApprovedStatus(log, withComment);
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

	
	
	
	
}
