package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.CsnDao;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("csnService")
@Transactional(propagation = Propagation.REQUIRED)
public class CsnServiceImpl implements CsnService {
	
	@Autowired
	private CsnDao CsnDao;
	@Autowired
	private UserService userService;
	
	@Override
	public void createCsn(Csn Csn) {
		CsnDao.save(Csn);
	}

	@Override
	public void updateCsn(Csn Csn) {
		CsnDao.update(Csn);
	}

	@Override
	public void deleteCsn(Csn Csn) {
		CsnDao.remove(Csn);
	}

	@Override
	@Transactional(readOnly = true)
	public Csn getCsnById(Long id) {
		return CsnDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Csn> getAllCsns() {
		return CsnDao.getAll();
	}
	
	@Override
	public void saveOrUpdate(Csn Csn) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(Csn.getId() == null){
			Csn.setCreatedBy(user);
			Csn.setCreated(new DateTime());
			CsnDao.save(Csn);
		}else{
			Csn.setChangedBy(user);
			Csn.setChanged(new DateTime());
			CsnDao.update(Csn);
		}
		
	}

	
}
