package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.AssessmentSystem;


public interface AssessmentSystemService {
	
	
	List<AssessmentSystem> getAllAssessmentSystems();
	
	void create(AssessmentSystem assessmentSystem);
	
	void update(AssessmentSystem assessmentSystem);
	
	void remove(AssessmentSystem assessmentSystem);
	
	AssessmentSystem getAssessmentSystemById(Long id);
	
	void saveOrUpdateAssessmentSystem(AssessmentSystem assessmentSystem);
}
