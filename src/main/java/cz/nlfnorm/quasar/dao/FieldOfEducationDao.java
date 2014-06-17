package cz.nlfnorm.quasar.dao;

import java.util.List;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.quasar.entities.FieldOfEducation;

/**
 * QUASR
 * 
 * @author Peter Jurkovic
 * @date Jun 17, 2014
 */
public interface FieldOfEducationDao extends BaseDao<FieldOfEducation, Long>{

	List<FieldOfEducation> getForActiveMedicalDevices();
	
	List<FieldOfEducation> getForNonActiveMedicalDevices();
}
