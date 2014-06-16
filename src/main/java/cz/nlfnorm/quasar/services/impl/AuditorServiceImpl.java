package cz.nlfnorm.quasar.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.AuthorityDao;
import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.dao.AuditorDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.services.AuditorEacCodeService;
import cz.nlfnorm.quasar.services.AuditorNandoCodeService;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.UserUtils;

/**
 * Implementation of Auditor service layer
 *  
 * @author Peter Jurkovic
 * @date Jun 12, 2014
 */
@Service("auditorService")
@Transactional(propagation = Propagation.REQUIRED)
public class AuditorServiceImpl implements AuditorService{
	
	@Autowired
	private AuditorDao auditorDao;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private AuditorEacCodeService auditorEacCodeService;
	@Autowired
	private AuditorNandoCodeService auditorNandoCodeService;
	
	@Override
	public void create(final Auditor auditor) {
		auditorDao.save(auditor);
	}

	@Override
	public void update(final Auditor auditor) {
		auditorDao.update(auditor);	
	}

	@Override
	public void delete(final Auditor auditor) {
		auditorDao.remove(auditor);
	}

	@Override
	@Transactional(readOnly = true)
	public Auditor getById(final Long id) {
		return auditorDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Auditor> getAll() {
		return auditorDao.getAll();
	}
	
	@Override
	public Long createAuditor(final Auditor auditor,final String password){
		userService.setUserPassword(auditor, password);
		createOrUpdate(auditor);
		auditor.getAuthoritySet().add(authorityDao.getByCode(Authority.ROLE_AUDITOR));
		auditor.getAuthoritySet().add(authorityDao.getByCode(Authority.ROLE_ADMIN));
		update(auditor);
		return auditor.getId();
	}

	@Override
	public void createOrUpdate(final Auditor auditor) {
		final User user = UserUtils.getLoggedUser();
		auditor.setChanged(new LocalDateTime());
		auditor.setChangedBy(user);
		if(auditor.getId() == null){
			auditor.setCreated(auditor.getChanged());
			auditor.setCreatedBy(user);
			create(auditor);
			syncCodes(auditor);
		}else{
			update(auditor);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isItcIdUniqe(final Integer id, final Long auditorId) {
		return auditorDao.isItcIdUniqe(id, auditorId);
	}

	private void syncCodes(final Auditor auditor){
		auditorEacCodeService.syncAuditorEacCodes(auditor);
		auditorNandoCodeService.syncAuditorNandoCodes(auditor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AutocompleteDto> autocomplete(String term, Boolean enabledObly, Boolean adminsOnly) {
		if(StringUtils.isBlank(term)){
			return new ArrayList<>();
		}
		return auditorDao.autocomplete(term, enabledObly, adminsOnly);
	}
}
