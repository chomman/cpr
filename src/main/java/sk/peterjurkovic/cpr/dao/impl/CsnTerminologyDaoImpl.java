package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.CsnTerminologyDao;
import sk.peterjurkovic.cpr.entities.CsnTerminology;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 7, 2013
 *
 */
@Repository("csnTerminologyDao")
public class CsnTerminologyDaoImpl extends BaseDaoImpl<CsnTerminology, Long> implements CsnTerminologyDao{
	
	public CsnTerminologyDaoImpl(){
		super(CsnTerminology.class);
	}
	
}
