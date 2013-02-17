package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.DeclarationOfPerformanceDao;
import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;

@Repository("declarationOfPerformanceDao")
public class DeclarationOfPerformanceDaoImpl 
		extends BaseDaoImpl<DeclarationOfPerformance, Long> 
		implements DeclarationOfPerformanceDao{
	
	
	public DeclarationOfPerformanceDaoImpl(){
		super(DeclarationOfPerformance.class);
	}
}
