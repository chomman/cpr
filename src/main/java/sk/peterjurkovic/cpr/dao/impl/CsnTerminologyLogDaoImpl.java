package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.CsnTerminologyLogDao;
import sk.peterjurkovic.cpr.entities.CsnTerminologyLog;


@Repository("csnTerminologyLogDao")
public class CsnTerminologyLogDaoImpl extends BaseDaoImpl<CsnTerminologyLog, Long> implements CsnTerminologyLogDao{
	
	public CsnTerminologyLogDaoImpl(){
		super(CsnTerminologyLog.class);
	}
	
}
