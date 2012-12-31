package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.StandardGroupDao;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;


@Service("standardGroupService")
@Transactional(propagation = Propagation.REQUIRED)
public class StandardGroupServiceImpl implements StandardGroupService {
	
	@Autowired
	private StandardGroupDao standardGroupDao;
	@Autowired
	private UserService userService;
	
	@Override
	public void createStandardGroup(StandardGroup standardGroup) {
		standardGroupDao.save(standardGroup);
	}

	@Override
	public void updateStandardGroup(StandardGroup standardGroup) {
		standardGroupDao.update(standardGroup);
	}

	@Override
	public void deleteStandardGroup(StandardGroup standardGroup) {
		standardGroupDao.remove(standardGroup);
	}

	@Override
	@Transactional(readOnly = true)
	public StandardGroup getStandardGroupByid(Long id) {
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
			standardGroup.setCreated(new DateTime());
			standardGroupDao.save(standardGroup);
		}else{
			standardGroup.setChangedBy(user);
			standardGroup.setChanged(new DateTime());
			standardGroupDao.update(standardGroup);
		}
		
	}

}
