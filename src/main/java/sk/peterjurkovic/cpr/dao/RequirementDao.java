package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;

public interface RequirementDao extends BaseDao<Requirement, Long>{
	
	
	/**
	 * Vrati poziadavky na zaklade danej krajiny a normy
	 * 
	 * @param country
	 * @param standard
	 * @return List<Requirement> zoznam parametrov/poziadavkov danej krajiny a normy
	 */
	List<Requirement> getRequirementsByCountryAndStandard(Country country, Standard standard);
}


