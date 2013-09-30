package sk.peterjurkovic.cpr.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.peterjurkovic.cpr.dto.CsnCategoryJsonDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnCategory;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.services.CsnCategoryService;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;

@Controller
public class AjaxCsnController {
	
	@Autowired
	private CsnService csnService;
	@Autowired
	private CsnCategoryService csnCategoryService;
	@Autowired
	private CsnTerminologyService csnTerminologyService;
	
		
	
	@RequestMapping(value = "/ajax/csn/category/{searchCodeOfParent}", method = RequestMethod.GET)
	public @ResponseBody List<CsnCategoryJsonDto>  getCsnCategoryChildrens(@PathVariable String searchCodeOfParent){
		CsnCategory category = csnCategoryService.getByCode(searchCodeOfParent);
		if(category != null){
			return category.toJsonFormat();
		}
		return new ArrayList<>();
	}
	
	
	@RequestMapping(value = "/ajax/csn/category", method = RequestMethod.GET)
	public @ResponseBody List<CsnCategoryJsonDto>  getCsnCategoryChildrens(){
		return csnCategoryService.getSubRootCategoriesInJsonFormat();
	}
	
	
	
	@RequestMapping(value = "/ajax/terminology/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<CsnTerminology>  searchTerminology(@RequestBody @RequestParam("term") String query){
		List<CsnTerminology> list =  csnTerminologyService.searchInTerminology(query);
		return list;
	}
	
	
	@RequestMapping(value = "/ajax/csn/category/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<CsnCategory>  searchCsnCategory(@RequestBody @RequestParam("term") String query){
		return csnCategoryService.autocomplete(query);
	}
	
	
	@RequestMapping(value = "/ajax/csn/category/autocomplete/cs", method = RequestMethod.GET)
	public @ResponseBody List<Csn>  searchCsnCategoryClassificationSymbol(@RequestBody @RequestParam("term") String query){
		return csnService.autocompleteByClassificationSymbol(query);
	}
	
	
	@RequestMapping(value = "/ajax/csn/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<Csn>  searchInCsnIds(@RequestBody @RequestParam("term") String query){
		return csnService.autocompleteByCsnId(query);
	}
}
