package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.StandardCsnDao;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.StandardCsn;
import sk.peterjurkovic.cpr.services.StandardCsnService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("standardCsnService")
@Transactional(propagation = Propagation.REQUIRED)
public class StandardCsnServiceImpl implements StandardCsnService {
	
	@Autowired
	private StandardCsnDao standardCsnDao;
	@Autowired
	private UserService userService;
	
	
	@Override
	public void createCsn(StandardCsn standardCsn) {
		standardCsnDao.save(standardCsn);
	}

	@Override
	public void updateCsn(StandardCsn standardCsn) {
		standardCsnDao.update(standardCsn);
	}

	@Override
	public void deleteCsn(StandardCsn standardCsn) {
		standardCsnDao.remove(standardCsn);
	}

	@Override
	@Transactional(readOnly = true)
	public StandardCsn getCsnById(Long id) {
		return standardCsnDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StandardCsn> getAllCsns() {
		return standardCsnDao.getAll();
	}
	
	@Override
	public void saveOrUpdate(StandardCsn standardCsn) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(standardCsn.getId() == null){
			standardCsn.setCreatedBy(user);
			standardCsn.setCreated(new DateTime());
			standardCsnDao.save(standardCsn);
		}else{
			standardCsn.setChangedBy(user);
			standardCsn.setChanged(new DateTime());
			standardCsnDao.update(standardCsn);
		}
		
	}

	
}
