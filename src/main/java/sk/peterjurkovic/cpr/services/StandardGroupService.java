package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardGroup;
/**
 * 
 * @author peto
 *
 */
public interface StandardGroupService {
	
	void createStandardGroup(StandardGroup standardGroup);
	
	void updateStandardGroup(StandardGroup standardGroup);
	
	void deleteStandardGroup(StandardGroup standardGroup);
	
	void mergeStandardGroup(StandardGroup standardGroup);
	
	StandardGroup getStandardGroupByid(Long id);
	
	StandardGroup getStandardGroupByCode(String code);
	
	List<StandardGroup> getAllStandardGroups();
	
	void saveOrdUpdateStandardGroup(StandardGroup standardGroup);
	
	Long getCountOfStandardsInGroup(StandardGroup standardGroup);
	
	boolean isStandardGroupNameUniqe(String groupName, Long standardGroupId);
	
	List<StandardGroup> getStandardGroupsForPublic();
	
	/**
	 * Metoda vrati vsetky skupiny vyrobkov, okrem skupin priradenych k danej norme.
	 * Tz. ak bude k danej norme priradena skupina 3, budu vratene vsetky skupiny okrem 3
	 * 
	 * @param standard
	 * @return 
	 */
	List<StandardGroup> getFiltredStandardGroups(Standard standard);
	
	void flush();
	
	
	StandardGroup findByMandateAndCommissionDecision(String mandateName, String cdName);
	
	
}
