package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;

/**
 * DAO rozhranie pre manipulaciu s poziadavkami 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface RequirementDao extends BaseDao<Requirement, Long>{
	
	
	/**
	 * Vrati zoznam poziadavko na pre danu krainu a normu
	 * 
	 * @param Country krajina
	 * @param Standar dana norma pre kotru maju byt vratene poziadavky
	 * @return List<Requirement> zoznam poziadavkov
	 */
	List<Requirement> getRequirementsByCountryAndStandard(Country country, Standard standard);
}


