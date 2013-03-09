package sk.peterjurkovic.cpr.controllers;

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


@Controller
public class PublicStandardController {

	@Autowired
	private StandardService standardService;
	
	
	
	@RequestMapping("/ehn/{standardCode}")
	public String showEhn(@PathVariable String standardCode,  ModelMap modelMap) throws PageNotFoundEception{
		
		Standard standard = standardService.getStandardByCode(standardCode);
		
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
