package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Country;
import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorEacCode;
import cz.nlfnorm.quasar.entities.AuditorExperience;
import cz.nlfnorm.quasar.entities.AuditorNandoCode;
import cz.nlfnorm.quasar.entities.EducationLevel;
import cz.nlfnorm.quasar.entities.Experience;
import cz.nlfnorm.quasar.entities.FieldOfEducation;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.entities.Partner;
import cz.nlfnorm.quasar.entities.SpecialTraining;
import cz.nlfnorm.quasar.enums.AuditorOrder;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.services.AuditorEacCodeService;
import cz.nlfnorm.quasar.services.AuditorNandoCodeService;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.services.EducationLevelService;
import cz.nlfnorm.quasar.services.ExperienceService;
import cz.nlfnorm.quasar.services.FieldOfEducationService;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.quasar.services.QuasarSettingsService;
import cz.nlfnorm.quasar.views.ProductAssessorA;
import cz.nlfnorm.quasar.views.ProductAssessorR;
import cz.nlfnorm.quasar.views.ProductSpecialist;
import cz.nlfnorm.quasar.web.forms.AuditorForm;
import cz.nlfnorm.quasar.web.validators.AuditorValidator;
import cz.nlfnorm.services.CountryService;
import cz.nlfnorm.services.NotifiedBodyService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.controllers.admin.cpr.NotifiedBodyController;
import cz.nlfnorm.web.editors.IdentifiableByLongPropertyEditor;
import cz.nlfnorm.web.editors.LocalDateEditor;

/**
 * 
 * @author Peter Jurkovic
 * @date Jun 12, 2014
 */
@Controller
public class AuditorController extends QuasarSupportController {
	
	private final static int TAB = 3; 
	
	
	private final static int SUB_TAB_PERSONAL_DATA = 1;
	private final static int SUB_TAB_QS_ADUTITOR = 2;
	private final static int SUB_TAB_PROUCT_ASSESSOR_A = 3;
	private final static int SUB_TAB_PROUCT_ASSESSOR_R = 4;
	private final static int SUB_TAB_PROUCT_SPECIALIST = 5;
	private final static int SUB_TAB_OUTPUT = 6;

	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/auditors";
	private final static String ADD_AUDITOR_MAPPING_URL = "/admin/quasar/manage/auditor/add";
	public final static String EDIT_AUDITOR_MAPPING_URL = "/admin/quasar/manage/auditor/{auditorId}";
	public final static String AUDITOR_DETAIL_URL = "/admin/quasar/manage/auditor/";
	public final static String AUDITOR_PROFILE_URL = "/admin/quasar/profile/{functionType}";
	
	private final static String AUDITOR_FUNCTION_MAPPING_URL =  EDIT_AUDITOR_MAPPING_URL + "/f/{functionType}";
	
	@Autowired
	private AuditorValidator auditorValidator;
	
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuditorService auditorService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private EducationLevelService educationLevelService;
	@Autowired
	private FieldOfEducationService fieldOfEducationService;
	@Autowired
	private ExperienceService experienceService;
	@Autowired
	private AuditorEacCodeService auditorEacCodeService;
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private LocalDateEditor localDateEditor;
	@Autowired
	private AuditorNandoCodeService auditorNandoCodeService;
	@Autowired
	private QuasarSettingsService quasarSettingsService;
	
	public AuditorController(){
		setTableItemsView("auditor-list");
		setEditFormView("auditor-edit");
		setViewName("auditor-add");
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Country.class, new IdentifiableByLongPropertyEditor<Country>( countryService ));
		binder.registerCustomEditor(EducationLevel.class, new IdentifiableByLongPropertyEditor<EducationLevel>( educationLevelService ));
		binder.registerCustomEditor(FieldOfEducation.class, new IdentifiableByLongPropertyEditor<FieldOfEducation>( fieldOfEducationService ));
		binder.registerCustomEditor(Partner.class, new IdentifiableByLongPropertyEditor<Partner>( partnerService ));
		binder.registerCustomEditor(Auditor.class, new IdentifiableByLongPropertyEditor<Auditor>( auditorService ));
		binder.registerCustomEditor(Experience.class, new IdentifiableByLongPropertyEditor<Experience>( experienceService ));
		binder.registerCustomEditor(NotifiedBody.class, new IdentifiableByLongPropertyEditor<NotifiedBody>( notifiedBodyService ));
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
	}
	
	@RequestMapping("/admin/quasar/auditors/{functionType}")
	public String handleFunction(@PathVariable int functionType, ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception{
		Map<String, Object> criteria = RequestUtils.getRequestParameterMap(request);
		Map<String, Object> model = new HashMap<>();
		final List<Auditor> auditorList = auditorService.getAuditors(criteria);
		switch (functionType) {
		case SUB_TAB_QS_ADUTITOR:
			model.put("items", auditorService.evaluateForQsAuditor(auditorList));
			break;
		case SUB_TAB_PROUCT_ASSESSOR_A:
			model.put("items", auditorService.evaludateForProductAssessorA(auditorList));
			break;
		case SUB_TAB_PROUCT_ASSESSOR_R:
			model.put("items", auditorService.evaludateForProductAssessorR(auditorList));
			break;
		case SUB_TAB_PROUCT_SPECIALIST:
			model.put("items", auditorService.evaludateForProductSpecialist(auditorList));
			break;
		case SUB_TAB_OUTPUT :
			model.put("items", auditorService.getEvaludatedAuditorFunctions(auditorList));
			break;
		default:
			throw new PageNotFoundEception();
		}
		model.put("params", criteria);
		model.put("partners", partnerService.getAll());
		model.put("functionType", functionType);
		model.put("disableScripts", true);
		model.put("printable", request.getParameter("printable") != null);
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getViewDir() + "function-output";
	}
	

	@RequestMapping(LIST_MAPPING_URL)
	public String showAuditorList(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		final PageDto page = auditorService.getAuditorPage(currentPage, params);
		if(page.getCount() > 0){
			model.put("paginationLinks", getPaginationItems(request, params, page.getCount(), "/admin/quasar/manage/auditors"));
			model.put("auditors", page.getItems());
		}
		model.put("orders", AuditorOrder.getAll());
		model.put("partners", partnerService.getAll());
		model.put("params", params);
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	
	@RequestMapping(value = ADD_AUDITOR_MAPPING_URL, method = RequestMethod.GET)
	public String createNewAuditor(ModelMap modelMap) {
		prepareCreateModel(modelMap, new AuditorForm());
		return getViewName();
	}
	
	@RequestMapping(value = ADD_AUDITOR_MAPPING_URL, method = RequestMethod.POST)
	public String processCreateAuditor(ModelMap modelMap, @Valid @ModelAttribute("auditorForm") AuditorForm form, BindingResult request ) {
		boolean isNotValid = request.hasErrors();
		if(!isNotValid){
			auditorValidator.validate(form, request);
		}
		if(isNotValid || request.hasErrors()){
			prepareCreateModel(modelMap, form);
			return getViewName();
		}
		final Long id = auditorService.createAuditor(form.getAuditor(), form.getNewPassword());
		return successUpdateRedirect(getEditUrl(id));
	}
	
	
	
	@RequestMapping(value = EDIT_AUDITOR_MAPPING_URL, method = RequestMethod.GET)
	public String showEditForm(ModelMap modelMap, HttpServletRequest request, @PathVariable long auditorId) throws ItemNotFoundException {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		final Auditor form = auditorService.getById(auditorId);
		prepareAuditorModel(modelMap, form, form);
		return getEditFormView();
	}
	
	
	
	@RequestMapping(value = EDIT_AUDITOR_MAPPING_URL, method = RequestMethod.POST)
	public String hadnleSubmit(ModelMap modelMap, 
			@RequestParam("action") int action,
			@ModelAttribute(COMMAND) Auditor form, 
			BindingResult result) throws ItemNotFoundException {
		
		if(action == AuditorService.PERSONAL_DATA_ACTION){
			auditorValidator.validate(form, result);
			if(result.hasErrors()){
				prepareAuditorModel(modelMap, form, form);
				return getEditFormView(); 
			}
		}
		auditorService.update(form, action);
		prepareAuditorModel(modelMap, form, auditorService.getById(form.getId()));
		appendSuccessCreateParam(modelMap);
		return getEditFormView();
	}
	
	@RequestMapping(value = AUDITOR_PROFILE_URL, method = RequestMethod.GET)
	public String handleAuditorProfile(ModelMap modelMap, HttpServletRequest request, @PathVariable int	functionType) throws ItemNotFoundException {
		final Auditor auditor = auditorService.getById(UserUtils.getLoggedUser().getId());
		Map<String, Object> model = new HashMap<>();
		model.put("auditor", auditor);
		prepareAuditorProfileModel(model, auditor);
		if(functionType != SUB_TAB_PERSONAL_DATA){
			prepareModelForFunction(model, functionType, auditor, false);
		}
		appendSubTab(model, functionType);
		appendModel(modelMap, model);
		return getViewDir() + "profile/auditor-profile";
	}
	
	
	
	@RequestMapping(value = EDIT_AUDITOR_MAPPING_URL + "/experience", method = RequestMethod.POST)
	public String handeExperienceSubmit(@Valid @ModelAttribute AuditorExperience form, BindingResult result){
		if(!result.hasErrors()){
			auditorService.createOrUpdateAuditorExperience(form);
			return successUpdateRedirect(getEditUrl(form.getAuditor().getId()));
		}
		return "redirect:" + getEditUrl(form.getAuditor().getId());
	}
	
	
	
	@RequestMapping(value = EDIT_AUDITOR_MAPPING_URL + "/special-training", method = RequestMethod.POST)
	public String handleSpecialTrainingCreate(@Valid @ModelAttribute SpecialTraining form, BindingResult result){
		if(!result.hasErrors()){
			auditorService.createAuditorSpecialTraining(form);
			return successUpdateRedirect(getEditUrl(form.getAuditor().getId()));
		}
		return "redirect:" + getEditUrl(form.getAuditor().getId());
	}
	
	
	
	@RequestMapping(EDIT_AUDITOR_MAPPING_URL + "/special-training/{id}")
	public String handleSpecialTrainingDelete(@PathVariable Long auditorId, @PathVariable Long id) throws ItemNotFoundException{
			final Auditor auditor = auditorService.getById(auditorId);
			validateNotNull(auditor,String.format("Auditor [id=%s] was not found", auditorId));
			auditorService.removeAuditorSpecailTraining(auditor, id);
		return successUpdateRedirect(getEditUrl(auditorId));
	}
	
	
	
	@RequestMapping(value = "/admin/quasar/auditors", method = RequestMethod.GET)
	public @ResponseBody List<AutocompleteDto>  auditorAutocomplete(
			@RequestBody @RequestParam("term") String term,
			@RequestBody @RequestParam(value = "enabled", required = false) Boolean enabled,
			@RequestBody @RequestParam(value = "adminsOnly", required = false) Boolean adminsOnly){
		return auditorService.autocomplete(term, enabled, adminsOnly);
	}
	
	
	
	@RequestMapping(value = AUDITOR_FUNCTION_MAPPING_URL, method = RequestMethod.GET)
	public String handleQsAuditor(
			ModelMap modelMap, 
			HttpServletRequest request,
			@PathVariable Long auditorId, 
			@PathVariable int functionType) throws ItemNotFoundException {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		final Auditor auditor = auditorService.getById(auditorId);
		validateNotNull(auditor, "Auditor was not found");
		prepareModelForFunction(modelMap, auditor, functionType);
		return getEditFormView();
	}
	
	@RequestMapping(value = EDIT_AUDITOR_MAPPING_URL + "/f/" +SUB_TAB_QS_ADUTITOR, method = RequestMethod.POST)
	public String handleQsAuditorSubmit(
			ModelMap modelMap, 
			@Valid @ModelAttribute AuditorEacCode form,
			BindingResult result
			) throws ItemNotFoundException {
		if(!result.hasErrors()){
			auditorEacCodeService.updateAndSetChanged(form);
			return successUpdateRedirect(getEditUrl(form.getAuditor().getId()) + "/f/" +SUB_TAB_QS_ADUTITOR);
		}
		return "redirect:" + getEditUrl(form.getAuditor().getId()) + "/f/" +SUB_TAB_QS_ADUTITOR;
	}
	
	
	@RequestMapping(value = EDIT_AUDITOR_MAPPING_URL + "/f/{functionType}", method = RequestMethod.POST)
	public String handleNandoCodeSubmit(
			@PathVariable int functionType, 
			ModelMap modelMap, 
			@ModelAttribute AuditorNandoCode form,
			BindingResult result
			) throws ItemNotFoundException {
		updateNandoCode(form, functionType);	
		return successUpdateRedirect(getEditUrl(form.getAuditor().getId()) + "/f/" +functionType);
	}
	
	
	@RequestMapping(value = EDIT_AUDITOR_MAPPING_URL + "/decision/{functionType}", method = RequestMethod.POST)
	public String handleDecisionOnTheAssessorsBranchSubmit(
			@PathVariable int functionType, 
			ModelMap modelMap, 
			@ModelAttribute Auditor form,
			BindingResult result
			) throws ItemNotFoundException {
			updateDecision(form, functionType);
		return successUpdateRedirect( getEditUrl(form.getId()) + "/f/" + functionType);
	}
	
	
	

	private void updateNandoCode(final AuditorNandoCode form, final int functionType){
		final AuditorNandoCode code = auditorNandoCodeService.getById(form.getId());
		Validate.notNull(code);
		switch (functionType) {
		case SUB_TAB_PROUCT_ASSESSOR_A:
			mergeProductAssessorA(code, form);
			break;
		case SUB_TAB_PROUCT_ASSESSOR_R:
			mergeProductAssessorR(code, form);
			break;
		case SUB_TAB_PROUCT_SPECIALIST:
			mergeProductSpecialist(code, form);
			break;
		default:
			throw new IllegalArgumentException("Unknown auditor function: " + functionType);
		}
		auditorNandoCodeService.createOrUpdate(code);
	}
			
	
	private void updateDecision(final Auditor form, final int functionType) {
		final Auditor auditor = auditorService.getById(form.getId());
		switch (functionType) {
		case SUB_TAB_QS_ADUTITOR:
			 auditor.setRecentActivitiesApprovedForQsAuditor(form.isRecentActivitiesApprovedForQsAuditor());
			break;
		case SUB_TAB_PROUCT_ASSESSOR_A:
			 auditor.getSpecialist()
			 	.put(Auditor.TYPE_PRODUCT_ASSESSOR_A, form.getSpecialist()
			 	.get(Auditor.TYPE_PRODUCT_ASSESSOR_A));
			 auditor.setRecentActivitiesApprovedForProductAssessorA(form.isRecentActivitiesApprovedForProductAssessorA());
			break;
		case SUB_TAB_PROUCT_ASSESSOR_R:
			 auditor.getSpecialist()
			 	.put(Auditor.TYPE_PRODUCT_ASSESSOR_R, form.getSpecialist()
			 	.get(Auditor.TYPE_PRODUCT_ASSESSOR_R));
			 auditor.setRecentActivitiesApprovedForProductAssessorR(form.isRecentActivitiesApprovedForProductAssessorR());
			break;
		case SUB_TAB_PROUCT_SPECIALIST:
			 auditor.getSpecialist()
			 	.put(Auditor.TYPE_PRODUCT_SPECIALIST, form.getSpecialist()
			 	.get(Auditor.TYPE_PRODUCT_SPECIALIST));
			 auditor.setResearchDevelopmentExperienceInYears(form.getResearchDevelopmentExperienceInYears());
			 auditor.setRecentActivitiesApprovedForProductSpecialist(form.isRecentActivitiesApprovedForProductSpecialist());
			break;
		default:
			throw new IllegalArgumentException("Unknown auditor function: " + functionType);
		}
		auditorService.createOrUpdate(auditor);
	}

	private void prepareModelForFunction(ModelMap map, final Auditor auditor, final int functionType){
		Map<String, Object> model = new HashMap<>();
		model.put("auditor", auditor);
		model.put("subTab", functionType);
		prepareModelForFunction(model, functionType, auditor, true);
		map.addAttribute("auditor", auditor);
		appendTabNo(model, TAB);
		appendModel(map, model);
	}
	
	
	
	
	
	private void prepareModelForFunction(Map<String, Object> model, final int functionType, final Auditor auditor, final boolean isEditable){
		switch (functionType) {
			case SUB_TAB_QS_ADUTITOR:
				model.put("function", auditorService.getQsAuditorById(auditor.getId()));
				model.put("codes",  auditorEacCodeService.getAllAuditorEacCodes(auditor));
			break;
			case SUB_TAB_PROUCT_ASSESSOR_A:
				final ProductAssessorA productAssessorAFunction = auditorService.getProductAssessorAById(auditor.getId());
				model.put("function", productAssessorAFunction);
				model.put("codes",  auditorService.evaluateAuditorNandoCodesFor(productAssessorAFunction, auditor));
			break;
			case SUB_TAB_PROUCT_ASSESSOR_R:
				final ProductAssessorR productAssessorRFunction = auditorService.getProductAssessorRById(auditor.getId());
				model.put("function", productAssessorRFunction);
				model.put("codes",  auditorService.evaluateAuditorNandoCodesFor(productAssessorRFunction, auditor));
			break;
			case SUB_TAB_PROUCT_SPECIALIST:
				final ProductSpecialist productSpecialist = auditorService.getProductSpecialistById(auditor.getId());
				model.put("function", productSpecialist);
				model.put("codes",  auditorService.evaluateAuditorNandoCodesFor(productSpecialist, auditor));
			break;
			case SUB_TAB_OUTPUT:
				model.put("eFunctions", auditorService.getEvaludatedAuditorFunctions(auditor));
			break;
			default:
				throw new IllegalArgumentException("Unknown function type: " + functionType);
		}
		model.put("nbUrl", NotifiedBodyController.QUASAR_NB_EIDT_URL);
		model.put("isEditable", isEditable);
	}
	
	
	
	private void prepareAuditorModel(ModelMap map, Auditor form, Auditor auditor){
		Map<String, Object> model = new HashMap<>();
		model.put("countries", countryService.getAllCountries());
		model.put("partners", partnerService.getAll());
		model.put("avgAuditLogRating", auditLogService.getAvgAuditorsRating(auditor));
		model.put("educationsLevels", educationLevelService.getAll());
		model.put("fieldsOfEducationActiveMd", fieldOfEducationService.getForActiveMedicalDevices());
		model.put("fieldsOfEducationNonActiveMd", fieldOfEducationService.getForNonActiveMedicalDevices());
		model.put("unassignedExperiences", experienceService.getAllExcept(auditor));
		prepareAuditorProfileModel(model, auditor);
		map.addAttribute(COMMAND, form);
		map.addAttribute("auditorExperience", new AuditorExperience(auditor));
		appendSubTab(model, SUB_TAB_PERSONAL_DATA);
		appendTabNo(model, TAB);
		appendModel(map, model);
	}
	
	private void prepareAuditorProfileModel(final Map<String, Object> model, final Auditor auditor){
		model.put("auditor", auditor);
		model.put("settings", quasarSettingsService.getSettings());
		model.put("sterileNandoCode", auditorNandoCodeService.getByNandoCode(NandoCode.STERILE, auditor.getId()));
		model.put("auditDaysIntRecentyear", auditorService.getCountOfAuditDaysInRecentYear(auditor.getId()));
	}
	
	
	private void prepareCreateModel(ModelMap map, AuditorForm form){
		Map<String, Object> model = new HashMap<>();
		map.addAttribute("auditorForm", form);
		appendTabNo(model, TAB);
		appendModel(map, model);
	}
	
	private void appendSubTab(Map<String, Object> model, final int tab){
		model.put("subTab", tab);
	}
	
	private String getEditUrl(Long id){
		return AUDITOR_DETAIL_URL + id;
	}
		
	
	private void mergeProductAssessorA(AuditorNandoCode obj, AuditorNandoCode form){
		obj.setCategorySpecificTraining(form.getCategorySpecificTraining());;
		obj.setNumberOfNbAudits(form.getNumberOfNbAudits());
		obj.setNumberOfIso13485Audits( form.getNumberOfIso13485Audits());
		obj.setProductAssessorAApproved( form.isProductAssessorAApproved() );
		obj.setProductAssessorARefused( form.isProductAssessorARefused());
		obj.setProductAssessorAReasonDetails(StringUtils.substring(form.getProductAssessorAReasonDetails(),0,255));
		obj.setProductAssessorAApprovedBy(form.getProductAssessorAApprovedBy());
	}
	
	
	private void mergeProductAssessorR(AuditorNandoCode obj, AuditorNandoCode form){
		obj.setCategorySpecificTraining(form.getCategorySpecificTraining());
		obj.setNumberOfTfReviews( form.getNumberOfTfReviews());
		obj.setProductAssessorRApproved( form.isProductAssessorRApproved());
		obj.setProductAssessorRRefused( form.isProductAssessorRRefused());
		obj.setProductAssessorRReasonDetails(StringUtils.substring(form.getProductAssessorRReasonDetails(),0,255));
		obj.setProductAssessorRApprovedBy( form.getProductAssessorRApprovedBy());
	}
	
	
	private void mergeProductSpecialist(AuditorNandoCode obj, AuditorNandoCode form){
		obj.setCategorySpecificTraining( form.getCategorySpecificTraining());
		obj.setNumberOfDdReviews( form.getNumberOfDdReviews());
		obj.setNumberOfTfReviews( form.getNumberOfTfReviews());
		obj.setProductSpecialistApproved( form.isProductSpecialistApproved());
		obj.setProductSpecialistRefused( form.isProductSpecialistRefused());
		obj.setProductSpecialistReasonDetails( StringUtils.substring(form.getProductSpecialistReasonDetails(),0,255));
		obj.setProductSpecialistApprovedBy( form.getProductSpecialistApprovedBy());
	}
	
	
}
