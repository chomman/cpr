package sk.peterjurkovic.cpr.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.CommissionDecisionDao;
import sk.peterjurkovic.cpr.entities.CommissionDecision;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.CommissionDecisionService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("commissionDecisionService")
@Transactional(propagation = Propagation.REQUIRED)
public class CommissionDecisionServiceImpl implements CommissionDecisionService {

	@Autowired
	private CommissionDecisionDao commissionDecisionDao;
	@Autowired
	private UserService userService;
	
	
	@Override
	public void create(CommissionDecision commisionDecision) {
		commissionDecisionDao.save(commisionDecision);
	}

	@Override
	public void update(CommissionDecision commisionDecision) {
		commissionDecisionDao.update(commisionDecision);
	}

	@Override
	public void delete(CommissionDecision commisionDecision) {
		commissionDecisionDao.remove(commisionDecision);
	}

	@Override
	public CommissionDecision getById(final Long id) {
		return commissionDecisionDao.getByID(id);
	}

	@Override
	public List<CommissionDecision> getAll() {
		return  commissionDecisionDao.getAll();
	}

	
	@Override
	public void saveOrUpdate(CommissionDecision commissionDecision) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		if(commissionDecision.getId() == null){
			commissionDecision.setCreatedBy(user);
			commissionDecision.setCreated(new LocalDateTime());
			create(commissionDecision);
		}else{
			commissionDecision.setChangedBy(user);
			commissionDecision.setChanged(new LocalDateTime());
			update(commissionDecision);
		}

	}

}
