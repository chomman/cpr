package sk.peterjurkovic.cpr.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.WebpageService;


@Controller
public class PublicStandardController {

	@Autowired
	private StandardService standardService;
	@Autowired
	private WebpageService webpageService;
	
	
	
	@RequestMapping("/ehn/{id}")
	public String showEhn(@PathVariable Long id,  ModelMap modelMap) throws PageNotFoundEception{
		
		Standard standard = standardService.getStandardById(id);
		
		if(standard == null || !standard.getEnabled()){
			throw new PageNotFoundEception();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("standard", standard);
		model.put("url", PublicDeclarationOfPerformanceController.DOP_FORM_URL);
		modelMap.put("model", model);
		return "public/ehn";
	}
	
	
}
