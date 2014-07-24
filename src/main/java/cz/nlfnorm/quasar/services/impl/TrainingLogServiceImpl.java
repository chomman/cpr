package cz.nlfnorm.quasar.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.dao.TrainingLogDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.entities.Partner;
import cz.nlfnorm.quasar.entities.QuasarSettings;
import cz.nlfnorm.quasar.entities.TrainingLog;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.quasar.services.TrainingLogService;
import cz.nlfnorm.utils.UserUtils;

@Transactional
@Service("trainingLogService")
public class TrainingLogServiceImpl extends LogServiceImpl implements TrainingLogService {

	@Autowired
	private TrainingLogDao trainingLogDao;
	@Autowired
	private AuditorService auditorService;
	@Autowired
	private PartnerService partnerService;

	/**
	 * Create new Training log and assign currently logged user (who invoked request).
	 * 
	 * @see {@link Auditor}
	 * 
	 * @return generated ID of created training log 
	 * @throws IllegalArgumentException if logged user is not Auditor user
	 */
	@Override
	public Long createNewToLoginedUser() {
		final User user = UserUtils.getLoggedUser();
		final Auditor auditor = auditorService.getById(user.getId());
		Validate.notNull(auditor);
		TrainingLog log = new TrainingLog();
		log.addAuditor(auditor);
		log.setChangedBy(user);
		log.setCreatedBy(user);
		trainingLogDao.save(log);
		return log.getId();
	}
	
	@Override
	public Long createNew() {
		final User user = UserUtils.getLoggedUser();
		TrainingLog log = new TrainingLog();
		log.setChangedBy(user);
		log.setCreatedBy(user);
		trainingLogDao.save(log);
		return log.getId();
	}
	
	/**
	 * Update given Training log
	 * @param trainingLog
	 * @throws IllegalArgumentException - If is given log NULL
	 */
	@Override
	public void update(final TrainingLog trainingLog) {
		Validate.notNull(trainingLog);
		trainingLogDao.update(trainingLog);
	}

	/**
	 * Update given Training log and set changed to current time.
	 * Update changed by to user, who creates request
	 * 
	 * @param trainingLog
	 * @throws IllegalArgumentException - If is given log NULL
	 */
	@Override
	public void updateAndSetChanged(final TrainingLog trainingLog) {
		Validate.notNull(trainingLog);
		trainingLog.setChangedBy(UserUtils.getLoggedUser());
		trainingLog.setChanged(new LocalDateTime());
		update(trainingLog);
	}

	
	/**
	 * Set new status to given training log, if is given user authorized and statuses are different. 
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
	public void changeStatus(final TrainingLog trainingLog, final LogStatus newStatus, final String comment) {
		Validate.notNull(trainingLog);
		Validate.notNull(newStatus);
		if(!newStatus.equals(trainingLog.getStatus())){
			if(newStatus.equals(LogStatus.PENDING)){
				setPendingStatus(trainingLog, comment);
			}else if(newStatus.equals(LogStatus.REFUSED)){
				setRfusedStatus(trainingLog, comment);
			}else if(newStatus.equals(LogStatus.APPROVED)){
				setApprovedStatus(trainingLog, comment);
			}else{
				throw new IllegalArgumentException("Unknown training log status: " + newStatus);
			}
		}
	}

	@Override
	public void setPendingStatus(TrainingLog trainingLog, String withComment) {
		super.setPendingStatus(trainingLog, withComment);
		updateAndSetChanged(trainingLog);
	}

	@Override
	public void setRfusedStatus(TrainingLog trainingLog, String withComment) {
		super.setApprovedStatus(trainingLog, withComment);
		// TODO updateQualification(log);
		updateAndSetChanged(trainingLog);
	}

	@Override
	public void setApprovedStatus(TrainingLog trainingLog, String withComment) {
		super.setRfusedStatus(trainingLog, withComment);
		updateAndSetChanged(trainingLog);
	}

	@Override
	@Transactional(readOnly = true)
	public PageDto getPage(Map<String, Object> criteria, int pageNumber) {
		return trainingLogDao.getPage(validateCriteria(criteria), pageNumber);
	}

	@Override
	@Transactional(readOnly = true)
	public TrainingLog getById(Long id) {
		return trainingLogDao.getByID(id);
	}

	
	
	/**
	 * Retrieve non-assigned worker in given Training log. Returned are only workers, for who 
	 * has creator of this log rights. So If is given user {@link Partner} manager, 
	 * returned will be all workers who has this manager under the competence. 
	 * Is if any user already assigned, will be excluded. If is given User QUASAR admin,
	 * will be returned all workers who are not already assigned in given log.
	 * 
	 * @param log - training log for which should be returned non-assigned Auditors
	 * @return list of non-Assigned auditors
	 * 
	 * @see {@link Authority}
	 * @see {@link Partner}
	 * 
	 * @throw {@link IllegalArgumentException} - If is given training log NULL
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Auditor> getUnassignedAuditorsForLog(final TrainingLog log) {
		Validate.notNull(log);
		return trainingLogDao.getAllUnassignedAuditorsToLog(log.getId(), log.getChangedBy().getId());
	}
	

	/**
	 * Retrieve all non-assigned worker in given {@link TrainingLog}. If given log
	 * does not contains any worker, will be returned all workers.
	 * 
	 * @param training log
	 * @return list of all unassigned worker in given Traning log
	 * 
	 * @throw {@link IllegalArgumentException} - If is given training log NULL
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Auditor> getAllUnassignedAuditorsForLog(final TrainingLog log) {
		Validate.notNull(log);
		return trainingLogDao.getAllUnassignedAuditorsToLog(log);
	}

	
	/**
	 * 
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Auditor> getUnassignedAuditorsFor(final TrainingLog log) {
		if(UserUtils.getLoggedUser().isQuasarAdmin()){
			return getAllUnassignedAuditorsForLog(log);
		}
		return getAllUnassignedAuditorsForLog(log);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NandoCode> getAllUnassignedNandoCodesForLog(final TrainingLog log) {
		return trainingLogDao.getAllUnassignedNandoCodesForLog(log);
	}

	
	
}
