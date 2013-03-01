package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;

public interface DeclarationOfPerformanceService {
	
	void createDoP(DeclarationOfPerformance dop);
	
	void updateDop(DeclarationOfPerformance dop);
	
	void deleteDop(DeclarationOfPerformance dop);
	
	DeclarationOfPerformance getDopById(Long id);
	
	DeclarationOfPerformance getDoPByCode(String code);
	
	List<DeclarationOfPerformance> getAll();
	
	DeclarationOfPerformance getByToken(String token);
	
	void deleteEssentialCharacteristicByDopId(Long id);
	
	Long getCountOfDop(final Map<String, Object> criteria);
	
	List<DeclarationOfPerformance> getDopPage(int pageNumber,Map<String, Object> criteria);
}
