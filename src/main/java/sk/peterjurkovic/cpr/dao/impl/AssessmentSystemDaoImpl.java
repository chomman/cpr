package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.AssessmentSystemDao;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;

/**
 * Implementácia datovej vrstvy systemov hodnotenia
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("assessmentSystem")
public class AssessmentSystemDaoImpl extends BaseDaoImpl<AssessmentSystem, Long> implements AssessmentSystemDao {

	public AssessmentSystemDaoImpl() {
		super(AssessmentSystem.class);
	
	}

}
