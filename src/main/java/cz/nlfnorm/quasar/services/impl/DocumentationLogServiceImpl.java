package cz.nlfnorm.quasar.services.impl;

import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.dao.DocumentationLogDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.DocumentationLog;
import cz.nlfnorm.quasar.entities.QuasarSettings;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.services.DocumentatinLogService;
import cz.nlfnorm.utils.UserUtils;

/**
 * QUASAR service. Implementation of Documentation log business logic
 * 
 * @see {@link DocumentationLog}
 * @author Peter Jurkovic
 * @date Jul 17, 2014
 */
@Transactional
@Service("documentationLogService")
public class DocumentationLogServiceImpl extends LogServiceImpl implements DocumentatinLogService{

	@Autowired
	private DocumentationLogDao documentationLogDao;
	@Autowired
	private AuditorService auditorService;
	
	@Override
	public void create(final DocumentationLog log) {
		documentationLogDao.save(log);
	}

	@Override
	public void update(final DocumentationLog log) {
		documentationLogDao.update(log);	
	}

	@Override
	@Transactional(readOnly = true)
	public DocumentationLog getById(final Long id) {
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
	 * Create new documentation log to logged user, who invoked request.
	 * 
	 * @see {@link Auditor}
	 * 
	 * @return generated ID of created documentation log 
	 * @throws IllegalArgumentException if logged user is not Auditor user
	 */
	@Override
	public Long createNewToLoginedUser() {
		final Auditor auditor = auditorService.getById(UserUtils.getLoggedUser().getId());
		Validate.notNull(auditor);
		DocumentationLog log = new DocumentationLog(auditor);
		log.setChangedBy(auditor);
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
	public void updateAndSetChanged(DocumentationLog log) {
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
	public void changeStatus(DocumentationLog auditLog, LogStatus newStatus, String comment) {
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
				throw new IllegalArgumentException("Unknown documentation log status: " + newStatus);
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
	public void setPendingStatus(DocumentationLog log, String withComment) {
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
	public void setRfusedStatus(DocumentationLog log, String withComment) {
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
	public void setApprovedStatus(DocumentationLog log, String withComment) {
		super.setApprovedStatus(log, withComment);
		// TODO Qualification update
		updateAndSetChanged(log);
	}

}
