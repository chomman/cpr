package sk.peterjurkovic.cpr.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.Tag;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.CountryService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.RequirementService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.TagService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.web.forms.DeclarationOfPerformaceForm;

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
	
	
	public static final String DOP_URL = "/vygenerovat-prohlaseni";
	
	public static final String DOP_FORM_URL = "/vygenerovat-prohlaseni/form/";
	
	private Logger logger = Logger.getLogger(getClass());
	
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
		}
		
		model.put("webpage", webpage);
		model.put("tab", webpage.getId());
		modelmap.put("model", model);
		return "/public/declaration-of-performance";
	}
	
	
	
	
	@RequestMapping(value = DOP_FORM_URL +"{standardCode}", method = RequestMethod.GET)
	public String showForm(@PathVariable String standardCode,  ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode(DOP_FORM_URL);
		Standard standard = standardService.getStandardByCode(standardCode);
		Country country = countryService.getCountryById(1L);
		if(webpage == null || !webpage.getEnabled() || standard == null  ){
			throw new PageNotFoundEception();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();		
		model.put("webpage", webpage);
		model.put("tab", webpage.getId());
		model.put("standard", standard);
		model.put("requiremets", requirementService.getRequirementsByCountryAndStandard(country, standard));
		model.put("assessmentSystems", assessmentSystemService.getAssessmentSystemsForPublic());
		model.put("notifiedBodies", notifiedBodyService.getNotifiedBodiesGroupedByCountry(Boolean.TRUE));
		modelMap.put("model", model);
		modelMap.addAttribute("declarationOfPerformance", createEmpty(standard));
		return "/public/declaration-of-performance-form";
	}
	
	@RequestMapping( value = "/dop", method = RequestMethod.POST)
	public String processSubmit(DeclarationOfPerformance form, BindingResult result, ModelMap model) throws ItemNotFoundException {
		
		
		if(result.hasErrors()){
			logger.info("Has some errors ..");
			
		}
		
		
		
		

		
		return "/public/declaration-of-performance-form";
	}
	
	@RequestMapping(value = "/tag/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<Tag>  searchInTags(@RequestBody @RequestParam("term") String query){
		return tagService.searchInTags(query);
	}
	
	
	public DeclarationOfPerformaceForm createEmpty(Standard standard){
		DeclarationOfPerformaceForm form = new DeclarationOfPerformaceForm();
		
		/*List<Requirement> requiremets = requirementService.getRequirementsByCountryAndStandard(country, standard);
		if(requiremets != null){
			form.createCharacteristics(requiremets);
		}*/
		form.setStandard(standard);
		form.setId(0l);
		return form;
	}
}
