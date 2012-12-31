package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.CountryDao;
import sk.peterjurkovic.cpr.entities.Country;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("countryDao")
public class CountryDaoImpl extends BaseDaoImpl<Country, Long> implements CountryDao {
	
	private CountryDaoImpl(){
		super(Country.class);
	}
	
}
