package cz.nlfnorm.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.CountryDao;
import cz.nlfnorm.entities.Country;

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
