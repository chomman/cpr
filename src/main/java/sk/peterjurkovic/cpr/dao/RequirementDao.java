package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;

public interface RequirementDao extends BaseDao<Requirement, Long>{
	
	List<Requirement> getRequirementsByCountryAndStandard(Country country, Standard standard);
}


