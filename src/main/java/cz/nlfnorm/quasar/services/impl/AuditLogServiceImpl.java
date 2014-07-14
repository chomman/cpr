package cz.nlfnorm.quasar.services.impl;

import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.dao.AuditLogDao;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.utils.UserUtils;

@Transactional
@Service("auditLogService")
public class AuditLogServiceImpl extends LogServiceImpl implements AuditLogService {

	@Autowired
	private AuditLogDao auditLogDao;
	@Autowired
	private AuditorService auditorService;
	
	@Override
	public void create(final AuditLog auditLog) {
		Validate.notNull(auditLog);
		auditLogDao.save(auditLog);
	}

	@Override
	public void update(final AuditLog auditLog) {
		Validate.notNull(auditLog);
		auditLog.setChangedBy(UserUtils.getLoggedUser());
		auditLog.setChanged(new LocalDateTime());
		auditLogDao.update(auditLog);
	}

	@Override
	@Transactional(readOnly = true)
	public AuditLog getById(final Long id) {
		return auditLogDao.getByID(id);
	}

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

	@Override
	public Long createNewToLoginedUser() {
		final Auditor auditor = auditorService.getById(UserUtils.getLoggedUser().getId());
		Validate.notNull(auditor);
		AuditLog auditLog = new AuditLog(auditor);
		auditLog.setChangedBy(auditor);
		create(auditLog);
		return auditLog.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public LocalDate getEarliestPossibleDateForAuditLog() {
		return  auditLogDao.getEarliestPossibleDateForAuditLog(UserUtils.getLoggedUser().getId());
	}

	@Override
	public void updateAndSetChanged(final AuditLog auditLog) {
		Validate.notNull(auditLog);
		auditLog.setChangedBy(UserUtils.getLoggedUser());
		auditLog.setChanged(new LocalDateTime());
		update(auditLog);
	}

	
	@Override
	public void changeStatus(final AuditLog auditLog, final LogStatus newStatus,final String comment) {
		Validate.notNull(auditLog);
		Validate.notNull(newStatus);
		if(!newStatus.equals(auditLog.getStatus())){
			
		}
	}
	
	
	
}
