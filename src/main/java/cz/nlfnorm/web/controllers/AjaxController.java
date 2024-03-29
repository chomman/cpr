package cz.nlfnorm.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.dto.CsnCategoryJsonDto;
import cz.nlfnorm.dto.FilterDto;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnCategory;
import cz.nlfnorm.entities.CsnTerminology;
import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.entities.StandardCsn;
import cz.nlfnorm.quasar.entities.CertificationBody;
import cz.nlfnorm.quasar.entities.Company;
import cz.nlfnorm.quasar.services.CertificationBodyService;
import cz.nlfnorm.quasar.services.CompanyService;
import cz.nlfnorm.services.AssessmentSystemService;
import cz.nlfnorm.services.CommissionDecisionService;
import cz.nlfnorm.services.CsnCategoryService;
import cz.nlfnorm.services.CsnService;
import cz.nlfnorm.services.CsnTerminologyService;
import cz.nlfnorm.services.MandateService;
import cz.nlfnorm.services.NotifiedBodyService;
import cz.nlfnorm.services.StandardCsnService;
import cz.nlfnorm.services.StandardGroupService;
import cz.nlfnorm.services.StandardService;
import cz.nlfnorm.services.TagService;

@Controller
public class AjaxController {
	
	@Autowired
	private CsnService csnService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private CsnCategoryService csnCategoryService;
	@Autowired
	private CsnTerminologyService csnTerminologyService;
	@Autowired
	private StandardCsnService standardCsnService;
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private StandardGroupService standardGroupService;
	@Autowired
	private AssessmentSystemService assessmentSystemService;
	@Autowired
	private MandateService mandateService;
	@Autowired
	private CommissionDecisionService commissionDecisionService;
	@Autowired
	private TagService tagService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CertificationBodyService certificationBodyService;
		
	
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
	
		

	@RequestMapping(value = "/ajax/standard-csn/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<StandardCsn>  searchInStandardCsns(@RequestBody @RequestParam("term") String term){
		return standardCsnService.autocomplete(term);
	}
	

	@RequestMapping(value = "/ajax/tag/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<String>  tagAutocomplete(final @RequestBody @RequestParam("term") String term){
		return tagService.autocomplete(term);
	}
	
	@RequestMapping(value = "/ajax/autocomplete/aono", method = RequestMethod.GET)
	public @ResponseBody List<NotifiedBody>  searchInNotifiedBodies(
			@RequestBody @RequestParam("term") String term,
			@RequestBody @RequestParam(value = "enabled", required = false) Boolean enabled){
		return notifiedBodyService.autocomplete(term, enabled);
	}
	
	@RequestMapping(value = "/ajax/companies", method = RequestMethod.GET)
	public @ResponseBody List<Company>  getCompanies(@RequestBody @RequestParam(required = false, value = "term") String query){
		if(StringUtils.isBlank(query)){
			return companyService.getAll();
		}
		return companyService.autocomplete(query);
	}
	
	@RequestMapping(value = "/ajax/certification-bodies", method = RequestMethod.GET)
	public @ResponseBody List<CertificationBody>  getCertificationBodies(@RequestBody @RequestParam(required = false, value = "term") String query){
		if(StringUtils.isBlank(query)){
			return certificationBodyService.getAll();
		}
		return certificationBodyService.autocomplete(query);
	}

	
	@RequestMapping(value = {"/ajax/standard-filter", "{lang}/ajax/standard-filter"})
	public @ResponseBody FilterDto  getFilterData(@RequestBody @RequestParam(value = "enabled", required = false) Boolean enabled){
		FilterDto data = new FilterDto();
		boolean useEnglish = !ContextHolder.isDefaultLang();
		if(enabled != null && enabled){
			data.setAssessmentSystems(assessmentSystemService.getAssessmentSystemsForPublic(), useEnglish);
			data.setStandardGroups(standardGroupService.getStandardGroupsForPublic(), useEnglish);
		}else{
			data.setAssessmentSystems(assessmentSystemService.getAllAssessmentSystems(), useEnglish);
			data.setStandardGroups(standardGroupService.getAllStandardGroups(), useEnglish);
		}
		data.setMandates(mandateService.getAllMandates());
		data.setCommissionDecisions(commissionDecisionService.getAll());
		return data;
	}
}
