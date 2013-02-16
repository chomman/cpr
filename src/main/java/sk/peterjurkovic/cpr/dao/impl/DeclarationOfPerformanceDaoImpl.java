package sk.peterjurkovic.cpr.dao.impl;

import sk.peterjurkovic.cpr.dao.DeclarationOfPerformanceDao;
import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;

public class DeclarationOfPerformanceDaoImpl 
		extends BaseDaoImpl<DeclarationOfPerformance, Long> 
		implements DeclarationOfPerformanceDao{
	
	
	public DeclarationOfPerformanceDaoImpl(){
		super(DeclarationOfPerformance.class);
	}
}
