package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.MandateDao;
import sk.peterjurkovic.cpr.entities.Mandate;


@Repository("mandateDao")
public class MandateDaoImpl extends BaseDaoImpl<Mandate, Long> implements MandateDao {
	
	public MandateDaoImpl(){
		super(Mandate.class);
	}
}
