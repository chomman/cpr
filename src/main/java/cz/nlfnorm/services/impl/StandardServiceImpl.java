package cz.nlfnorm.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.StandardDao;
import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.entities.StandardCsn;
import cz.nlfnorm.entities.StandardGroup;
import cz.nlfnorm.entities.StandardNotifiedBody;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.enums.StandardStatus;
import cz.nlfnorm.services.StandardService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.utils.UserUtils;

@Service("standardService")
@Transactional(propagation = Propagation.REQUIRED)
public class StandardServiceImpl implements StandardService {
	
	@Autowired
	private StandardDao standardDao;
	@Autowired
	private UserService userService;
	
	@Override
	public void createStandard(Standard standard) {
		standardDao.save(standard);
	}

	@Override
	public void updateStandard(Standard standard) {
		standardDao.update(standard);
	}

	@Override
	public void deleteStandard(Standard standard) {
		standardDao.remove(standard);
	}
	
	@Override
	public void removeReferences(Standard standard){
		Validate.notNull(standard);
		List<Standard> replacedStandards = standardDao.getStandardsByReplaceStandard(standard);
		for(Standard s : replacedStandards){
			s.setReplaceStandard(null);
			standardDao.update(s);
		}
	}

	@Override
	@Transactional(readOnly =  true )
	public Standard getStandardById(Long id) {
		return standardDao.getByID(id);
	}

	@Override
	@Transactional(readOnly =  true )
	public Standard getStandardByCode(String code) {
		return standardDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly =  true )
	public List<Standard> getAllStandards() {
		return standardDao.getAll();
	}

	@Override
	@Transactional(readOnly =  true )
	public List<Standard> getStandardPage(int pageNumber, Map<String, Object> criteria) {
		return standardDao.getStandardPage(validateCriteria(criteria), pageNumber, Constants.ADMIN_PAGINATION_PAGE_SIZE);
	}
	
	@Override
	@Transactional(readOnly =  true )
	public List<Standard> getStandardPage(final int pageNumber, Map<String, Object> criteria, final int limit) {
		return standardDao.getStandardPage(validateCriteria(criteria), pageNumber, limit);
	}

		
	@Override
	@Transactional(readOnly =  true )
	public Long getCountOfStandards(Map<String, Object> criteria) {
		return standardDao.getCountOfSdandards(validateCriteria(criteria));
	}

	@Override
	@Transactional(readOnly =  true )
	public boolean isStandardIdUnique(String standardId, Long id) {
		return standardDao.isStandardIdUnique(standardId, id);
	}


	@Override
	public void mergeAndSetChanged(Standard standard) {
		final User user = UserUtils.getLoggedUser();
		standard.setChangedBy(user);
		standard.setChanged(new LocalDateTime());
		mergeStandard(standard);
	}
	
	@Override
	public void saveOrUpdate(final Standard standard) {
		final User user = UserUtils.getLoggedUser();
		if(standard.getId() == null){
			standard.setCreatedBy(user);
			standard.setChangedBy(user);
			standard.setCreated(new LocalDateTime());
			standard.setChanged(new LocalDateTime());
			standardDao.save(standard);
			standardDao.flush();
		}else{
			standard.setChangedBy(user);
			standard.setChanged(new LocalDateTime());
			standardDao.update(standard);
		}
		
	}


	@Override
	@Transactional(readOnly =  true )
	public List<Standard> autocomplateSearch(String query, Boolean enabled) {
		List<Standard> result = standardDao.autocomplateSearch(query, enabled);
		if(result == null){
			return new ArrayList<Standard>();
		}
		return result;
	}
	
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put(Filter.STANDARD_CATEGORY, ParseUtils.parseLongFromStringObject(criteria.get(Filter.STANDARD_CATEGORY)));
			criteria.put(Filter.REGULATION, ParseUtils.parseLongFromStringObject(criteria.get(Filter.REGULATION)));
			criteria.put(Filter.STANDARD_GROUP, ParseUtils.parseLongFromStringObject(criteria.get(Filter.STANDARD_GROUP)));
			criteria.put(Filter.COMMISION_DECISION, ParseUtils.parseLongFromStringObject(criteria.get(Filter.COMMISION_DECISION)));
			criteria.put(Filter.MANDATE, ParseUtils.parseLongFromStringObject(criteria.get(Filter.MANDATE)));
			criteria.put(Filter.NOTIFIED_BODY, ParseUtils.parseLongFromStringObject(criteria.get(Filter.NOTIFIED_BODY)));
			criteria.put(Filter.ASSESMENT_SYSTEM, ParseUtils.parseLongFromStringObject(criteria.get(Filter.ASSESMENT_SYSTEM)));
			criteria.put(Filter.ORDER, ParseUtils.parseIntFromStringObject(criteria.get(Filter.ORDER)));
			criteria.put(Filter.CREATED_TO, ParseUtils.parseLocalDateFromStringObject(criteria.get(Filter.CREATED_TO)));
			criteria.put(Filter.CREATED_FROM, ParseUtils.parseLocalDateFromStringObject(criteria.get(Filter.CREATED_FROM)));
			criteria.put(Filter.ENABLED, ParseUtils.parseStringToBoolean(criteria.get(Filter.ENABLED)));
		}
		return criteria;
	}

	
	@Override
	@Transactional(readOnly =  true )
	public List<Standard> getLastEditedOrNewestStandards(int count, Boolean enabled) {
		return standardDao.getLastEditedOrNewestStandards(count, enabled);
	}

	
	@Override
	@Transactional(readOnly =  true )
	public List<Standard> getStandardByStandardGroupForPublic(final StandardGroup StandardGroup) {
		return standardDao.getStandardByStandardGroupForPublic(StandardGroup);
	}

	@Override
	@Transactional(readOnly =  true )
	public List<Standard> getStandardsByTagName(final String tagName) {
		return standardDao.getStandardsByTagName(tagName);
	}

	@Override
	public void mergeStandard(Standard standard) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		standard.setChanged(new LocalDateTime());
		standard.setChangedBy(user);
		standardDao.merge(standard);
	}

	
	@Override
	@Transactional(readOnly =  true )
	public List<Standard> getStandardsByCsn(final StandardCsn csn) {
		return standardDao.getStandardsByCsn(csn);
	}
	
	@Override
	@Transactional(readOnly =  true )
	public List<Standard> getStandardsByStandardGroupCode(final String standardGroupCode){
		return standardDao.getStandardsByStandardGroupCode(standardGroupCode);
	}

	
	
	@Transactional(readOnly =  true )
	public List<Standard> getStandardsByNotifiedBody(final NotifiedBody notifiedBody){
		return standardDao.getStandardsByNotifiedBody(notifiedBody);
	}
	
	
	@Override
	public boolean updateReferencedStandard(Standard standard){
		Standard referencedStandard = standard.getReplaceStandard();
		StandardStatus status = standard.getStandardStatus();
		if(referencedStandard != null){
			if(status == null || status.equals(StandardStatus.HARMONIZED) || status.equals(StandardStatus.NON_HARMONIZED)){
					// ak sa niejedna o cyklicku zavislost
				if(!referencedStandard.equals(standard) &&
					referencedStandard.getReplaceStandard() == null || 
					!referencedStandard.getReplaceStandard().equals(standard) || 
					!referencedStandard.getStandardStatus().equals(StandardStatus.CANCELED_HARMONIZED) ||
					referencedStandard.getStandardStatus() == null){
						referencedStandard.setStandardStatus(StandardStatus.CANCELED_HARMONIZED);
						referencedStandard.setReplaceStandard(standard);
						saveOrUpdate(referencedStandard);
						return true;
					}
					
			}else if(status.equals(StandardStatus.CONCURRENT) && !referencedStandard.getStandardStatus().equals(StandardStatus.CONCURRENT)){
				referencedStandard.setStandardStatus(StandardStatus.CONCURRENT);
				referencedStandard.setReplaceStandard(standard);
				saveOrUpdate(referencedStandard);
				return true;
			}else{
				if( status.equals(StandardStatus.CANCELED_HARMONIZED) && !referencedStandard.equals(standard) && 
				   (referencedStandard.getReplaceStandard() == null || !referencedStandard.getReplaceStandard().equals(standard))){
					referencedStandard.setReplaceStandard(standard);
					saveOrUpdate(referencedStandard);
					return true;
				}
			}
		}
		return false;
	}

	
	@Override
	public void addStandardCsn(Standard standard, StandardCsn standardCsn) {
		Validate.notNull(standard);
		if(standardCsn != null && !standard.getStandardCsns().contains(standardCsn)){
			standard.getStandardCsns().add(standardCsn);
			saveOrUpdate(standard);
		}
	}

	@Override
	public void removeStandardCsn(Standard standard, StandardCsn standardCsn) {
		Validate.notNull(standard);
		Validate.notNull(standardCsn);
		if(standard.getStandardCsns().remove(standardCsn)){
			updateStandard(standard);
		}
	}

	@Override
	@Transactional( readOnly = true )
	public boolean hasAssociatedNotifiedBody(final NotifiedBody notifiedBody,final Standard standard) {
		if(notifiedBody == null){
			return false;
		}
		Validate.notNull(standard);
		for(StandardNotifiedBody snb : standard.getNotifiedBodies()){
			if(snb.getNotifiedBody().equals(notifiedBody)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void unassigenNotifiedBody(final Long standardNotifiedBodyId){
		standardDao.unassignNotifiedBody(standardNotifiedBodyId);
	}

	@Override
	@Transactional( readOnly = true )
	public List<Standard> getChangedStanards(final LocalDate dateFrom, final LocalDate dateTo, Boolean enabledOnly) {
		return standardDao.getChangedStanards(dateFrom, dateTo, enabledOnly);
	}

	@Override
	@Transactional( readOnly = true )
	public List<Standard> getStandardsForSitemap() {
		return standardDao.getStandardsForSitemap();
	}
	
	
		
	
}
