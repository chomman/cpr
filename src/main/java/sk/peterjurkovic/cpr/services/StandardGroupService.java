package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.StandardGroup;




public interface StandardGroupService {
	
	void createStandardGroup(StandardGroup standardGroup);
	
	void updateStandardGroup(StandardGroup standardGroup);
	
	void deleteStandardGroup(StandardGroup standardGroup);
	
	StandardGroup getStandardGroupByid(Long id);
	
	StandardGroup getStandardGroupByCode(String code);
	
	List<StandardGroup> getAllStandardGroups();
	
	void saveOrdUpdateStandardGroup(StandardGroup standardGroup);
	
	Long getCountOfStandardsInGroup(StandardGroup standardGroup);
	
	boolean isStandardGroupNameUniqe(String groupName, Long standardGroupId);
}
