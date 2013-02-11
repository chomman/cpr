package sk.peterjurkovic.cpr.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.Tag;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.TagService;
import sk.peterjurkovic.cpr.services.WebpageService;

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
	
	
	
	public static final String DOP_URL = "/vygenerovat-prohlaseni";
	
	public static final String DOP_FORM_URL = "/vygenerovat-prohlaseni/form/";
	
	
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
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();		
		model.put("webpage", webpage);
		model.put("tab", webpage.getId());
		model.put("assessmentSystems", assessmentSystemService.getAssessmentSystemsForPublic());
		model.put("notifiedBodies", notifiedBodyService.getNotifiedBodiesGroupedByCountry(Boolean.TRUE));
		modelMap.put("model", model);
		modelMap.addAttribute("declarationOfPerformance", createEmpty());
		return "/public/declaration-of-performance-form";
	}
	
	
	
	@RequestMapping(value = "/tag/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<Tag>  searchInTags(@RequestBody @RequestParam("term") String query){
		return tagService.searchInTags(query);
	}
	
	
	public DeclarationOfPerformance createEmpty(){
		DeclarationOfPerformance form = new DeclarationOfPerformance();
		form.setId(0l);
		return form;
	}
}
