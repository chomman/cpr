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
import cz.nlfnorm.quasar.dao.AuditorNandoCodeDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorNandoCode;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.services.AuditorNandoCodeService;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.services.NandoCodeService;
import cz.nlfnorm.utils.UserUtils;

/**
 * 
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
@Service("auditorNandoCodeService")
@Transactional(propagation = Propagation.REQUIRED)
public class AuditorNandoCodeServiceImpl implements AuditorNandoCodeService{

	@Autowired
	private AuditorNandoCodeDao auditorNandoCodeDao;
	@Autowired
	private NandoCodeService nandoCodeService;
	@Autowired
	private AuditorService auditorService;
	
	@Override
	public void create(final AuditorNandoCode AuditorNandoCode) {
		auditorNandoCodeDao.save(AuditorNandoCode);	
	}

	@Override
	public void update(final AuditorNandoCode AuditorNandoCode) {
		auditorNandoCodeDao.update(AuditorNandoCode);	
	}

	@Override
	public void delete(final AuditorNandoCode AuditorNandoCode) {
		auditorNandoCodeDao.remove(AuditorNandoCode);	
	}

	@Override
	@Transactional(readOnly = true)
	public AuditorNandoCode getById(final Long id) {
		return auditorNandoCodeDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuditorNandoCode> getAllAuditorNandoCodes(final Auditor auditor) {
		return auditorNandoCodeDao.getAllAuditorNandoCodes(auditor); 
	}

	@Override
	public void syncAuditorNandoCodes(Auditor auditor) {
		Validate.notNull(auditor);
		final List<NandoCode> codeList = nandoCodeService.getAllNonAssociatedAuditorsNandoCodes(auditor);
		final User user = UserUtils.getLoggedUser();
		if(CollectionUtils.isNotEmpty(codeList)){
			for(NandoCode code : codeList){
				AuditorNandoCode auditorNandoCode = new AuditorNandoCode(auditor, code);
				auditorNandoCode.setChangedBy(user);
				create(auditorNandoCode);
				auditor.getAuditorsNandoCodes().add(auditorNandoCode);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AuditorNandoCode getByNandoCode(final String code, final Long auditorId) {
		if(StringUtils.isBlank(code) || auditorId == null){
			return null;
		}
		return auditorNandoCodeDao.getByNandoCode(code, auditorId);
	}

	@Override
	public void createOrUpdate(final AuditorNandoCode auditorNandoCode) {
		Validate.notNull(auditorNandoCode);
		final User user = UserUtils.getLoggedUser();
		auditorNandoCode.setChangedBy(user);
		auditorNandoCode.setChanged(new LocalDateTime());
		if(auditorNandoCode.getId() == null){
			create(auditorNandoCode);
		}else{
			update(auditorNandoCode);
		}
	}

	@Override
	public void syncNandoCodes() {
		List<Auditor> auditorList = auditorService.getAll();
		for(final Auditor auditor : auditorList){
			syncAuditorNandoCodes(auditor);
		}
	}

}
