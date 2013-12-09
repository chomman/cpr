package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.StandardGroupMandateDao;
import sk.peterjurkovic.cpr.entities.StandardGroupMandate;

/**
 * Implementacia rozhrania pre pracu s asociacnou entitiou @see StandardGroupMandate
 * 
 * @author Peter Jurkovič
 * @date Dec 9, 2013
 *
 */
@Repository("standardGroupMandateDao")
public class StandardGroupMandateDaoImpl extends BaseDaoImpl<StandardGroupMandate, Long> implements StandardGroupMandateDao {
	
	public StandardGroupMandateDaoImpl(){
		super(StandardGroupMandate.class);
	}
	
}
