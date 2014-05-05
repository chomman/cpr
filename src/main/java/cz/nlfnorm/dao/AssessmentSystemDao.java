package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.entities.AssessmentSystem;

/**
 * Rozhranie pre prácu s entitou sk.peterjurkovic.cpr.AssessmentSystem
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface AssessmentSystemDao extends BaseDao<AssessmentSystem, Long> {
	
	/**
	 * Vrati zoznam publikovanych systemov posudzovania nemennosti parametrov, 
	 * @return List<AssessmentSystem> 
	 */
	List<AssessmentSystem> getAssessmentSystemsForPublic();
	
}
