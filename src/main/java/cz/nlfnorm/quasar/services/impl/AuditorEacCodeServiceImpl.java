package cz.nlfnorm.quasar.services.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.dao.AuditorEacCodeDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorEacCode;
import cz.nlfnorm.quasar.entities.EacCode;
import cz.nlfnorm.quasar.services.AuditorEacCodeService;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.services.EacCodeService;
import cz.nlfnorm.utils.UserUtils;

@Service("auditorEacCodeService")
@Transactional(propagation = Propagation.REQUIRED)
public class AuditorEacCodeServiceImpl implements AuditorEacCodeService{
	
	@Autowired
	private AuditorEacCodeDao auditorEacCodeDao;
	@Autowired
	private EacCodeService eacCodeService;
	@Autowired
	private AuditorService auditorService;
	
	@Override
	public void create(final AuditorEacCode auditorEacCode) {
		auditorEacCodeDao.save(auditorEacCode);
	}

	@Override
	public void update(final AuditorEacCode auditorEacCode) {
		auditorEacCodeDao.update(auditorEacCode);
	}

	@Override
	public void delete(final AuditorEacCode auditorEacCode) {
		auditorEacCodeDao.remove(auditorEacCode);
	}

	@Override
	@Transactional(readOnly = true)
	public AuditorEacCode getById(final Long id) {
		return auditorEacCodeDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuditorEacCode> getAllAuditorEacCodes(final Auditor auditor) {
		return auditorEacCodeDao.getAllAuditorEacCodes(auditor);
	}

	
	@Override
	public void syncAuditorEacCodes(final Auditor auditor) {
			Validate.notNull(auditor);
			final List<EacCode> nonAssociatedCodeList = eacCodeService.getAllNonAssociatedAuditorsEacCodes(auditor);
			final User user = UserUtils.getLoggedUser();
			if(CollectionUtils.isNotEmpty(nonAssociatedCodeList)){
				for(EacCode eacCode : nonAssociatedCodeList){
					AuditorEacCode auditorEacCode = new AuditorEacCode(auditor, eacCode);
					auditorEacCode.setChangedBy(user);
					create(auditorEacCode);
					auditor.getAuditorsEacCodes().add(auditorEacCode);
				}
			}
	}

	
	@Override
	@Transactional(readOnly = true)
	public AuditorEacCode getByEacCode(final String code, final Long auditorId) {
		if(StringUtils.isBlank(code) || auditorId == null){
			return null;
		}
		return auditorEacCodeDao.getByEacCode(code, auditorId);
	}

	@Override
	public void updateAndSetChanged(final AuditorEacCode form) {
		AuditorEacCode auditorEacCode  = getById(form.getId());
		Validate.notNull(form);
		auditorEacCode.setItcApproved(form.isItcApproved());
		auditorEacCode.setNotifiedBody(form.getNotifiedBody());
		auditorEacCode.setNumberOfIso13485Audits(form.getNumberOfIso13485Audits());
		auditorEacCode.setNumberOfNbAudits(form.getNumberOfNbAudits());
		auditorEacCode.setChanged(new LocalDateTime());
		auditorEacCode.setChangedBy(UserUtils.getLoggedUser());
		auditorEacCode.setRefused(form.isRefused());
		auditorEacCode.setReasonOfRefusal(form.getReasonOfRefusal());
		update(auditorEacCode);
	}

	public void syncEacCodes(){
		final List<Auditor> auditorList = auditorService.getAll();
		for(Auditor auditor : auditorList){
			syncAuditorEacCodes(auditor);
		}
	}
}
