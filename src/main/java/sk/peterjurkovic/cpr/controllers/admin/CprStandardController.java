package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.pagination.PageLink;
import sk.peterjurkovic.cpr.pagination.PaginationLinker;
import sk.peterjurkovic.cpr.services.CountryService;
import sk.peterjurkovic.cpr.services.RequirementService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.editors.CountryEditor;
import sk.peterjurkovic.cpr.web.editors.DateTimeEditor;
import sk.peterjurkovic.cpr.web.editors.StandardGroupEditor;


@Controller
public class CprStandardController extends SupportAdminController {
	
	public static final int CPR_TAB_INDEX = 1;
	
	@Autowired
	private StandardService standardService;
	@Autowired
	private StandardGroupService standardGroupService;
	@Autowired
	private StandardGroupEditor standardGroupEditor;
	@Autowired
	private DateTimeEditor dateTimeEditor;
	@Autowired
	private CountryEditor countryEditor;
	@Autowired
	private CountryService countryService;
	@Autowired
	private RequirementService requirementService;
	
	public CprStandardController(){
		setTableItemsView("cpr-standards");
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(StandardGroup.class, this.standardGroupEditor);
		binder.registerCustomEditor(DateTime.class, this.dateTimeEditor);
		binder.registerCustomEditor(Country.class, this.countryEditor);
    }
	
	/**
	 * Metoda kontroleru, ktora zobrazi skupiny výrobku
	 * 
	 * @param modelMap model
	 * @return String view, ktore bude interpretovane
	 */
	@RequestMapping("/admin/cpr/standards")
    public String showCprGroupsPage(ModelMap modelMap,HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		List<Standard> standards = standardService.getStandardPage(currentPage, params);
		model.put("standards", standards);
		model.put("paginationLinks", paginationLinks);
		model.put("tab", CPR_TAB_INDEX);	
		modelMap.put("model", model);
        return getTableItemsView();
    }
	
	
	 //##################################################
	 //#	Requirements methods
	 //##################################################

	
	/**
	 * Zobrazi pozadavky danej normy
	 * 
	 * @param modelMap
	 * @param request
	 * @return String view
	 */
	@RequestMapping("/admin/cpr/standard/edit/{standardId}/requirements")
   public String showRequirements(@PathVariable Long standardId, ModelMap modelMap,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		setEditFormView("cpr-standard-edit2");
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createItemNotFoundError();
		}
		Country country = null;
		if(request.getParameter("country") != null){
			country = countryService.getCountryById(ParseUtils.parseLongFromStringObject(request.getParameter("country")));
		}
		
		if(country == null){
			map.put("requirements", standard.getRequirements());
		}else{
			map.put("requirements", requirementService.getRequirementsByCountryAndStandard(country, standard));
		}
		modelMap.addAttribute("standard", standard);
		map.put("standardId", standardId);
		map.put("tab", CPR_TAB_INDEX);
		modelMap.put("model", map);
        return getEditFormView();
   }
	
	/**
	 * Zobrazi formular pre pridanie, resp. editaciu noveho pzadavku. V pripade ak je ID
	 * pozadavku 0, jedna sa o udalost vytvorenia novej polozky, inak o editaciu.
	 * 
	 * @param modelMap
	 * @param request
	 * @return String view
	 */
	@RequestMapping("/admin/cpr/standard/edit/{standardId}/req/{requirementId}")
   public String showRequirementForm(@PathVariable Long standardId,@PathVariable Long requirementId, ModelMap modelMap,HttpServletRequest request) {
		
		setEditFormView("cpr-standard-edit2-requirement");
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createItemNotFoundError();
		}
		Requirement form = null;
		if(requirementId == null || requirementId == 0){
			form = createEmptyRequirementForm();
		}else{
			form = requirementService.getRequirementById(requirementId);
		}
		
		prepareModelForRequirement(modelMap, standard, form, requirementId);
        return getEditFormView();
   }
	
	
	/**
	 * Zobrazi view (stranku) s formularom umoznujuci pridat novu normu
	 * 
	 * @param Model model
	 * @return String view, nazov view s formularom
	 */
	@RequestMapping( value = "/admin/cpr/standard/add", method = RequestMethod.GET)
	public String showCrateForm(ModelMap model) {
		setEditFormView("cpr-standard-add");
		Standard form = createEmptyForm();
		prepareModelForEditBasicInfo(form, model, 0L);
        return getEditFormView();
	}
	
	
	
	@RequestMapping( value = "/admin/cpr/standard/edit/{standardId}", method = RequestMethod.GET)
	public String showEditForm1(@PathVariable Long standardId, ModelMap model) {
		setEditFormView("cpr-standard-edit1");
		
		Standard form = standardService.getStandardById(standardId);
		if(form == null){
			createItemNotFoundError();
		}
		
		prepareModelForEditBasicInfo(form, model, standardId);
        return getEditFormView();
	}
	
		
	/**
	 * Spracuje odoslany formular, obsahujuci zakladne informacie o norme.
	 * 
	 * 
	 * @param standardId
	 * @param form
	 * @param result
	 * @param model
	 * @return String view
	 */
	@RequestMapping( value = "/admin/cpr/standard/edit/{standardId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long standardId,  @Valid  Standard form, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			prepareModelForEditBasicInfo(form, model, standardId);
        }else{
        	if(standardService.isStandardIdUnique(form.getStandardId(), form.getId())){
        		createOrUpdateBasicInfo(form);
	        	model.put("successCreate", true);
	        	if(standardId == 0){
	        		form = createEmptyForm();
	        		prepareModelForEditBasicInfo(form, model, standardId);
	        	}
        	}else{
        		result.rejectValue("standardId", "cpr.standard.id.error.uniqe");
        		prepareModelForEditBasicInfo(form, model, standardId);
        	}
        }
		
        return getEditFormView();
	}
	
	
	private Long createOrUpdateBasicInfo(Standard form){
		Standard standard = null;
	
		if(form.getId() == null || form.getId() == 0){
			standard = new Standard();
		}else{
			standard = standardService.getStandardById(form.getId());
			if(standard == null){
				createItemNotFoundError();
			}
		}
		
		standard.setCode(CodeUtils.toSeoUrl(form.getStandardId()));
		standard.setStandardId(form.getStandardId());
		standard.setReplacedStandardId(form.getReplacedStandardId());
		standard.setStandardName(form.getStandardName());
		standard.setStartValidity(form.getStartValidity());
		standard.setStopValidity(form.getStopValidity());
		standard.setStartConcurrentValidity(form.getStartConcurrentValidity());
		standard.setStandardGroup(form.getStandardGroup());
		standard.setEnabled(form.getEnabled());
		
		standardService.saveOrUpdate(standard);
		return standard.getId();
	}
	
	private void prepareModelForEditBasicInfo(Standard form, ModelMap map, Long standardId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("standard", form);
		model.put("standardId", standardId);
		model.put("groups", standardGroupService.getAllStandardGroups());
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	private void prepareModelForRequirement(ModelMap modelMap, Standard standard,  Requirement form, Long requirementId){
		Map<String, Object> map = new HashMap<String, Object>();
		modelMap.addAttribute("standard", standard);
		modelMap.addAttribute("requirement", form);
		map.put("standardId", standard.getId());
		map.put("requirementId", requirementId);
		map.put("countries", countryService.getAllCountries());
		map.put("tab", CPR_TAB_INDEX);
		modelMap.put("model", map);
	}
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params,int currentPage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/admin/cpr/standards");
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount( standardService.getCountOfStandards(params).intValue() );
		return paginger.getPageLinks(); 
	}
	
	
	private Standard createEmptyForm(){
		Standard form = new Standard();
		form.setId(0L);
		return form;
	}
	
	private Requirement createEmptyRequirementForm(){
		Requirement form = new Requirement();
		form.setId(0L);
		return form;
	}
	
}
