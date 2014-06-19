package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.LocalDate;
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
import cz.nlfnorm.entities.Country;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorExperience;
import cz.nlfnorm.quasar.entities.EducationLevel;
import cz.nlfnorm.quasar.entities.Experience;
import cz.nlfnorm.quasar.entities.FieldOfEducation;
import cz.nlfnorm.quasar.entities.Partner;
import cz.nlfnorm.quasar.entities.SpecialTraining;
import cz.nlfnorm.quasar.forms.AuditorForm;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.services.EducationLevelService;
import cz.nlfnorm.quasar.services.ExperienceService;
import cz.nlfnorm.quasar.services.FieldOfEducationService;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.quasar.validators.AuditorValidator;
import cz.nlfnorm.services.CountryService;
import cz.nlfnorm.services.UserService;
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

	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/auditors";
	private final static String ADD_AUDITOR_MAPPING_URL = "/admin/quasar/manage/auditor/add";
	private final static String EDIT_AUDITOR_MAPPING_URL = "/admin/quasar/manage/auditor/{auditorId}";
	
	@Autowired
	private AuditorValidator auditorValidator;
	
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
	private LocalDateEditor localDateEditor;
	
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
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String showAuditorList(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("auditors", auditorService.getAll());
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
	
	
	
	@RequestMapping(value = "/admin/quasar/auditors", method = RequestMethod.GET)
	public @ResponseBody List<AutocompleteDto>  auditorAutocomplete(
			@RequestBody @RequestParam("term") String term,
			@RequestBody @RequestParam(value = "enabled", required = false) Boolean enabled,
			@RequestBody @RequestParam(value = "adminsOnly", required = false) Boolean adminsOnly){
		return auditorService.autocomplete(term, enabled, adminsOnly);
	}
	
	
	private void prepareAuditorModel(ModelMap map, Auditor form, Auditor auditor){
		Map<String, Object> model = new HashMap<>();
		model.put("auditor", auditor);
		model.put("countries", countryService.getAllCountries());
		model.put("partners", partnerService.getAll());
		model.put("educationsLevels", educationLevelService.getAll());
		model.put("fieldsOfEducationActiveMd", fieldOfEducationService.getForActiveMedicalDevices());
		model.put("fieldsOfEducationNonActiveMd", fieldOfEducationService.getForNonActiveMedicalDevices());
		model.put("unassignedExperiences", experienceService.getAllExcept(auditor));
		map.addAttribute(COMMAND, form);
		map.addAttribute("auditorExperience", new AuditorExperience(auditor));
		appendSubTab(model, SUB_TAB_PERSONAL_DATA);
		appendTabNo(model, TAB);
		appendModel(map, model);
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
		return EDIT_AUDITOR_MAPPING_URL.replace("{auditorId}", id.toString());
	}
}
