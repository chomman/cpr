package sk.peterjurkovic.cpr.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;
import sk.peterjurkovic.cpr.entities.EssentialCharacteristic;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.Tag;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.CountryService;
import sk.peterjurkovic.cpr.services.DeclarationOfPerformanceService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.RequirementService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.TagService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.web.editors.AssessmentSystemEditor;
import sk.peterjurkovic.cpr.web.editors.NotifiedBodyEditor;
import sk.peterjurkovic.cpr.web.forms.DeclarationOfPerformanceForm;

@Controller
public class PublicDeclarationOfPerformanceController {

	@Autowired
	private WebpageService webpageService;
	@Autowired
	private TagService tagService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private AssessmentSystemService assessmentSystemService;
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private RequirementService requirementService;
	@Autowired 
	private CountryService countryService;
	@Autowired
	private DeclarationOfPerformanceService declarationOfPerformanceService;
	
	@Autowired
	private AssessmentSystemEditor assessmentSystemEditor;
	@Autowired
	private NotifiedBodyEditor notifiedBodyEditor;
	
	public static final String DOP_URL = "/vygenerovat-prohlaseni";
	
	public static final String DOP_FORM_URL = "/vygenerovat-prohlaseni/form/";
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(NotifiedBody.class, this.notifiedBodyEditor);
		binder.registerCustomEditor(AssessmentSystem.class, this.assessmentSystemEditor);
    }
	
	
	@RequestMapping(DOP_URL)
	public String showSearchSection(ModelMap modelmap, HttpServletRequest request) throws PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode(DOP_URL);
		if(webpage == null){
			throw new PageNotFoundEception();
		}
				
		Map<String, Object> model = new HashMap<String, Object>();
		
		String query = request.getParameter("query");
		if(StringUtils.isNotBlank(query)){
			List<Standard> standards = standardService.getStandardsByTagName(query);
			model.put("standards", standards);
			model.put("query", query);
			model.put("url", DOP_FORM_URL);
		}
		
		model.put("webpage", webpage);
		model.put("tab", webpage.getId());
		modelmap.put("model", model);
		return "/public/declaration-of-performance";
	}
	
	
	
	
	@RequestMapping(value = DOP_FORM_URL, method = RequestMethod.GET)
	public String showForm(@RequestParam(value="ehn", required=false) String standardCode, ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception {
		
		//String standardCode = request.getParameter("ehn");
		if(standardCode == null){
			throw new PageNotFoundEception();
		}
		
		Webpage webpage = webpageService.getWebpageByCode(DOP_FORM_URL);
		Standard standard = standardService.getStandardByCode(standardCode);
		
		if(webpage == null || !webpage.getEnabled() || standard == null  ){
			throw new PageNotFoundEception();
		}
		
		prepareModel(webpage, standard, modelMap, createEmptyForm(standard));
		return "/public/declaration-of-performance-form";
	}
	
	
	
	
	@RequestMapping(value = DOP_FORM_URL, method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("declarationOfPerformance") @Valid DeclarationOfPerformanceForm form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException, PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode(DOP_FORM_URL);
		Standard standard = standardService.getStandardByCode(form.getDeclarationOfPerformance().getStandard().getCode());
		
		if(webpage == null || !webpage.getEnabled() || standard == null  ){
			throw new PageNotFoundEception();
		}
		form.getDeclarationOfPerformance().setStandard(standard);
		
		if(result.hasErrors()){
			logger.info("Has some errors ..");
			
		}else{
			createOrUpdate(form);
        }
		prepareModel(webpage, standard, modelMap, form);
		return "/public/declaration-of-performance-form";
	}
	
		
	
	@RequestMapping(value = "/tag/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<Tag>  searchInTags(@RequestBody @RequestParam("term") String query){
		return tagService.searchInTags(query);
	}
	
	
	
	
	public void prepareModel(Webpage webpage, Standard standard, ModelMap modelMap, DeclarationOfPerformanceForm form){
		Country country = countryService.getCountryById(1L);
		Map<String, Object> model = new HashMap<String, Object>();		
		model.put("webpage", webpage);
		model.put("tab", webpage.getId());
		model.put("url", DOP_FORM_URL + standard.getCode());
		model.put("standard", standard);
		model.put("requiremets", requirementService.getRequirementsByCountryAndStandard(country, standard));
		model.put("assessmentSystems", assessmentSystemService.getAssessmentSystemsForPublic());
		model.put("notifiedBodies", notifiedBodyService.getNotifiedBodiesGroupedByCountry(Boolean.TRUE));
		modelMap.put("model", model);
		modelMap.addAttribute("declarationOfPerformance", form);
	}
	
	public DeclarationOfPerformanceForm createEmptyForm(Standard standard){
		DeclarationOfPerformanceForm form = new DeclarationOfPerformanceForm();
		DeclarationOfPerformance dop = new DeclarationOfPerformance();
		dop.setStandard(standard);
		form.createCharacteristics(standard.getRequirements());
		dop.setId(0l);
		form.setDeclarationOfPerformance(dop);
		return form;
	}
	
	private void createOrUpdate(DeclarationOfPerformanceForm form) throws PageNotFoundEception{
		DeclarationOfPerformance dopWebForm = form.getDeclarationOfPerformance();
		DeclarationOfPerformance dop = null;
		if(dopWebForm.getId() == null || (dopWebForm.getId() == 0)){
			dop = new DeclarationOfPerformance();
			dop.setToken(RandomStringUtils.randomAlphanumeric(Constants.DOP_TOKEN_LENGTH));
		}else{
			dop = declarationOfPerformanceService.getDopById(dopWebForm.getId());
			if(dop == null){
				throw new PageNotFoundEception();
			}
		}
		
		dop.setAssessmentSystem(dopWebForm.getAssessmentSystem());
		dop.setAuthorisedRepresentative(dopWebForm.getAuthorisedRepresentative());
		dop.setEssentialCharacteristics(prepareCharacteristics(form.getCharacteristics(), dop));
		dop.setIntendedUse(dopWebForm.getIntendedUse());
		dop.setManufacturer(dopWebForm.getManufacturer());
		dop.setNotifiedBody(dopWebForm.getNotifiedBody());
		dop.setNumberOfDeclaration(dopWebForm.getNumberOfDeclaration());
		dop.setProductId(dopWebForm.getProductId());
		dop.setSerialId(dopWebForm.getSerialId());
		dop.setStandard(dopWebForm.getStandard());

		//dop.setEssentialCharacteristics(prepareCharacteristics(form.getCharacteristics()));
		
		if(dop.getId() == null){
			declarationOfPerformanceService.createDoP(dop);
		}
		
	}
	
	
	private Set<EssentialCharacteristic> prepareCharacteristics(List<EssentialCharacteristic> characteristics, DeclarationOfPerformance dop){
		Set<EssentialCharacteristic> newItems = new HashSet<EssentialCharacteristic>();
			for(EssentialCharacteristic characteristic : characteristics){
				Long id = characteristic.getRequirement().getId();
				Requirement requirement = requirementService.getRequirementById(id);
				characteristic.setRequirement(requirement);
				characteristic.setDeclarationOfPerformance(dop);
				newItems.add(characteristic);
				
			}
		return newItems;
	}
	
}
