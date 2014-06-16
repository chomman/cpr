package cz.nlfnorm.quasar.services.impl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.quasar.dao.ExperienceDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.Experience;
import cz.nlfnorm.quasar.services.ExperienceService;

@Transactional(readOnly = true)
@Service("experienceService")
public class ExperienceServiceImpl implements ExperienceService {
	
	@Autowired
	private ExperienceDao experienceDao;
	
	@Override
	public Experience getById(final Long id) {
		return experienceDao.getByID(id);
	}

	@Override
	public List<Experience> getAll() {
		return experienceDao.getAll();
	}

	@Override
	public List<Experience> getAllExcept(final Auditor auditor) {
		Validate.notNull(auditor);
		return experienceDao.getAllUnassignetExperiences(auditor.getId());
	}

}
