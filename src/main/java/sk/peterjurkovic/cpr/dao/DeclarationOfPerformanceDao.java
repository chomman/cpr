package sk.peterjurkovic.cpr.dao;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;

public interface DeclarationOfPerformanceDao  extends BaseDao<DeclarationOfPerformance, Long> {
	
	DeclarationOfPerformance getByToken(String token);
	
}
