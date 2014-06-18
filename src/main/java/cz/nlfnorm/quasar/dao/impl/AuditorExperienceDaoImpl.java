package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.AuditorExperienceDao;
import cz.nlfnorm.quasar.entities.AuditorExperience;

/**
 * QUASAR
 * 
 * Hibernate implementation of Auditor experience DAO interface
 * 
 * @author Peter Jurkovic
 * @date Jun 18, 2014
 */
@Repository("auditorExperienceDao")
public class AuditorExperienceDaoImpl extends BaseDaoImpl<AuditorExperience, Long> 
						implements AuditorExperienceDao{

	
	public AuditorExperienceDaoImpl(){
		super(AuditorExperience.class);
	}
	
}
