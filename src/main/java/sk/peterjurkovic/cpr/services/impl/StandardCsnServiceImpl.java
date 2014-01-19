package sk.peterjurkovic.cpr.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.StandardCsnDao;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.StandardCsn;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.enums.StandardStatus;
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
			standardCsn.setCreated(new LocalDateTime());
			standardCsnDao.save(standardCsn);
		}else{
			standardCsn.setChangedBy(user);
			standardCsn.setChanged(new LocalDateTime());
			standardCsnDao.update(standardCsn);
		}
		
	}
	
	
	@Override
	public boolean updateReferencedStandard(StandardCsn csn){
		StandardCsn replaced = csn.getReplaceStandardCsn();
		StandardStatus status = csn.getStandardStatus();
		if(status == null || status.equals(StandardStatus.NORMAL)){
			if(replaced != null){
				replaced.setStandardStatus(StandardStatus.NORMAL);
				replaced.setReplaceStandardCsn(null);
				saveOrUpdate(replaced);
				return true;
			}
		}else{
			if(replaced != null && !replaced.getStandardStatus().equals(StandardStatus.CANCELED)){
				replaced.setReplaceStandardCsn(csn);
				replaced.setStandardStatus(StandardStatus.CANCELED);
				saveOrUpdate(replaced);
				return true;
			}
		}
		return false;
	}
	
	

	@Override
	@Transactional(readOnly = true)
	public StandardCsn getByCatalogNo(final String catalogNumber) {
		if(StringUtils.isBlank(catalogNumber)){
			return null;
		}
		return standardCsnDao.getByCatalogNo(catalogNumber);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StandardCsn> autocomplete(final String term) {
		if(StringUtils.isBlank(term)){
			return new ArrayList<StandardCsn>();
		}
		return standardCsnDao.autocomplete(term);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PageDto getPage(int pageNumber, Map<String, Object> criteria){
		return standardCsnDao.getPage(pageNumber, criteria);
	}
	
}
