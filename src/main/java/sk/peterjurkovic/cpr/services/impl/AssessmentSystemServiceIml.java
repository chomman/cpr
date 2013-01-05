package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.AssessmentSystemDao;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;


@Service("assessmentSystemService")
@Transactional(propagation = Propagation.REQUIRED)
public class AssessmentSystemServiceIml implements AssessmentSystemService {
	
	@Autowired
	private AssessmentSystemDao assessmentSystemDao;
	
	
	
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
	public void getAssessmentSystemById(Long id) {
		assessmentSystemDao.getByID(id);
	}

}