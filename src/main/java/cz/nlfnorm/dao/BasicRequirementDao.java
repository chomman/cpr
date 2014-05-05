package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.entities.BasicRequirement;


public interface BasicRequirementDao extends BaseDao<BasicRequirement, Long> {
	
	/**
	 * Skontroluje, ci nazov daneho zakladneho pozadavku je jedinecny
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	boolean isNameUniqe(String name, Long id);
	
	/**
	 * Vrati zoznam publikvoanych zakladnych poziadavkov
	 * 
	 * @return List<BasicRequirement>
	 */
	List<BasicRequirement> getBasicRequirementsForPublic();
}
