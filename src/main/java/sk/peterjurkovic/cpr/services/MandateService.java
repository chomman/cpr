package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Mandate;

public interface MandateService {
	
	
	void createMandate(Mandate mandate);
	
	void updateMandate(Mandate mandate);
	
	void deleteMandate(Mandate mandate);
	
	List<Mandate> getAllMandates();
	
	Mandate getMandateById(Long id);
	
	Mandate getMandateByCode(String code);
	
	List<Mandate> getMandatePage(int pageNumber);
	
	void saveOrUpdateMandate(Mandate mandate);
	
	Long getCountOfMandates();
}
