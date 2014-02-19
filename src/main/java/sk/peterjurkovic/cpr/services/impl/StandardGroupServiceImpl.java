package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.StandardGroupDao;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;


@Service("standardGroupService")
@Transactional(propagation = Propagation.REQUIRED)
public class StandardGroupServiceImpl implements StandardGroupService {
	
	@Autowired
	private StandardGroupDao standardGroupDao;
	@Autowired
	private UserService userService;
	
	@Override
	public void createStandardGroup(final StandardGroup standardGroup) {
		standardGroupDao.save(standardGroup);
	}

	@Override
	public void updateStandardGroup(final StandardGroup standardGroup) {
		standardGroupDao.update(standardGroup);
	}

	@Override
	public void deleteStandardGroup(final StandardGroup standardGroup) {
		standardGroupDao.remove(standardGroup);
	}

	@Override
	@Transactional(readOnly = true)
	public StandardGroup getStandardGroupByid(final Long id) {
		return standardGroupDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public StandardGroup getStandardGroupByCode(String code) {
		return standardGroupDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StandardGroup> getAllStandardGroups() {
		return standardGroupDao.getAll();
	}

	
	@Override
	public void saveOrdUpdateStandardGroup(StandardGroup standardGroup) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(standardGroup.getId() == null){
			standardGroup.setCreatedBy(user);
			standardGroup.setCreated(new LocalDateTime());
			standardGroupDao.save(standardGroup);
		}else{
			standardGroup.setChangedBy(user);
			standardGroup.setChanged(new LocalDateTime());
			standardGroupDao.update(standardGroup);
		}
		
	}

	
	@Override
	@Transactional(readOnly = true)
	public Long getCountOfStandardsInGroup(final StandardGroup standardGroup) {
		return standardGroupDao.getCoutOfStandardInGroup(standardGroup);
	}

	
	@Override
	@Transactional(readOnly = true)
	public boolean isStandardGroupNameUniqe(final String groupName,final Long standardGroupId) {
		return standardGroupDao.isGroupNameUniqe(CodeUtils.toSeoUrl(groupName), standardGroupId);
	}

	@Override
	public List<StandardGroup> getStandardGroupsForPublic() {
		return standardGroupDao.getStandardGroupsForPublic();
	}

	@Override
	public void mergeStandardGroup(StandardGroup standardGroup) {
		standardGroup.setChanged(new LocalDateTime());
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		standardGroup.setChangedBy(user);
		standardGroupDao.merge(standardGroup);
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<StandardGroup> getFiltredStandardGroups(final Standard standard) {
		List<StandardGroup> allStandardGroups = getAllStandardGroups();
		allStandardGroups.removeAll(standard.getStandardGroups());
		return allStandardGroups;
	}
	

	@Override
	public void flush() {
		standardGroupDao.flush();
	}
	
	
	@Transactional(readOnly = true)
	public StandardGroup findByMandateAndCommissionDecision(final String mandateName,final String cdName){ 
		return standardGroupDao.findByMandateAndCommissionDecision(mandateName, cdName);
	}
}
