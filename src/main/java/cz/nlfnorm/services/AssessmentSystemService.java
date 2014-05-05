package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.AssessmentSystem;


/**
 * Rozhranie pre pracu so systemom posudzovania nemennosti parametrov
 * 
 * @author Peter Jurkovic
 *
 */
public interface AssessmentSystemService {
	
	
	/**
	 * Vrati vsetky systemy posudzovania nemennosti parametrov evidovane v systeme
	 * 
	 * @return zoznam systemov posudzovania nemennosti parametrov
	 */
	List<AssessmentSystem> getAllAssessmentSystems();
	
	
	/**
	 * 
	 * Vytvori novy system posudzovania nemennosti parametrov
	 * 
	 * @param assessmentSystem
	 */
	void create(AssessmentSystem assessmentSystem);
	
	
	/**
	 * Aktualizuje system posudzovania nemennosti parametrov
	 * 
	 * @param assessmentSystem
	 */
	void update(AssessmentSystem assessmentSystem);
	
	
	/**
	 * Odstrani system posudzovania nemennosti parametrov
	 * 
	 * @param assessmentSystem
	 */
	void remove(AssessmentSystem assessmentSystem);
	
	
	/**
	 * Vrati system posudzovania nemennosti parametrov na zaklade daneho identifikatora
	 * 
	 * 
	 * @param id identifikator daneho systemu posudzovania nemennosti parametrov
	 * @return system posudzovania nemennosti parametrov
	 */
	AssessmentSystem getAssessmentSystemById(Long id);
	
	
	/**
	 * Aktualizuje, alebo vytvory system posudzovania nemennosti parametrov. V pripade ak je ID 
	 * systemu posudzovania nemennosti parametrov NULL, jedna jedna sa o kaciu vytvorenia, v inom pripade ho aktualizuje
	 * 
	 * @param assessmentSystem system posudzovania nemennosti parametrov
	 */
	void saveOrUpdateAssessmentSystem(AssessmentSystem assessmentSystem);
	
	
	
	/**
	 * Vrati vsetky publikovane systemy nemennosti parametrov
	 * 
	 * @return zoznam publikovanych systemov posudzovania nemennosti parametrov
	 */
	List<AssessmentSystem> getAssessmentSystemsForPublic();
}
