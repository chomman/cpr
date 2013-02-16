package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;

public interface DeclarationOfPerformanceService {
	
	void createDoP(DeclarationOfPerformance dop);
	
	void updateDop(DeclarationOfPerformance dop);
	
	void deleteDop(DeclarationOfPerformance dop);
	
	DeclarationOfPerformance getDopById(Long id);
	
	DeclarationOfPerformance getDoPByCode(String code);
	
	List<DeclarationOfPerformance> getAll();
	
	
}
