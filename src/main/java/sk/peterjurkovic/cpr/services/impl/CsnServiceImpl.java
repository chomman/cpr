package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.CsnDao;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.FileService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;


@Service("csnService")
@Transactional(propagation = Propagation.REQUIRED)
public class CsnServiceImpl implements CsnService{
	
	
	@Autowired
	private CsnDao csnDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	
	@Override
	public void createCsn(Csn csn) {
		csnDao.save(csn);
	}

	@Override
	public void updateCsn(Csn csn) {
		csnDao.update(csn);
	}

	@Override
	public void deleteCsn(Csn csn) {
		fileService.removeDirectory(Constants.CSN_DIR_PREFIX + csn.getId());
		csnDao.remove(csn);
	}

	@Override
	@Transactional(readOnly = true)
	public Csn getById(Long id) {
		return csnDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Csn getByCode(String code) {
		return csnDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Csn> getAll() {
		return csnDao.getAll();
	}

	@Override
	public void saveOrUpdate(Csn csn) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		csn.setCode(CodeUtils.toSeoUrl(csn.getCsnId()));
		if(csn.getId() == null){
			csn.setCreatedBy(user);
			csn.setCreated(new DateTime());
			csnDao.save(csn);
			fileService.createDirectory(Constants.CSN_DIR_PREFIX + csn.getId());
		}else{
			csn.setChangedBy(user);
			csn.setChanged(new DateTime());
			csnDao.update(csn);
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isCsnIdUniqe(Long id, String csnId) {
		return csnDao.isCsnIdUniqe(id, csnId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Csn> getCsnByTerminology(String terminologyTitle) {
		return csnDao.getCsnByTerminology(terminologyTitle);
	}

	

}
