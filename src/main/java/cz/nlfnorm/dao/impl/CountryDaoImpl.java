package cz.nlfnorm.dao.impl;

import java.util.List;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Country> getAllOrdred() {
		 return createQuery("from Country c order by c.countryName")
		.setCacheable(false).list();
	}
	
}
