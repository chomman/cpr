package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.FieldOfEducationDao;
import cz.nlfnorm.quasar.entities.FieldOfEducation;

/**
 * QUASAR Component
 * 
 * @author Peter Jurkovic
 * @date Jun 17, 2014
 */

@Repository("fieldOfEducationDao")
public class FieldOfEducationDaoImpl extends BaseDaoImpl<FieldOfEducation, Long> implements FieldOfEducationDao {
	
	public FieldOfEducationDaoImpl(){
		super(FieldOfEducation.class);
	}

	@Override
	public List<FieldOfEducation> getForActiveMedicalDevices() {
		return getFor(true);
	}

	@Override
	public List<FieldOfEducation> getForNonActiveMedicalDevices() {
		return getFor(false);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<FieldOfEducation> getFor(boolean activeMedicalDevices){
		String hql = " from FieldOfEducation f where ";
		if(activeMedicalDevices){
			hql += " f.forActiveMedicalDevices = true "; 
		}else{
			hql += " f.forNonActiveMedicalDevices = true ";
		}
		hql += " order by f.name ";
		return createQuery(hql).setCacheable(false).list();
	}
}
