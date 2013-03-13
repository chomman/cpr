package sk.peterjurkovic.cpr.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import org.springframework.web.servlet.support.RequestContext;

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
import sk.peterjurkovic.cpr.export.PdfByXhtmlrendererView;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.CountryService;
import sk.peterjurkovic.cpr.services.DeclarationOfPerformanceService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.RequirementService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.TagService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.validators.DeclarationOfPerformanceValidator;
import sk.peterjurkovic.cpr.web.editors.AssessmentSystemEditor;
import sk.peterjurkovic.cpr.web.editors.NotifiedBodyEditor;
import sk.peterjurkovic.cpr.web.forms.DeclarationOfPerformanceForm;

@Controller
@Scope("request")
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
	private DeclarationOfPerformanceValidator declarationOfPerformanceValidator;
	
	@Autowired
	private PdfByXhtmlrendererView pdfView;
	
	@Autowired
	private AssessmentSystemEditor assessmentSystemEditor;
	@Autowired
	private NotifiedBodyEditor notifiedBodyEditor;
	
	public static final String DOP_URL = "/vygenerovat-prohlaseni";
	
	public static final String DOP_FORM_URL = "/vygenerovat-prohlaseni/form/";
	
	public static final String DOP_SHOW_URL = "/dop/";
	
	public static final String DOP_EDIT_URL = "/dop/edit/";
	
	public static final String DOP_DELETE_URL = "/dop/delete/";
	
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(NotifiedBody.class, this.notifiedBodyEditor);
		binder.registerCustomEditor(AssessmentSystem.class, this.assessmentSystemEditor);
    }
	
	/**
	 * Zobrazi verejnu sekciu, obsahujuci vyhladavaci formular vyrobkov
	 * 
	 * @param Modelmap
	 * @param request
	 * @return
	 * @throws PageNotFoundEception
	 */
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
		
		if(request.getParameter("successDelete") != null){
			model.put("successDelete", true);
		}
		
		model.put("webpage", webpage);
		model.put("tab", webpage.getId());
		modelmap.put("model", model);
		return "/public/declaration-of-performance";
	}
	
	/**
	 * Zobrazi DoP formular, prostrednictvom ktoreho je mozne vygenerovat prohlaseni
	 * 
	 * @param standardCode
	 * @param modelMap
	 * @param request
	 * @return String View
	 * @throws PageNotFoundEception
	 */
	@RequestMapping(value = DOP_FORM_URL, method = RequestMethod.GET)
	public String showForm(@RequestParam(value="ehn", required=true) String standardCode, ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception {
		
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
	
	
	/**
	 * Vyexportuje DoP do PDF
	 * 
	 * @param String token, idetifikator DoP
	 * @param request
	 * @param response
	 * @return String view
	 * @throws PageNotFoundEception, ak DoP v systeme neexistuje
	 */
	@RequestMapping("/dop/export/pdf/{token}")
	public PdfByXhtmlrendererView exportPdf(@PathVariable String token, HttpServletRequest request, HttpServletResponse response) throws PageNotFoundEception{
		
		DeclarationOfPerformance dop = declarationOfPerformanceService.getByToken(token);
		if(token == null){
			throw new PageNotFoundEception();
		}
		
		Map model = new HashMap<String, Object>();
		model.put("springMacroRequestContext", new RequestContext(request, null, null, null));
		model.put("dop", dop);
		pdfView.setOutputFileName("dop-"+ dop.getStandard().getCode() +".pdf");
		pdfView.setFtlTemplateName("dop.ftl");
        try {	
        	pdfView.renderMergedOutputModel(model, request, response);
		} catch (Exception e) {
			logger.error("Nastala chyba pri generovani PDF: " + token , e);
		}
       return pdfView;
	}
	
	
	/**
	 * Zobrazi verejnu sekciu, v ktorej je mozne editovat DoP
	 * 
	 * @param String token, identifikator DoP
	 * @param ModelMap
	 * @param request
	 * @return String view
	 * @throws PageNotFoundEception
	 */
	@RequestMapping(DOP_EDIT_URL + "{token}")
	public String showEditForm(@PathVariable String token, ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception {
		DeclarationOfPerformance dop = declarationOfPerformanceService.getByToken(token);
		if(token == null){
			throw new PageNotFoundEception();
		}
		DeclarationOfPerformanceForm form = new DeclarationOfPerformanceForm();
		form.setDeclarationOfPerformance(dop);
		form.setCharacteristics(dop.getEssentialCharacteristics());
		prepareModel(null, dop.getStandard(), modelMap, form);
		return "/public/declaration-of-performance-edit";
	}
	
	
	/**
	 * Odstrani DoP na zaklade identifikatoru
	 * 
	 * @param String token
	 * @param modelMap
	 * @param request
	 * @return String view
	 * @throws PageNotFoundEception
	 */
	@RequestMapping(DOP_DELETE_URL + "{token}")
	public String deleteDoP(@PathVariable String token, ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception {
		DeclarationOfPerformance dop = declarationOfPerformanceService.getByToken(token);
		if(token == null){
			throw new PageNotFoundEception();
		}
		declarationOfPerformanceService.deleteDop(dop);
		return "redirect:" + DOP_URL + "?successDelete=1";
	}
	
	
	/**
	 * Zobrazi verejnu sekciu, v ktorej je mozne editovat DoP
	 * 
	 * @param DeclarationOfPerformanceForm form
	 * @param result
	 * @param modelMap
	 * @return View
	 * @throws ItemNotFoundException
	 * @throws PageNotFoundEception
	 */
	@RequestMapping(value = DOP_EDIT_URL + "{token}", method = RequestMethod.POST)
	public String processEditSubmit(@ModelAttribute("declarationOfPerformance") @Valid DeclarationOfPerformanceForm form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException, PageNotFoundEception {
		declarationOfPerformanceValidator.validate(result, form);
		if(result.hasErrors()){
			prepareModel(null, form.getDeclarationOfPerformance().getStandard(), modelMap, form);
			return "/public/declaration-of-performance-edit";
		}else{
			DeclarationOfPerformance dop = createOrUpdate(form);
			return "redirect:" + DOP_SHOW_URL  + dop.getToken() + "?success=1";
		}
	}
	
	/**
	 * Spracuje odoslany DoP formular.
	 * 
	 * @param form
	 * @param result
	 * @param modelMap
	 * @return Success view
	 * @throws ItemNotFoundException
	 * @throws PageNotFoundEception
	 */
	@RequestMapping(value = DOP_FORM_URL, method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("declarationOfPerformance") @Valid DeclarationOfPerformanceForm form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException, PageNotFoundEception {
		Webpage webpage = webpageService.getWebpageByCode(DOP_FORM_URL);
		Standard standard = standardService.getStandardByCode(form.getDeclarationOfPerformance().getStandard().getCode());
		if(webpage == null || !webpage.getEnabled() || standard == null  ){
			throw new PageNotFoundEception();
		}
		form.getDeclarationOfPerformance().setStandard(standard);
		declarationOfPerformanceValidator.validate(result, form);
		if(result.hasErrors()){
			logger.info("Has some errors ..");
			prepareModel(webpage, standard, modelMap, form);
			return "/public/declaration-of-performance-form";
		}else{
			DeclarationOfPerformance dop = createOrUpdate(form);
			return "redirect:" + DOP_SHOW_URL  + dop.getToken() + "?success=1";
		}
	}
	
	
	/**
	 * Zobrazi vygenerovane DoP
	 * 
	 * @param token
	 * @param modelMap
	 * @param request
	 * @return view
	 */
	@RequestMapping(DOP_SHOW_URL + "{token}")
	public String showDeclarationOfPerformance(@PathVariable String token, ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		DeclarationOfPerformance dop = declarationOfPerformanceService.getByToken(token);
		
		if(dop == null){
			modelMap.put("dopNotFound", true);
		}else{
			if(request.getParameter("success") != null){
				model.put("success", true);
			}
			model.put("dop", dop);
			modelMap.put("model", model);
		}
		
		return "/public/declaration-of-performance-view"; 
	}
	
	
	@RequestMapping(value = "/tag/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<Tag>  searchInTags(@RequestBody @RequestParam("term") String query){
		return tagService.searchInTags(query);
	}
	
	
	
	
	public void prepareModel(Webpage webpage, Standard standard, ModelMap modelMap, DeclarationOfPerformanceForm form){
		//Country country = countryService.getCountryById(1L);
		Map<String, Object> model = new HashMap<String, Object>();		
		if(webpage != null){
			model.put("webpage", webpage);
			model.put("tab", webpage.getId());
			model.put("url", DOP_FORM_URL + standard.getCode());
		}
		model.put("standard", standard);
		//model.put("requiremets", requirementService.getRequirementsByCountryAndStandard(country, standard));
		model.put("assessmentSystems", assessmentSystemService.getAssessmentSystemsForPublic());
		model.put("notifiedBodies", notifiedBodyService.getNotifiedBodiesGroupedByCountry(Boolean.TRUE));
		modelMap.put("model", model);
		modelMap.addAttribute("declarationOfPerformance", form);
	}
	
	
	
	public DeclarationOfPerformanceForm createEmptyForm(Standard standard){
		DeclarationOfPerformanceForm form = new DeclarationOfPerformanceForm();
		Country country = countryService.getCountryById(1L);
		DeclarationOfPerformance dop = new DeclarationOfPerformance();
		dop.setStandard(standard);
		form.createCharacteristics(requirementService.getRequirementsByCountryAndStandard(country, standard));
		dop.setId(0l);
		form.setDeclarationOfPerformance(dop);
		return form;
	}
	
	
	
	private DeclarationOfPerformance createOrUpdate(DeclarationOfPerformanceForm form) throws PageNotFoundEception{
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
			declarationOfPerformanceService.deleteEssentialCharacteristicByDopId(dopWebForm.getId());
		}
		dop.setAssessmentSystem(dopWebForm.getAssessmentSystem());
		dop.setAssessmentSystem2(dopWebForm.getAssessmentSystem2());
		dop.setAuthorisedRepresentative(dopWebForm.getAuthorisedRepresentative());
		dop.setEssentialCharacteristics(prepareCharacteristics(form.getCharacteristics(), dop));
		dop.setIntendedUse(dopWebForm.getIntendedUse());
		dop.setManufacturer(dopWebForm.getManufacturer());
		dop.setNotifiedBody(dopWebForm.getNotifiedBody());
		dop.setNotifiedBody2(dopWebForm.getNotifiedBody2());
		dop.setReport(dopWebForm.getReport());
		dop.setReport2(dopWebForm.getReport2());
		dop.setNumberOfDeclaration(dopWebForm.getNumberOfDeclaration());
		dop.setProductId(dopWebForm.getProductId());
		dop.setSerialId(dopWebForm.getSerialId());
		dop.setCumulative(dopWebForm.getCumulative());
		if(dop.getId() == null || dop.getId() == 0){
			dop.setStandard(dopWebForm.getStandard());
			declarationOfPerformanceService.createDoP(dop);
		}else{
			 declarationOfPerformanceService.updateDop(dop);
		}
		return dop;
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
