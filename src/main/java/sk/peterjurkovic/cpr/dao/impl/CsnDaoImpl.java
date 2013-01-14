package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.CsnDao;
import sk.peterjurkovic.cpr.entities.Csn;

/**
 * Implementacia rozhrania sk.peterjurkovic.cpr.dao.impl.StandardCsnDao
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("standardCsnDao")
public class CsnDaoImpl extends BaseDaoImpl<Csn, Long> implements CsnDao{
	
	
	public CsnDaoImpl(){
		super(Csn.class);
	}
	
}
