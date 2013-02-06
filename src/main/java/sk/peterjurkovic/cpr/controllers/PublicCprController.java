package sk.peterjurkovic.cpr.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.BasicRequirementService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.WebpageService;


@Controller
public class PublicCprController {
	
	@Autowired
	private WebpageService webpageService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private BasicRequirementService basicRequirementService;
	public static final String CPR_INDEX_URL = "/cpr";
	
	
	
	
	@RequestMapping(CPR_INDEX_URL)
	public String home(ModelMap modelmap) throws PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode(CPR_INDEX_URL);
		if(webpage == null){
			throw new PageNotFoundEception();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		model.put("subtab", webpage.getId());
		model.put("tab", 3);
		model.put("submenu", webpageService.getPublicSection(Constants.WEBPAGE_CATEGORY_CPR_SUBMENU));
		modelmap.put("model", model);
		return "/public/cpr/index";
	}
	
	
	
	
	@RequestMapping("/cpr/zakladni-pozadavky-podle-cpr")
	public String requirements(ModelMap modelmap) throws PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode("/cpr/zakladni-pozadavky-podle-cpr");
		if(webpage == null){
			throw new PageNotFoundEception();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		model.put("subtab", webpage.getId());
		model.put("basicRequremets", basicRequirementService.getAllBasicRequirements());
		model.put("parentWebpage", webpageService.getWebpageByCode(CPR_INDEX_URL));
		model.put("tab", 3);
		model.put("submenu", webpageService.getPublicSection(Constants.WEBPAGE_CATEGORY_CPR_SUBMENU));
		modelmap.put("model", model);
		return "/public/cpr/basic-requirement";
	}
}
