package sk.peterjurkovic.cpr.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.CsnTerminologyDao;
import sk.peterjurkovic.cpr.dto.CsnTerminologyDto;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 7, 2013
 *
 */
@Service("csnTerminologyService")
@Transactional(propagation = Propagation.REQUIRED)
public class CsnTerminologyServiceImpl implements CsnTerminologyService{

	@Autowired
	private CsnTerminologyDao csnTerminologyDao;
	@Autowired
	private UserService userService;
	
	@Override
	public void createCsnTerminology(CsnTerminology csnTerminology) {
		csnTerminologyDao.save(csnTerminology);
	}

	@Override
	public void updateCsnTerminology(CsnTerminology csnTerminology) {
		csnTerminologyDao.update(csnTerminology);
	}

	@Override
	public void deleteCsnTerminology(CsnTerminology csnTerminology) {
		csnTerminologyDao.remove(csnTerminology);
	}

	@Override
	@Transactional(readOnly = true)
	public CsnTerminology getById(Long id) {
		return csnTerminologyDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public CsnTerminology getByCode(String code) {
		return csnTerminologyDao.getByCode(code);
	}
	
	@Override
	public Long saveOrUpdate(CsnTerminology csnTerminology){
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		if(csnTerminology.getId() == null){
			csnTerminology.setCreatedBy(user);
			csnTerminology.setCreated(new LocalDateTime());
			csnTerminologyDao.save(csnTerminology);
		}else{
			csnTerminology.setChangedBy(user);
			csnTerminology.setChanged(new LocalDateTime());
			csnTerminologyDao.update(csnTerminology);
		}
		return csnTerminology.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isTitleUniqe(Long csnId, Long terminologyId, String title) {
		return csnTerminologyDao.isTitleUniqe(csnId, terminologyId, CodeUtils.toSeoUrl(title));
	}

	@Override
	@Transactional(readOnly = true)
	public List<CsnTerminology> searchInTerminology(String term) {
		List<CsnTerminology> result = csnTerminologyDao.searchInTerminology(term);
		if(result == null){
			result = new ArrayList<CsnTerminology>();
		}
		
		return result;
	}

	@Override
	public void saveTerminologies(CsnTerminologyDto terminologyDto) {
		Validate.notNull(terminologyDto);
		if(CollectionUtils.isNotEmpty(terminologyDto.getCzechTerminologies())){
			saveTerminologies(terminologyDto.getCzechTerminologies());
		}
		if(CollectionUtils.isNotEmpty(terminologyDto.getEnglishTerminologies())){
			saveTerminologies(terminologyDto.getEnglishTerminologies());
		}
		
		
	}
	
	@Override
	public void saveTerminologies(List<CsnTerminology> terminologies){
		User user = UserUtils.getLoggedUser();
		if(CollectionUtils.isNotEmpty(terminologies)){	
			for(CsnTerminology t : terminologies){
				t.setCreated(new LocalDateTime());
				t.setCreatedBy(user);
				csnTerminologyDao.save(t);
			}
		}
	}
	
	@Override
	public void deleteAll(Set<CsnTerminology> terminologies){
		if(CollectionUtils.isNotEmpty(terminologies)){	
			for(CsnTerminology t : terminologies){
				csnTerminologyDao.remove(t);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PageDto getCsnTerminologyPage(int currentPage, Map<String, Object> criteria) {
		return csnTerminologyDao.getCsnTerminologyPage(currentPage, criteria);
	}
	
	

}
