package cz.nlfnorm.quasar.services.impl;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.dao.AuditorExperienceDao;
import cz.nlfnorm.quasar.entities.AuditorExperience;
import cz.nlfnorm.quasar.services.AuditorExperienceService;
import cz.nlfnorm.utils.UserUtils;

@Transactional
@Service("auditorExperienceService")
public class AuditorExperienceServiceImpl implements AuditorExperienceService {

	@Autowired
	private AuditorExperienceDao auditorExperienceDao;
	
	@Override
	@Transactional(readOnly = true)
	public AuditorExperience getById(Long id) {
		return auditorExperienceDao.getByID(id);
	}

	
	@Override
	public void createOrUpdate(final AuditorExperience auditorExperience) {
		Validate.notNull(auditorExperience);
		final User user = UserUtils.getLoggedUser();
		auditorExperience.setChangedBy(user);
		auditorExperience.setChanged(new LocalDateTime());
		if(auditorExperience.getId() == null){
			auditorExperienceDao.save(auditorExperience);
		}else{
			auditorExperienceDao.update(auditorExperience);
		}
	}

}
