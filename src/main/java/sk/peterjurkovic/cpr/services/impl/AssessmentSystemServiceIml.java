package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.AssessmentSystemDao;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;


@Service("assessmentSystemService")
@Transactional(propagation = Propagation.REQUIRED)
public class AssessmentSystemServiceIml implements AssessmentSystemService {
	
	@Autowired
	private AssessmentSystemDao assessmentSystemDao;
	@Autowired
	private UserService userService;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<AssessmentSystem> getAllAssessmentSystems() {
		return assessmentSystemDao.getAll();
	}
	
	
	@Override
	public void create(AssessmentSystem assessmentSystem) {
		assessmentSystemDao.save(assessmentSystem);
	}

	@Override
	public void update(AssessmentSystem assessmentSystem) {
		assessmentSystemDao.update(assessmentSystem);
	}

	@Override
	public void remove(AssessmentSystem assessmentSystem) {
		assessmentSystemDao.remove(assessmentSystem);
	}

	@Override
	@Transactional(readOnly = true)
	public AssessmentSystem getAssessmentSystemById(Long id) {
		return assessmentSystemDao.getByID(id);
	}

	@Override
	public void saveOrUpdateAssessmentSystem(AssessmentSystem assessmentSystem) {
		
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(assessmentSystem.getId() == null || assessmentSystem.getId() == 0){
			assessmentSystem.setCreatedBy(user);
			assessmentSystem.setCreated(new DateTime());
			assessmentSystemDao.save(assessmentSystem);
		}else{
			assessmentSystem.setChangedBy(user);
			assessmentSystem.setChanged(new DateTime());
			assessmentSystemDao.update(assessmentSystem);
		}
	}


	@Override
	@Transactional(readOnly = true)
	public List<AssessmentSystem> getAssessmentSystemsForPublic() {
		return assessmentSystemDao.getAssessmentSystemsForPublic();
	}
	
	
}
