package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.entities.Country;


/**
 * Rozhranie entitz sk.peterjurkovic.cpr.entities.Country 
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */
public interface CountryDao extends BaseDao<Country, Long> {
	
	List<Country> getAllOrdred();
	
}
