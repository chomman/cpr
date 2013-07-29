package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.CsnDao;
import sk.peterjurkovic.cpr.entities.Csn;


@Repository("csnDao")
public class CsnDaoImpl extends BaseDaoImpl<Csn, Long> implements CsnDao{
	
	public CsnDaoImpl(){
		super(Csn.class);
	}
}
