package sk.peterjurkovic.cpr.web.controllers.admin.cpr;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardChange;
import sk.peterjurkovic.cpr.entities.StandardCsn;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.entities.Tag;
import sk.peterjurkovic.cpr.enums.StandardOrder;
import sk.peterjurkovic.cpr.exceptions.CollisionException;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.parser.cpr.StandardParser;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.CountryService;
import sk.peterjurkovic.cpr.services.MandateService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.RequirementService;
import sk.peterjurkovic.cpr.services.StandardCsnService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.validators.admin.StandardValidator;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;
import sk.peterjurkovic.cpr.web.editors.AssessmentSystemCollectionEditor;
import sk.peterjurkovic.cpr.web.editors.CountryEditor;
import sk.peterjurkovic.cpr.web.editors.LocalDateEditor;
import sk.peterjurkovic.cpr.web.editors.MandateCollectionEditor;
import sk.peterjurkovic.cpr.web.editors.NotifiedBodyCollectionEditor;
import sk.peterjurkovic.cpr.web.editors.StandardGroupEditor;
import sk.peterjurkovic.cpr.web.editors.TagEditor;
import sk.peterjurkovic.cpr.web.forms.admin.StandardForm;
import sk.peterjurkovic.cpr.web.json.JsonResponse;
import sk.peterjurkovic.cpr.web.json.JsonStatus;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;


@Controller
public class StandardController extends SupportAdminController{
	
	public static final int CPR_TAB_INDEX = 1;
	
	// services
	@Autowired
	private StandardService standardService;
	@Autowired
	private StandardGroupService standardGroupService;	
	@Autowired
	private CountryService countryService;
	@Autowired
	private RequirementService requirementService;
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private MandateService mandateService;
	@Autowired
	private AssessmentSystemService assessmentSystemService;
	@Autowired
	private StandardCsnService standardCsnService;
	
	// editors
	@Autowired
	private StandardGroupEditor standardGroupEditor;
	@Autowired
	private TagEditor tagEditor;
	@Autowired
	private LocalDateEditor localDateEditor;
	@Autowired
	private CountryEditor countryEditor;
	@Autowired
	private NotifiedBodyCollectionEditor notifiedBodiesEditor;
	@Autowired
	private AssessmentSystemCollectionEditor assessmentSystemCollectionEditor;
	@Autowired
	private MandateCollectionEditor mandateCollectionEditor;
	
	@Autowired
	private StandardValidator standardValidator;
	
	public StandardController(){
		setTableItemsView("cpr/standards");
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(StandardGroup.class, this.standardGroupEditor);
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
		binder.registerCustomEditor(Country.class, this.countryEditor);
		binder.registerCustomEditor(Tag.class, this.tagEditor);
		binder.registerCustomEditor(Set.class, "notifiedBodies", this.notifiedBodiesEditor);
		binder.registerCustomEditor(Set.class, "assessmentSystems", this.assessmentSystemCollectionEditor);
		binder.registerCustomEditor(Set.class, "mandates", this.mandateCollectionEditor);
    }
	
	
	/**
	 * Metoda kontroleru, ktora zobrazi skupiny výrobku
	 * 
	 * @param modelMap model
	 * @return String view, ktore bude interpretovane
	 */
	@RequestMapping("/admin/cpr/standards")
    public String showStandards(ModelMap modelMap,HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		List<Standard> standards = standardService.getStandardPage(currentPage, params);
		model.put("standards", standards);
		model.put("paginationLinks", paginationLinks);
		model.put("orders", StandardOrder.getAll());
		model.put("groups", standardGroupService.getAllStandardGroups());
		model.put("tab", CPR_TAB_INDEX);
		model.put("params", params);
		modelMap.put("model", model);
		if(params.get("import") != null){
			processImport();
		}
        return getTableItemsView();
    }
	
	private void processImport(){
		StandardParser parser = new StandardParser();
		parser.setStandardService(standardService);
		parser.setAssessmentSystemService(assessmentSystemService);
		parser.setNotifiedBodyService(notifiedBodyService);
		parser.setStandardGroupService(standardGroupService);
		parser.parse("http://www.sgpstandard.cz/editor/files/unmz/nv190/nv_190.htm");
	}
	
	/**
	 * Zobrazi view (stranku) s formularom umoznujuci pridat novu normu
	 * 
	 * @param Model model
	 * @return String view, nazov view s formularom
	 */
	@RequestMapping( value = "/admin/cpr/standard/add", method = RequestMethod.GET)
	public String showCreateForm(ModelMap model) {
		setEditFormView("cpr/standard-add");
		Standard form = createEmptyForm();
		prepareModelForEditBasicInfo(form, model, 0L);
        return getEditFormView();
	}
	
	
	
	/**
	 * Zobrazi JSP stranku s formularom, v ktorom je mozne editovat zakladne informacie o norme.
	 * 
	 * @param Long standardId
	 * @param Model model s informaciami o norme
	 * @return String JSP stranka
	 * @throws ItemNotFoundException, v pripade ak sa v systeme norma s danym ID nenachadza
	 */
	@RequestMapping( value = "/admin/cpr/standard/edit/{standardId}", method = RequestMethod.GET)
	public String showEditForm1(@PathVariable Long standardId, ModelMap model) throws ItemNotFoundException {
		setEditFormView("cpr/standard-edit1");
		Standard form = standardService.getStandardById(standardId);
		if(form == null){
			createItemNotFoundError("Norma s ID: "+ standardId + " se v systému nenachází");
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
	 * @throws ItemNotFoundException 
	 */
	@RequestMapping( value = "/admin/cpr/standard/edit/{standardId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long standardId,  @Valid Standard form, BindingResult result, ModelMap model) throws ItemNotFoundException {
		if(standardId == 0){
			setEditFormView("cpr/standard-add");
		}else{
			setEditFormView("cpr/standard-edit1");
		}
		standardValidator.validate(result, form);
		if (! result.hasErrors()) {
        	if(standardService.isStandardIdUnique(form.getStandardId(), form.getId())){
        		try {
        			form = createOrUpdateBasicInfo(form);
					model.put("successCreate", true);
				} catch (CollisionException e) {
					result.rejectValue("timestamp", "error.collision", e.getMessage());
				}	
        	}else{
        		result.rejectValue("standardId", "cpr.standard.id.error.uniqe");
        	}
        }
		
		if(!result.hasErrors() && standardId == 0){
			return "redirect:/admin/cpr/standard/edit/" + form.getId();
		}
		prepareModelForEditBasicInfo(form, model, standardId);
        return getEditFormView();
	}
	
	/**
	 * Spracuje poziadavku zobrazenia formulara, pre pridanie/editaciu zmeny normy 
	 * 
	 * @param standardId
	 * @param id
	 * @param map
	 * @return
	 * @throws ItemNotFoundException
	 */
	@RequestMapping( value = "/admin/cpr/standard/edit/{standardId}/standard-change/{id}", method = RequestMethod.GET)
	public String showStandardChangeForm(@PathVariable Long standardId, @PathVariable Long id, ModelMap map) throws ItemNotFoundException {
		setEditFormView("cpr/standard-edit1");
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createItemNotFoundError("Norma s ID: "+ standardId + " se v systému nenachází");
		}
		StandardChange form = null;
		if(id == null || id == 0){
			form = new StandardChange();
			form.setId(0l);
		}else{
			form = standard.getStandardChangeById(id);
			if(form == null){
				createItemNotFoundError("Změna normy s ID: "+ id + " se v systému nenachází");
			}
		}
		prepareModelForStandardChange(standard, form, map);
        return getEditFormView();
	}
	
	
	@RequestMapping( value = "/admin/cpr/standard/edit/{standardId}/standard-change/{id}", method = RequestMethod.POST)
	public String processSubmitStandardChange(@PathVariable Long standardId, @Valid StandardChange form, BindingResult result, ModelMap model) throws ItemNotFoundException {
		setEditFormView("cpr/standard-edit1");
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createItemNotFoundError("Norma s ID: "+ standardId + " se v systému nenachází");
		}
		if(StringUtils.isBlank(form.getChangeCode())){
			result.rejectValue("changeCode", "cpr.standard.changes.error");
		}else{
			if(standard.createOrUpdateStandardChange(form)){
				standardService.updateStandard(standard);
				model.put("successCreate", true);
			}
		}
		prepareModelForStandardChange(standard, form, model);
        return getEditFormView();
	}
	
	
	@RequestMapping( value = "/admin/cpr/standard/edit/{standardId}/standard-change/delete/{id}", method = RequestMethod.GET)
	public String removeStandardChange(@PathVariable Long standardId, @PathVariable Long id, ModelMap map) throws ItemNotFoundException {
		setEditFormView("cpr/standard-edit1");
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createItemNotFoundError("Norma s ID: "+ standardId + " se v systému nenachází");
		}
		if(standard.removeStandardChange(id)){
			standardService.mergeStandard(standard);
		}
		return "redirect:/admin/cpr/standard/edit/" + standardId;
	}
	
	
	/**
	 * Odstranie normu na zaklade daneho ID
	 * 
	 * @param Long ID normy, ktora ma byt odstranena
	 * @param Model model
	 * @param HttpServletRequest request
	 * @return String JSP stranka, zobrazena po odstraneni polozky
	 * @throws ItemNotFoundException, v pripade ak sa polozka s danym ID v systeme nenachdza
	 */
	@RequestMapping( value = "/admin/cpr/standard/delete/{standardId}", method = RequestMethod.GET)
	public String deleteStandard(@PathVariable Long standardId, ModelMap model, HttpServletRequest request) throws ItemNotFoundException {
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			 createStandardNotFound(standardId); 
		}
		model.put("successDelete", true);
		standardService.deleteStandard(standard);
        return showStandards(model, request);
	}
	
		
	
	
	
	
	/**
	 * Spracuje odoslany formular, urceny na priradenie skupiny vyrobku k danej norme
	 * 
	 * @param standardId
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 * @throws ItemNotFoundException
	 */
	@RequestMapping( value = "/admin/cpr/standard/edit/{standardId}/standard-group/add", method = RequestMethod.POST)
	public String processStandardGroupAdding(@PathVariable Long standardId,  @Valid  StandardForm form, BindingResult result, ModelMap model) throws ItemNotFoundException {
		
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createItemNotFoundError("Standard with ID " +standardId + " was now found");
		}
		
		if(!standard.getStandardGroups().contains(form.getStandardGroup())){
			standard.getStandardGroups().add(form.getStandardGroup());
			standardService.saveOrUpdate(standard);
		}
		
		return "redirect:/admin/cpr/standard/edit/" + standardId;
	}
	
	
	
	/**
	 * Zrusi priradenie danej skupiny k danej norme
	 * 
	 * @param standardId
	 * @param id
	 * @return
	 * @throws ItemNotFoundException
	 */
	@RequestMapping( value = "/admin/cpr/standard/edit/{standardId}/standard-group/delete/{id}")
	public String processStandardGroupAdding(@PathVariable Long standardId, @PathVariable Long id) throws ItemNotFoundException {
		Standard standard = standardService.getStandardById(standardId);
		final StandardGroup standardGroup = standardGroupService.getStandardGroupByid(id);
		if(standard == null || standardGroup == null){
			createItemNotFoundError("Standard with ID " +standardId + " was now found");
		}
		if(standard.getStandardGroups().contains(standardGroup)){
			standard.getStandardGroups().remove(standardGroup);
			standardService.saveOrUpdate(standard);
		}
		return "redirect:/admin/cpr/standard/edit/" + standardId;
	}
	
	
	 //##################################################
	 //#  2	Requirements methods
	 //##################################################

	/**
	 * Zobrazi pozadavky danej normy
	 * 
	 * @param modelMap
	 * @param request
	 * @return String view
	 * @throws ItemNotFoundException 
	 */
	@RequestMapping("/admin/cpr/standard/edit/{standardId}/requirements")
   public String showRequirements(@PathVariable Long standardId, ModelMap modelMap,HttpServletRequest request) throws ItemNotFoundException {
		Map<String, Object> map = new HashMap<String, Object>();
		setEditFormView("cpr/standard-edit2");
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createStandardNotFound(standardId); 
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
		map.put("country", country);
		map.put("countries", countryService.getAllCountries());
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
	 * @throws ItemNotFoundException 
	 */
   @RequestMapping("/admin/cpr/standard/edit/{standardId}/req/{requirementId}")
   public String showRequirementForm(@PathVariable Long standardId,@PathVariable Long requirementId, ModelMap modelMap,HttpServletRequest request) throws ItemNotFoundException {
		
		setEditFormView("cpr/standard-edit2-requirement");
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createStandardNotFound(standardId); 
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
	 * Spracuje odoslany formular a upravi, resp vytvori novu poziadavku. V pripade ak je ID poziadavky 0, jedna sa o vytvorenie novej polozky,
	 * inak o editaciu uz existujucej polozky.
	 * 
	 * @param Long identifikator normy standardId
	 * @param Long identifikator poziadavky requirementId
	 * @param Requirement form, odoslany formular
	 * @param result
	 * @param ModelMap model
	 * @return String JSP stranka
	 * @throws ItemNotFoundExceptionm, v pripade ak sa norma, alebo poziadavka na zaklade daneho ID v systeme nenachadza
	 */
    @RequestMapping( value = "/admin/cpr/standard/edit/{standardId}/req/{requirementId}", method = RequestMethod.POST)
	public String processRequirementSubmit(@PathVariable Long standardId,@PathVariable Long requirementId, @Valid  Requirement form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException {
       setEditFormView("cpr/standard-edit2-requirement");
       Standard standard = standardService.getStandardById(standardId);
       if(standard == null){
    	   createStandardNotFound(standardId); 
       }
       if (result.hasErrors()) {
			prepareModelForRequirement(modelMap, standard, form, requirementId);
       }else{
       	createOrUpdateRequrement(standard, form);
       	
       	modelMap.put("successCreate", true);
       	if(requirementId == 0){
       		form = createEmptyRequirementForm();
       		prepareModelForRequirement(modelMap, standard, form, requirementId);
       	}
       }
       return getEditFormView();
	}
   
    
    /**
     * Odstrani poziadavok na zaklade daneho identifikatora
     * 
     * @param Long requirementId
     * @param ModelMap model
     * @return String JSP stranka
     * @throws ItemNotFoundException, v pripade ak nie je Rquirement s danym ID v systeme evidovany
     */
    @RequestMapping( value = "/admin/cpr/standard/requirement/delete/{requirementId}", method = RequestMethod.GET)
	public String deleteRequirement(@PathVariable Long requirementId,  ModelMap modelMap) throws ItemNotFoundException {
		
    	Requirement requirement =  requirementService.getRequirementById(requirementId);
		if(requirement == null){
			createItemNotFoundError("Požadavek s ID: " +requirementId + " se v systému nenachází");
		}
		Long id = requirement.getStandard().getId();
		requirementService.deleteRequirement(requirement);
		modelMap.put("successDelete", true);
		return "forward:/admin/cpr/standard/edit/"+ id +"/requirements";
	}
    
    
    
    private void createOrUpdateRequrement(Standard standard, Requirement form) throws ItemNotFoundException{
    	Requirement requirement = null;
    	
    	if(form.getId() == null || form.getId() == 0){
    		requirement = new Requirement();
    	}else{
    		requirement = requirementService.getRequirementById(form.getId());
    		if(requirement == null){
    			createItemNotFoundError("Požadavek s ID: " + form.getId() + " se v systému nenachází");
    		}
    	}
    	
    	requirement.setName(form.getName());
    	requirement.setNote(form.getNote());
    	requirement.setLevels(form.getLevels());
    	requirement.setSection(form.getSection());
    	requirement.setNpd(form.getNpd());
    	requirement.setStandard(standard);
    	requirement.setCountry(form.getCountry());
    	requirementService.saveOrUpdateRequirement(requirement);
    }
	
     //##################################################
 	 //#  3	Csnonline
 	 //##################################################
    
    /**
     * Zobrazi zoznam evidovanych CSN k danej norme
     * 
     * @param Standar identifikator normy
     * @param ModelMap
     * @param request
     * @return String JSP stranak
     * @throws ItemNotFoundException, v pripade ak sa norma s danym ID v systeme nenachadza
     */
    @RequestMapping("/admin/cpr/standard/edit/{standardId}/csn")
    public String showCsns(@PathVariable Long standardId, ModelMap modelMap,HttpServletRequest request) throws ItemNotFoundException {
 		Map<String, Object> map = new HashMap<String, Object>();
 		setEditFormView("cpr/standard-edit3");
 		Standard standard = standardService.getStandardById(standardId);
 		if(standard == null){
 			createStandardNotFound( standardId );
 		}
 		modelMap.addAttribute("standard", standard);
 		map.put("standardId", standardId);
 		map.put("tab", CPR_TAB_INDEX);
 		modelMap.put("model", map);
         return getEditFormView();
    }
    
    /**
     * Zobrazi formular pre editaciu, resp pre pridanie novej CSN k norme. AK je ID csn 0, 
     * jedna sa o pridanie noevej CSN, inak o editaciu
     * 
     * @param Long standardId
     * @param Long csnId
     * @param ModelMap model
     * @param request
     * @return String JSP stranka
     * @throws ItemNotFoundException, v pripade ak sa norma s danym ID v systeme nenachadza
     */
    @RequestMapping(value = "/admin/cpr/standard/edit/{standardId}/csn-edit/{csnId}", method = RequestMethod.GET)
    public String showCsnEditForm(@PathVariable Long standardId, @PathVariable Long csnId, ModelMap modelMap,HttpServletRequest request) throws ItemNotFoundException {
 		setEditFormView("cpr/standard-edit3-edit");
 		Standard standard = standardService.getStandardById(standardId);
 		if(standard == null){
 			createStandardNotFound( standardId );
 		}
 		StandardCsn form = null;
		if(csnId == null || csnId == 0){
			form = createEmptyCsnForm();
		}else{
			form = standardCsnService.getCsnById(csnId);
		}
		prepareModelForCsn(standard, form, modelMap);
        return getEditFormView();
    }
    
    
    /**
     * Spracuje odoslany formular, obsahujuci CSN.
     * 
     * @param Long standardId
     * @param Long csnId
     * @param StandardCsn form
     * @param result
     * @param ModelMap model
     * @return String JSP stranka
     * @throws ItemNotFoundException, v pripade ak sa norma s danym ID v systeme nenachadza
     */
    @RequestMapping(value = "/admin/cpr/standard/edit/{standardId}/csn-edit/{csnId}", method = RequestMethod.POST)
    public String processCsnSubmit(@PathVariable Long standardId,@PathVariable Long csnId, @Valid  StandardCsn form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException {
 		setEditFormView("cpr/standard-edit3-edit");
 		 Standard standard = standardService.getStandardById(standardId);
 	       if(standard == null){
 	    	  createStandardNotFound( standardId );
 	       }
 	       if (!result.hasErrors()) {
				createOrUpdateCsn(standard, form);
				modelMap.put("successCreate", true);
				if(csnId == 0){
					form = createEmptyCsnForm();
	 	       }
 	       }
	 	   prepareModelForCsn(standard, form, modelMap);
        return getEditFormView();
    }
    
    /**
     * Odstrani CSN  na zaklade danoe ID
     * 
     * @param Long csnId
     * @param ModelMap model
     * @param request
     * @return String view
     * @throws ItemNotFoundException, v pripade ak sa CSN s danym ID v systeme nenachadza
     */
    @RequestMapping(value = "/admin/cpr/standard/csn/delete/{csnId}", method = RequestMethod.GET)
    public String deleteCsn(@PathVariable Long csnId, ModelMap modelMap,HttpServletRequest request) throws ItemNotFoundException {
 		StandardCsn StandardCsn = standardCsnService.getCsnById(csnId);
 		if(StandardCsn == null){
 			createItemNotFoundError("ČSN s ID: " + csnId + " se v systému nenachází");
 		}
 		standardCsnService.deleteCsn(StandardCsn);
        return "forward:/admin/cpr/standard/edit/"+StandardCsn.getStandard().getId()+"/csn";
    }
    
     //##################################################
  	 //#  4	NO/AO methods
  	 //##################################################
    
    
    /**
	 * Zobrazi pozadavky danej normy
	 * 
	 * @param modelMap
	 * @param request
	 * @return String view
     * @throws ItemNotFoundException 
	 */
    @RequestMapping("/admin/cpr/standard/edit/{standardId}/notifiedbodies")
    public String showNotifiedBodies(@PathVariable Long standardId, ModelMap modelMap,HttpServletRequest request) throws ItemNotFoundException {
		setEditFormView("cpr/standard-edit4");
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createStandardNotFound( standardId );
		}
		prepeareModelForNotifiedBodies(standard, modelMap);
        return getEditFormView();
   }
   
    
    
    /**
     * Spracuje odoslany formular, s vybranymi NOAO a priradi ich k danej norme.
     * 
     * @param Long standardId
     * @param Standard form
     * @param result
     * @param ModelMap model
     * @return String JSP stranka
     * @throws ItemNotFoundException, ak sa norma s danym ID v systeme nenachadza
     */
   @RequestMapping(value = "/admin/cpr/standard/edit/{standardId}/notifiedbodies", method = RequestMethod.POST)
   public String  processNotifiedBodiesSubmit(@PathVariable Long standardId,@Valid  Standard form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException{
	   setEditFormView("cpr/standard-edit4");
	   Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createStandardNotFound( standardId );
		}
		try {
			standardValidator.validateCollision(form, standard);
			if(form.getNotifiedBodies() != null){
				standard.setNotifiedBodies(form.getNotifiedBodies());
			}else{
				standard.setNotifiedBodies(new HashSet<NotifiedBody>());
			}
			standardService.saveOrUpdate(standard);
			standard.setTimestamp(standard.getChanged().toDateTime().getMillis());
			modelMap.put("successCreate", true);
		} catch (CollisionException e) {
			result.rejectValue("timestamp", "error.collision", e.getMessage());
		}
	   prepeareModelForNotifiedBodies(standard, modelMap);
	   return getEditFormView();
   }
   
   
   
   //##################################################
   //#  5	AssesmentsSystems
   //##################################################
  @RequestMapping("/admin/cpr/standard/edit/{standardId}/other")
  public String showOtherSettings(@PathVariable Long standardId, ModelMap modelMap,HttpServletRequest request) throws ItemNotFoundException {
		setEditFormView("cpr/standard-edit5");
		
		Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createStandardNotFound( standardId );;
		}

		prepeareModelForAssessmentSystems(standard, modelMap);
      return getEditFormView();
 }
 
  
 /**
  * Ulozi odoslany formular, obsahujici mandary a systemi podudzovania, ktore maju byt priradene, k danej norme.
  * V priapde ak formular neobsahuje polozku, ktora bola v minulosti priradena, je odstranena.
  *  
  * @param Long ID danej nromy, ku kotrej maju polozky pripradene
  * @param Standard form
  * @param result
  * @param ModelMap model
  * @return String JSP stranka
  * @throws ItemNotFoundException, v pripade ak sa norma s danym ID v systeme nenachadza
  */
 @RequestMapping(value = "/admin/cpr/standard/edit/{standardId}/other", method = RequestMethod.POST)
 public String  processOtherSettingsSubmit(@PathVariable Long standardId,@Valid  Standard form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException{
	   setEditFormView("cpr/standard-edit5");
	   Standard standard = standardService.getStandardById(standardId);
		if(standard == null){
			createStandardNotFound( standardId );
		}
		try{
			standardValidator.validateCollision(form, standard);
			if(form.getAssessmentSystems() != null){
				standard.setAssessmentSystems(form.getAssessmentSystems());
			}else{
				standard.setAssessmentSystems(new HashSet<AssessmentSystem>());
			}
			standard.setCumulative(form.getCumulative());
			standardService.saveOrUpdate(standard);
			standard.setTimestamp(standard.getChanged().toDateTime().getMillis());
			modelMap.put("successCreate", true);
		}catch(CollisionException e){
			result.rejectValue("timestamp", "error.collision", e.getMessage());
		}
		prepeareModelForAssessmentSystems(standard, modelMap);
	   return getEditFormView();
 }
    
	 //##################################################
	 //#  6	describe
	 //##################################################
 	
 	/**
 	 * Zobrazi JSP stranku, v ktorej je mozne evidovat podrobnejsi popis k norme.
 	 * 
 	 * @param Long identifikator danej normy
 	 * @param ModelMap model
 	 * @param request
 	 * @return String JSP stranka s formularom
 	 * @throws ItemNotFoundException, ak sa norma s danym ID v systeme nenachadza
 	 */
	 @RequestMapping("/admin/cpr/standard/edit/{standardId}/describe")
	 public String showStandardText(@PathVariable Long standardId, ModelMap modelMap,HttpServletRequest request) throws ItemNotFoundException {
			setEditFormView("cpr/standard-edit6");
			
			Standard form = standardService.getStandardById(standardId);
			if(form == null){
				createStandardNotFound( standardId );
			}
			prepareModelForEditBasicInfo(form, modelMap, standardId);
	     return getEditFormView();
	}
	
	 /**
	  * Spracuje asynchrone odoslany formular, resp JSON. Jakson automaticky prekonvertuje json na pozadovany objekt
	  * 
	  * @param Standard form
	  * @param Long standardId
	  * @return @ResponseBody, odpoved vo formatu JSP
	  */
	@RequestMapping(value = "/admin/cpr/standard/edit/{standardId}/describe", method = RequestMethod.POST,  headers = {"content-type=application/json"})
	public @ResponseBody JsonResponse  processAjaxTextSubmit(@Valid @RequestBody  Standard form, @PathVariable Long standardId){
		JsonResponse response = new JsonResponse();
		
		   Standard standard = standardService.getStandardById(standardId);
			if(standard == null){
				return response;
			}
			standard.setText(form.getText());
			standardService.saveOrUpdate(standard);
			response.setStatus(JsonStatus.SUCCESS);
		   return response;
	}
	
	/**
	 * Spracuje odoslany formular, a ulozi zmeny
	 * 
	 * @param Long standardId
	 * @param Standar form
	 * @param result
	 * @param ModelMap model
	 * @return String JSP stranka
	 * @throws ItemNotFoundException, ak sa norma s danym ID v systeme nenachadza
	 */
	@RequestMapping(value = "/admin/cpr/standard/edit/{standardId}/describe", method = RequestMethod.POST)
	public String  processTextSubmit(@PathVariable Long standardId,@Valid  Standard form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException{
		   setEditFormView("cpr/standard-edit6");
		   Standard standard = standardService.getStandardById(standardId);
			if(standard == null){
				createStandardNotFound( standardId );
			}
			standard.setText(form.getText());
			standardService.saveOrUpdate(standard);
			
			modelMap.put("successCreate", true);
			prepareModelForEditBasicInfo(form, modelMap, standardId);
		   return getEditFormView();
	}

	@RequestMapping(value = "/admin/cpr/standard/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<Standard>  autocomplete(@RequestBody @RequestParam("term") String query){
		return standardService.autocomplateSearch(query, null);
	}

 
    /**
     * Vytvori, alebo aktualizuje normu.
     * 
     * @param Standard form, odoslany formular
     * @return Long id, vytvorenej/upravenej normy
     * @throws CollisionException 
     * @throws ItemNotFoundException 
     */
	private Standard createOrUpdateBasicInfo(Standard form) throws CollisionException, ItemNotFoundException{
		Standard standard = null;
	
		if(form.getId() == null || form.getId() == 0){
			standard = new Standard();
		}else{
			standard = standardService.getStandardById(form.getId());
			if(standard == null){
				createStandardNotFound(form.getId());
			}
			standardValidator.validateCollision(form, standard);
			standardService.clearStandardTags(standard);
		}
		Set<Tag> tags = form.getTags();
		if(CollectionUtils.isNotEmpty(tags)){
			for(Tag tag : tags){
				tag.setStandard(standard);
			}
			standard.setTags(tags);
		}
		standard.setCode(CodeUtils.toSeoUrl(form.getStandardId()));
		standard.setStandardId(form.getStandardId());
		standard.setReplacedStandardId(form.getReplacedStandardId());
		standard.setCzechName(form.getCzechName());
		standard.setStartValidity(form.getStartValidity());
		standard.setStopValidity(form.getStopValidity());
		//standard.setStandardGroup(form.getStandardGroup());
		standard.setEnabled(form.getEnabled());
		
		standardService.saveOrUpdate(standard);
		if(standard.getChanged() != null){
			form.setTimestamp(standard.getChanged().toDateTime().getMillis());
		}
		return standard;
	}
	
	
	
	private void createOrUpdateCsn(Standard standard, StandardCsn form) throws ItemNotFoundException{
		StandardCsn StandardCsn = null;
		if(form.getId() == null || form.getId() == 0){
			StandardCsn = new StandardCsn();
		}else{
			StandardCsn = standardCsnService.getCsnById(form.getId());
			if(StandardCsn == null){
				createItemNotFoundError("ČSN s ID: " + form.getId() + " se v systému nenachází");
			}
		}

		StandardCsn.setCsnName(form.getCsnName());
		StandardCsn.setNote(form.getNote());
		StandardCsn.setCsnOnlineId(form.getCsnOnlineId());
		StandardCsn.setStandard(standard);
		standardCsnService.saveOrUpdate(StandardCsn);
	}
	
	
	
	
	private void prepareModelForEditBasicInfo(Standard form, ModelMap map, Long standardId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("standard", form);
		map.addAttribute("standardForm", new StandardForm());
		model.put("standardId", standardId);
		model.put("standardGroups", standardGroupService.getFiltredStandardGroups(form));
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	private void prepareModelForStandardChange(Standard standard, StandardChange form, ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("standard", standard);
		map.addAttribute("standardChange", form);
		model.put("standardId", standard.getId());
		model.put("showStandardChangeForm", true);
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
	
	
	private void prepeareModelForNotifiedBodies(Standard standard, ModelMap modelMap){
		Map<String, Object> map = new HashMap<String, Object>();
		modelMap.addAttribute("standard", standard);
		map.put("standardId", standard.getId());
		map.put("notifiedBodies", notifiedBodyService.getNotifiedBodiesGroupedByCountry(null));
		map.put("standardnotifiedBodies", standard.getNotifiedBodies());
		map.put("tab", CPR_TAB_INDEX);
		modelMap.put("model", map);
	}
	
	private void prepeareModelForAssessmentSystems(Standard standard, ModelMap modelMap){
		Map<String, Object> map = new HashMap<String, Object>();
		modelMap.addAttribute("standard", standard);
		map.put("standardId", standard.getId());
		map.put("assessmentSystem", assessmentSystemService.getAllAssessmentSystems());
		modelMap.put("model", map);
	}
	
	private void prepareModelForCsn(Standard standard, StandardCsn form,  ModelMap modelMap){
		Map<String, Object> map = new HashMap<String, Object>();
 		modelMap.addAttribute("csn", form);
 		map.put("standardId", standard.getId());
 		map.put("standardName", standard.getStandardId());
 		map.put("csnId", form.getId());
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
	
	private StandardCsn createEmptyCsnForm(){
		StandardCsn form = new StandardCsn();
		form.setId(0L);
		return form;
	}
	
	
	void createStandardNotFound(Long standardId) throws ItemNotFoundException{
		createItemNotFoundError("Norma s ID: "+ standardId + " se v systému nenachází");
	}
	
}
