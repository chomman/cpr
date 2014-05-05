package cz.nlfnorm.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.AssessmentSystemDao;
import cz.nlfnorm.entities.AssessmentSystem;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.AssessmentSystemService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.UserUtils;


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
			assessmentSystem.setCreated(new LocalDateTime());
			assessmentSystemDao.save(assessmentSystem);
		}else{
			assessmentSystem.setChangedBy(user);
			assessmentSystem.setChanged(new LocalDateTime());
			assessmentSystemDao.update(assessmentSystem);
		}
	}


	@Override
	@Transactional(readOnly = true)
	public List<AssessmentSystem> getAssessmentSystemsForPublic() {
		return assessmentSystemDao.getAssessmentSystemsForPublic();
	}
	
	
}
