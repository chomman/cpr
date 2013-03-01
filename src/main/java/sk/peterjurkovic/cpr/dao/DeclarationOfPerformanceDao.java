package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;

public interface DeclarationOfPerformanceDao  extends BaseDao<DeclarationOfPerformance, Long> {
	
	DeclarationOfPerformance getByToken(String token);
	
	void deleteEssentialCharacteristicByDopId(Long id);
	
	List<DeclarationOfPerformance> getDopPage(int pageNumber, Map<String, Object> criteria);
	
	Long getCountOfDop(Map<String, Object> criteria);
	
}
