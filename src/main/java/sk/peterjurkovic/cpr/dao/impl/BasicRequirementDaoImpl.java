package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.BasicRequirementDao;
import sk.peterjurkovic.cpr.entities.BasicRequirement;

/**
 * Trieda implementujuca rozhranie sk.peterjurkovic.cpr.dao.BasicRequirementDao
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("basicRequirement")
public class BasicRequirementDaoImpl extends BaseDaoImpl<BasicRequirement, Long> implements  BasicRequirementDao  {
	
	public BasicRequirementDaoImpl(){
		super(BasicRequirement.class);
	}
	
}


