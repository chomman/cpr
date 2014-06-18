package cz.nlfnorm.quasar.dao;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.entities.EducationLevel;

/**
 * QUASAR
 * Hibernate implementation of Education DAO interface
 * 
 * @author Peter Jurkovic
 * @date Jun 18, 2014
 */
@Repository("educationLevelDao")
public class EducationLevelDaoImpl extends BaseDaoImpl<EducationLevel, Long> implements EducationLevelDao{

	public EducationLevelDaoImpl(){
		super(EducationLevel.class);
	}
	
}
