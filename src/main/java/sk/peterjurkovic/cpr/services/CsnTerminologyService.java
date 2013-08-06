package sk.peterjurkovic.cpr.services;

import sk.peterjurkovic.cpr.entities.CsnTerminology;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 7, 2013
 *
 */
public interface CsnTerminologyService {

	void createCsnTerminology(CsnTerminology csnTerminology);
	
	void updateCsnTerminology(CsnTerminology csnTerminology);
	
	void deleteCsnTerminology(CsnTerminology csnTerminology);
	
	CsnTerminology getById(Long id);
	
	CsnTerminology getByCode(String code);
	
	void saveOrUpdate(CsnTerminology csnTerminology);
	
}
