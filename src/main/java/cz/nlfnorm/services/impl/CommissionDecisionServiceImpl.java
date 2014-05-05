package cz.nlfnorm.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.CommissionDecisionDao;
import cz.nlfnorm.entities.CommissionDecision;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.CommissionDecisionService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.utils.UserUtils;

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
		commissionDecision.setCode(CodeUtils.toSeoUrl(commissionDecision.getCzechLabel()));
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
