package sk.peterjurkovic.cpr.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;
import sk.peterjurkovic.cpr.services.WebpageService;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Controller
public class PublicCsnTerminologyController {

	public static final String PUBLIC_CSN_TERMINOLOGY_URL = "/terminologicky-slovnik";
	
	@Autowired
	private WebpageService webpageService;
	@Autowired
	private CsnTerminologyService csnTerminologyService;
	@Autowired
	private CsnService csnService;
	
	
	@RequestMapping(PUBLIC_CSN_TERMINOLOGY_URL)
	public String showSearchForm(ModelMap modelMap, HttpServletRequest request, @RequestParam(value="query", required = false) String query) throws PageNotFoundEception{
		
		Webpage webpage = webpageService.getWebpageByCode(PUBLIC_CSN_TERMINOLOGY_URL);
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = prepareBaseModel(webpage);
		
		
		if(StringUtils.isNotBlank(query)){
			model.put("terminologies", csnService.getCsnByTerminology(query));
			model.put("query", query);
		}
		
		
		model.put("subtab", webpage.getId());
		modelMap.put("model", model);
		return "/public/csn/terminology-search";
	}
	
	@RequestMapping(value = "/terminology/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<CsnTerminology>  searchTerminology(@RequestBody @RequestParam("term") String query){
		return csnTerminologyService.searchInTerminology(query);
	}
	
	
	/**
	 * Pripravi zakladny model pre view
	 * 
	 * @param Webpage sekcia systemu
	 * @return pripraveny model
	 */
	private Map<String, Object> prepareBaseModel(Webpage webpage){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		return model;
	}
}
