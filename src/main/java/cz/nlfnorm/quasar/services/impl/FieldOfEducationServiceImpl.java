package cz.nlfnorm.quasar.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.quasar.dao.FieldOfEducationDao;
import cz.nlfnorm.quasar.entities.FieldOfEducation;
import cz.nlfnorm.quasar.services.FieldOfEducationService;

/**
 * Quasar
 * 
 * @author Peter Jurkovic
 * @date Jun 17, 2014
 */
@Transactional(readOnly = true)
@Service("fieldOfEducationService")
public class FieldOfEducationServiceImpl implements FieldOfEducationService {

	@Autowired
	private FieldOfEducationDao fieldOfEducationDao;
	
	@Override
	public FieldOfEducation getById(Long id) {
		return fieldOfEducationDao.getByID(id);
	}

	@Override
	public List<FieldOfEducation> getAll() {
		return fieldOfEducationDao.getAll();
	}

	@Override
	public List<FieldOfEducation> getForActiveMedicalDevices() {
		return fieldOfEducationDao.getForActiveMedicalDevices();
	}

	@Override
	public List<FieldOfEducation> getForNonActiveMedicalDevices() {
		return fieldOfEducationDao.getForNonActiveMedicalDevices();
	}

	@Override
	@Transactional(readOnly = false)
	public void createOrUpdate(final FieldOfEducation fieldOfEducation) {
		if(fieldOfEducation.getId() == null){
			fieldOfEducationDao.save(fieldOfEducation);
		}else{
			fieldOfEducationDao.update(fieldOfEducation);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void remove(final FieldOfEducation fieldOfEducation) {
		fieldOfEducationDao.remove(fieldOfEducation);
	}

}
