package cz.nlfnorm.quasar.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.quasar.dao.EducationLevelDao;
import cz.nlfnorm.quasar.entities.EducationLevel;
import cz.nlfnorm.quasar.services.EducationLevelService;

/**
 * QUASAR Component
 * 
 * Implementation of Education level interface
 * 
 * @author Peter Jurkovic
 * @date Jun 18, 2014
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
@Service("educationLevelService")
public class EducationLevelServiceImpl implements EducationLevelService {

	@Autowired
	private EducationLevelDao educationLevelDao;
	
	@Override
	public EducationLevel getById(final Long id) {
		return educationLevelDao.getByID(id);
	}

	@Override
	public List<EducationLevel> getAll() {
		return educationLevelDao.getAll();
	}

}
