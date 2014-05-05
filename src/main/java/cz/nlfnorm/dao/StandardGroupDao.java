package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.entities.StandardGroup;

/**
 * Rozhranie datovej vrstvy entity sk.peterjurkovic.cpr.entities.StandardGroup
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface StandardGroupDao extends BaseDao<StandardGroup, Long>{

	/**
	 * Vrati pocet evidovanych noriem v danej skupine vyrobkov
	 * 
	 * @param StandardForm dana skupina vyrobkov
	 * @return Long pocet noriem, nachadzajucich sa v danej skupine
	 */
	Long getCoutOfStandardInGroup(StandardGroup standardGroup);
	
	
	/**
	 * Skontroluje ci je nazov skupiny jedinecny v ramci systemu
	 * 
	 * @param String kod skupiny code (seo url)
	 * @param Long ID skupiny
	 * @return TRUE, ak je jedinecna, inak FALSE
	 */
	boolean isGroupNameUniqe(String code, Long id);
	
	
	/**
	 * Vrati zoznam publikovanych skupin vyrobkov
	 * 
	 * @return List<StandardGroup> skupiny vyrobkov
	 */
	List<StandardGroup> getStandardGroupsForPublic();
	
	
	StandardGroup findByMandateAndCommissionDecision(String mandateName, String cdName);
}
