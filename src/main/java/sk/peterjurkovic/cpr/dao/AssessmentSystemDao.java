package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.AssessmentSystem;

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
