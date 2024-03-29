package cz.nlfnorm.quasar.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.AuthorityDao;
import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.dao.AuditorDao;
import cz.nlfnorm.quasar.dto.EvaluatedAuditorFunctions;
import cz.nlfnorm.quasar.dto.EvaluatedAuditorNandoCode;
import cz.nlfnorm.quasar.dto.EvaluatedAuditorNandoFunctionDto;
import cz.nlfnorm.quasar.dto.EvaluatedEacCode;
import cz.nlfnorm.quasar.dto.EvaludatedQsAuditorDto;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorEacCode;
import cz.nlfnorm.quasar.entities.AuditorExperience;
import cz.nlfnorm.quasar.entities.AuditorNandoCode;
import cz.nlfnorm.quasar.entities.SpecialTraining;
import cz.nlfnorm.quasar.services.AuditorEacCodeService;
import cz.nlfnorm.quasar.services.AuditorNandoCodeService;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.views.AbstractNandoFunction;
import cz.nlfnorm.quasar.views.ProductAssessorA;
import cz.nlfnorm.quasar.views.ProductAssessorR;
import cz.nlfnorm.quasar.views.ProductSpecialist;
import cz.nlfnorm.quasar.views.QsAuditor;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.utils.UserUtils;

/**
 * Implementation of Auditor service layer
 *  
 * @author Peter Jurkovic
 * @date Jun 12, 2014
 */
@Service("auditorService")
@Transactional(propagation = Propagation.REQUIRED)
public class AuditorServiceImpl implements AuditorService{
		
	@Autowired
	private AuditorDao auditorDao;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private AuditorEacCodeService auditorEacCodeService;
	@Autowired
	private AuditorNandoCodeService auditorNandoCodeService;
	
	@Override
	public void create(final Auditor auditor) {
		auditorDao.save(auditor);
	}

	@Override
	public void update(final Auditor auditor) {
		auditorDao.update(auditor);	
	}

	@Override
	public void delete(final Auditor auditor) {
		auditorDao.remove(auditor);
	}

	@Override
	@Transactional(readOnly = true)
	public Auditor getById(final Long id) {
		return auditorDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Auditor> getAll() {
		return auditorDao.getAll();
	}
	
	@Override
	public Long createAuditor(final Auditor auditor,final String password){
		userService.setUserPassword(auditor, password);
		createOrUpdate(auditor);
		auditor.getAuthoritySet().add(authorityDao.getByCode(Authority.ROLE_AUDITOR));
		auditor.getAuthoritySet().add(authorityDao.getByCode(Authority.ROLE_ADMIN));
		update(auditor);
		return auditor.getId();
	}

	@Override
	public void createOrUpdate(final Auditor auditor) {
		final User user = UserUtils.getLoggedUser();
		auditor.setChanged(new LocalDateTime());
		auditor.setChangedBy(user);
		if(auditor.getId() == null){
			auditor.setCreated(auditor.getChanged());
			auditor.setCreatedBy(user);
			create(auditor);
			syncCodes(auditor);
		}else{
			update(auditor);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isItcIdUniqe(final Integer id, final Long auditorId) {
		return auditorDao.isItcIdUniqe(id, auditorId);
	}

	private void syncCodes(final Auditor auditor){
		auditorEacCodeService.syncAuditorEacCodes(auditor);
		auditorNandoCodeService.syncAuditorNandoCodes(auditor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AutocompleteDto> autocomplete(final String term, final Boolean enabledObly, final Boolean adminsOnly) {
		if(StringUtils.isBlank(term)){
			return new ArrayList<>();
		}
		return auditorDao.autocomplete(term, enabledObly, adminsOnly);
	}

	@Override
	public void update(final Auditor form,final int actionType) {
		Auditor auditor = getById(form.getId());
		Validate.notNull(auditor);
		switch (actionType) {
		case PERSONAL_DATA_ACTION:
				auditor.mergePersonalData(form);
			break;

		default:
			throw new IllegalArgumentException("Unsupported action type: " + actionType);
		}
		createOrUpdate(auditor);
	}

	@Override
	public void createOrUpdateAuditorExperience(final AuditorExperience form) {
		Validate.notNull(form);
		Validate.notNull(form.getExperience());
		Validate.notNull(form.getAuditor());
		if(form.getId() != null){
			Set<AuditorExperience> experiencies = form.getAuditor().getAuditorExperiences();
			for(AuditorExperience exp : experiencies){
				if(exp.equals(form)){
					exp.setYears(form.getYears());
					setChanged(exp);
					break;
				}
			}
		}else{
			setChanged(form);
			form.getAuditor().getAuditorExperiences().add(form);
		}
		createOrUpdate(form.getAuditor());
	}
	
	@Override
	public void createAuditorSpecialTraining(final SpecialTraining form) {
		Validate.notNull(form);
		Validate.notNull(form.getAuditor());
		form.setChangedBy(UserUtils.getLoggedUser());
		form.getAuditor().getSpecialTrainings().add(form);
		createOrUpdate(form.getAuditor());
	}
	
	public void removeAuditorSpecailTraining(final Auditor auditor, final Long specialTrainingId){
		Validate.notNull(auditor);
		for(final SpecialTraining st : auditor.getSpecialTrainings()){
			if(st.getId().equals(specialTrainingId)){
				auditor.getSpecialTrainings().remove(st);
				break;
			}
		}
	}

	
	
	private void setChanged(AuditorExperience experience){
		experience.setChangedBy(UserUtils.getLoggedUser());
		experience.setChanged(new LocalDateTime());
	}

	@Override
	@Transactional(readOnly = true)
	public QsAuditor getQsAuditorById(Long id) {
		return auditorDao.getQsAuditorById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public ProductAssessorA getProductAssessorAById(final Long id) {
		return auditorDao.getProductAssessorAById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProductAssessorR getProductAssessorRById(Long id) {
		return auditorDao.getProductAssessorRById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProductSpecialist getProductSpecialistById(Long id) {
		return auditorDao.getProductSpecialistById(id);
	}
	
	
	
	
	
	@Transactional(readOnly = true)
	public List<EvaludatedQsAuditorDto> evaluateForQsAuditor(final List<Auditor> auditorList){
		final List<EvaludatedQsAuditorDto> result = new ArrayList<>();
		for(final Auditor auditor : auditorList){
			result.add(evaaluateCodesForQsAuditor(auditor));
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	private EvaludatedQsAuditorDto evaaluateCodesForQsAuditor(final Auditor auditor){
		final EvaludatedQsAuditorDto evaluatedFunction = new EvaludatedQsAuditorDto(auditor);
		evaluatedFunction.setCodes(evaluateCodesForQsAuditor(auditor));
		return evaluatedFunction;
	}
	
	
	@Transactional(readOnly = true)
	public List<EvaluatedEacCode> evaluateCodesForQsAuditor(final Auditor auditor){
		List<EvaluatedEacCode> result = new ArrayList<>();
		final QsAuditor qsAuditor = getQsAuditorById(auditor.getId());
		final List<AuditorEacCode> codeList =  auditorEacCodeService.getAllAuditorEacCodes(auditor);
		for(final AuditorEacCode code : codeList){
			EvaluatedEacCode eCode = new EvaluatedEacCode(code);
			if(qsAuditor.getAreAllRequirementsValid() && code.isGranted()){
				eCode.setGrated(true);
			}
			result.add(eCode);
		}	
		return result;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EvaluatedAuditorNandoFunctionDto> evaludateForProductAssessorA(final List<Auditor> auditorList){
		return evaludateNandoFunctions(auditorList, Auditor.TYPE_PRODUCT_ASSESSOR_A);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EvaluatedAuditorNandoFunctionDto> evaludateForProductAssessorR(final List<Auditor> auditorList){
		return evaludateNandoFunctions(auditorList, Auditor.TYPE_PRODUCT_ASSESSOR_R);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EvaluatedAuditorNandoFunctionDto> evaludateForProductSpecialist(final List<Auditor> auditorList){
		return evaludateNandoFunctions(auditorList, Auditor.TYPE_PRODUCT_SPECIALIST);
	}
	
	@Transactional(readOnly = true)
	private List<EvaluatedAuditorNandoFunctionDto> evaludateNandoFunctions(final List<Auditor> auditorList, final int functionType){
		final List<EvaluatedAuditorNandoFunctionDto> result = new ArrayList<>();
			for(final Auditor auditor : auditorList){
				result.add(evaludateNandoFunction(auditor, functionType));
			}
		return result;
	}
	
	@Transactional(readOnly = true)
	private EvaluatedAuditorNandoFunctionDto evaludateNandoFunction(final Auditor auditor, final int functionType){
		Validate.notNull(auditor);
		EvaluatedAuditorNandoFunctionDto eCode = new EvaluatedAuditorNandoFunctionDto(auditor);
		AbstractNandoFunction function = null;
		switch (functionType) {
		case Auditor.TYPE_PRODUCT_ASSESSOR_A:
			function = getProductAssessorAById(auditor.getId());
			break;
		case Auditor.TYPE_PRODUCT_ASSESSOR_R:
			function = getProductAssessorRById(auditor.getId());
			break;
		case Auditor.TYPE_PRODUCT_SPECIALIST:
			function = getProductSpecialistById(auditor.getId());
			break;
		default:
			throw new IllegalArgumentException(String.format("Unknow function type: %s" , functionType));
		}
		eCode.setCodes(evaluateAuditorNandoCodesFor(function, auditor));
		return eCode;
	}
	
	/**
	 * Evaludate, If NANDO codes for function ProductAssessorA are granted.
	 * @return list of evaluated auditor's NANDO codes.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<EvaluatedAuditorNandoCode> evaluateAuditorNandoCodesFor(final AbstractNandoFunction function, final Auditor auditor) {
		
		List<EvaluatedAuditorNandoCode> result = new ArrayList<>();
		final List<AuditorNandoCode> auditorNandoCodes = getAuditorNandoCodesFor(function, auditor);
		final boolean forProductAssessorA = function instanceof ProductAssessorA;
		final boolean forProductAssessorR = function instanceof ProductAssessorR;
		final boolean forProductSpecialist = function instanceof ProductSpecialist;
		
		for(final AuditorNandoCode code : auditorNandoCodes){
			EvaluatedAuditorNandoCode eCode = new EvaluatedAuditorNandoCode(code);
			if(
					(forProductAssessorA && code.isGrantedForProductAssessorA()) ||
					(forProductAssessorR && code.isGrantedForProductAssessorR()) ||
					(forProductSpecialist && code.isGrantedForProductSpecialist()) 
					
			){
				if(isCodeGrantedToFunction(code, function)){
					eCode.setGrated(true);
				}
			}
			result.add(eCode);
		}
		return result;
	}
	
	private boolean isCodeGrantedToFunction(final AuditorNandoCode code, final AbstractNandoFunction function){
		return (code.isActiveMd() && function.isGeneralRequirementsActiveMd()) ||
				(code.isNonActiveMd() && function.isGeneralRequirementsNonActiveMd()) ||
				(code.isIvd() && function.isGeneralRequirementsIvd()) ||
				(code.isHorizontal() && (
								function.isGeneralRequirementsActiveMd() ||
								function.isGeneralRequirementsNonActiveMd()
								)
				);
	}
	
	
	private List<AuditorNandoCode> getAuditorNandoCodesFor(final AbstractNandoFunction function, final Auditor auditor){
		if(function instanceof ProductAssessorA){
			return auditorNandoCodeService.getForProductAssessorA(auditor);
		}else if(function instanceof ProductAssessorR){
			return auditorNandoCodeService.getForProductAssessorR(auditor);
		}else if(function instanceof ProductSpecialist){
			return auditorNandoCodeService.getForProductSpecialist(auditor);
		}
		throw new IllegalArgumentException("Unknown auditor function");
	}

	@Override
	@Transactional(readOnly = true)
	public PageDto getAuditorPage(final int pageNumber, Map<String, Object> criteria) {
		return auditorDao.getAuditorPage(pageNumber, validateCriteria( criteria ));
	}
	
	
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() > 0){
			criteria.put(Filter.ORDER, ParseUtils.parseIntFromStringObject(criteria.get(Filter.ORDER)));
			criteria.put(Filter.ENABLED, ParseUtils.parseStringToBoolean(criteria.get(Filter.ENABLED)));
			criteria.put(AuditorFilter.INTERNAL_ONLY, ParseUtils.parseStringToBoolean(criteria.get(AuditorFilter.INTERNAL_ONLY)));
			criteria.put(AuditorFilter.IN_TRAINING, ParseUtils.parseStringToBoolean(criteria.get(AuditorFilter.IN_TRAINING)));
			criteria.put(AuditorFilter.FORMAL_LEG_REQ, ParseUtils.parseStringToBoolean(criteria.get(AuditorFilter.FORMAL_LEG_REQ)));
			criteria.put(AuditorFilter.PARNTER, ParseUtils.parseLongFromStringObject(criteria.get(AuditorFilter.PARNTER)));
			criteria.put(AuditorFilter.DATE_FROM, ParseUtils.parseDateTimeFromStringObject(criteria.get(AuditorFilter.DATE_FROM)));
			criteria.put(AuditorFilter.DATE_TO, ParseUtils.parseDateTimeFromStringObject(criteria.get(AuditorFilter.DATE_TO)));
		}
		return criteria;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Auditor> getAuditors(Map<String, Object> criteria) {
		return auditorDao.getAuditors(validateCriteria( criteria ));
	}

	
	@Override
	@Transactional(readOnly = true)
	public EvaluatedAuditorFunctions getEvaludatedAuditorFunctions(final Auditor auditor) {
		Validate.notNull(auditor);
		EvaluatedAuditorFunctions eFunction = new EvaluatedAuditorFunctions(auditor);
		eFunction.setQsAuditor(evaaluateCodesForQsAuditor(auditor));
		eFunction.setProductAssessorA(evaludateNandoFunction(auditor, Auditor.TYPE_PRODUCT_ASSESSOR_A));
		eFunction.setProductAssessorR(evaludateNandoFunction(auditor, Auditor.TYPE_PRODUCT_ASSESSOR_R));
		eFunction.setProductSpecialist(evaludateNandoFunction(auditor, Auditor.TYPE_PRODUCT_SPECIALIST));
		return eFunction;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EvaluatedAuditorFunctions> getEvaludatedAuditorFunctions(List<Auditor> auditorList) {
		final List<EvaluatedAuditorFunctions> eAuditorList = new ArrayList<>();
		if(auditorList != null){
			for(final Auditor auditor : auditorList){
				eAuditorList.add(getEvaludatedAuditorFunctions(auditor));
			}
		}
		return eAuditorList;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountOfAuditDaysInRecentYear(final Long auditorId) {
		return auditorDao.getCountOfAuditDaysInRecentYear(auditorId);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountOfTrainingHoursInRecentYear(final Long auditorId) {
		return auditorDao.getCountOfTrainingHoursInRecentYear(auditorId);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountOfDesignDossiersInLastDays(final Long auditorId, final int countOfDays) {
		return auditorDao.getCountOfDesignDossiersInLastDays(auditorId, countOfDays);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountOfTechnicalFilesInLastDays(final Long auditorId, final int countOfDays) {
		return auditorDao.getCountOfTechnicalFilesInLastDays(auditorId, countOfDays);
	}

	
}
