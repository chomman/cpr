package cz.nlfnorm.web.controllers.admin.cpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.servlet.ModelAndView;

import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.entities.AssessmentSystem;
import cz.nlfnorm.entities.Country;
import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.entities.StandardCategory;
import cz.nlfnorm.entities.StandardChange;
import cz.nlfnorm.entities.StandardCsn;
import cz.nlfnorm.entities.StandardGroup;
import cz.nlfnorm.entities.StandardNotifiedBody;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.enums.StandardOrder;
import cz.nlfnorm.enums.StandardStatus;
import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.exceptions.CollisionException;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.parser.cpr.StandardParser;
import cz.nlfnorm.services.AssessmentSystemService;
import cz.nlfnorm.services.CountryService;
import cz.nlfnorm.services.MandateService;
import cz.nlfnorm.services.NotifiedBodyService;
import cz.nlfnorm.services.StandardCategoryService;
import cz.nlfnorm.services.StandardCsnService;
import cz.nlfnorm.services.StandardGroupService;
import cz.nlfnorm.services.StandardService;
import cz.nlfnorm.services.WebpageService;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.validators.admin.StandardValidator;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;
import cz.nlfnorm.web.editors.AssessmentSystemCollectionEditor;
import cz.nlfnorm.web.editors.CountryEditor;
import cz.nlfnorm.web.editors.IdentifiableByLongPropertyEditor;
import cz.nlfnorm.web.editors.LocalDateEditor;
import cz.nlfnorm.web.editors.NotifiedBodyEditor;
import cz.nlfnorm.web.editors.StandardCsnPropertyEditor;
import cz.nlfnorm.web.editors.StandardGroupEditor;
import cz.nlfnorm.web.editors.StandardPropertyEditor;
import cz.nlfnorm.web.forms.admin.StandardCsnForm;
import cz.nlfnorm.web.forms.admin.StandardForm;
import cz.nlfnorm.web.json.JsonResponse;
import cz.nlfnorm.web.json.JsonStatus;
import cz.nlfnorm.web.pagination.PageLink;
import cz.nlfnorm.web.pagination.PaginationLinker;


@Controller
public class StandardController extends AdminSupportController{
	
	public static final int CPR_TAB_INDEX = 1;
	private static final String UPDATED_STANDARD_PARAM = "updatedStandard";
	
	// services
	@Autowired
	private StandardService standardService;
	@Autowired
	private StandardGroupService standardGroupService;	
	@Autowired
	private CountryService countryService;
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private MandateService mandateService;
	@Autowired
	private AssessmentSystemService assessmentSystemService;
	@Autowired
	private StandardCsnService standardCsnService;
	@Autowired
	private WebpageService webpageService;
	@Autowired
	private StandardCategoryService standardCategoryService;
	
	// editors
	@Autowired
	private StandardGroupEditor standardGroupEditor;
	@Autowired
	private LocalDateEditor localDateEditor;
	@Autowired
	private CountryEditor countryEditor;
	@Autowired
	private NotifiedBodyEditor notifiedBodyEditor;
	@Autowired
	private AssessmentSystemCollectionEditor assessmentSystemCollectionEditor;
	@Autowired
	private StandardPropertyEditor standardPropertyEditor;
	@Autowired
	private StandardCsnPropertyEditor standardCsnPropertyEditor;
	
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
		binder.registerCustomEditor(Standard.class, this.standardPropertyEditor);
		binder.registerCustomEditor(StandardCsn.class, this.standardCsnPropertyEditor);
		binder.registerCustomEditor(NotifiedBody.class, this.notifiedBodyEditor);
		binder.registerCustomEditor(Set.class, "assessmentSystems", this.assessmentSystemCollectionEditor);
		binder.registerCustomEditor(StandardCategory.class, new IdentifiableByLongPropertyEditor<StandardCategory>( standardCategoryService ));
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
		final int count = standardService.getCountOfStandards(params).intValue();
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage, count);
		List<Standard> standards = standardService.getStandardPage(currentPage, params);
		params.put(Filter.NOTIFIED_BODY, getNotifiedBody(params.get(Filter.NOTIFIED_BODY)));
		model.put("standards", standards);
		model.put("count", count);
		model.put("paginationLinks", paginationLinks);
		model.put("orders", StandardOrder.getAll());
		model.put("standardStatuses", StandardStatus.getAll());
		model.put("tab", CPR_TAB_INDEX);
		model.put("params", params);
		model.put("webpage", webpageService.getWebpageByModule(WebpageModule.CPR_EHN_LIST));
		modelMap.put("model", model);
		if(params.get("import") != null){
			processImport();
		}
        return getTableItemsView();
    }
	
	private NotifiedBody getNotifiedBody(final Object id){
		Long nbid = ParseUtils.parseLongFromStringObject(id);
		if(nbid != null && nbid != 0){
			return notifiedBodyService.getNotifiedBodyById(nbid);
		}
		return null;
	}
	
	private void processImport(){
		StandardParser parser = new StandardParser();
		parser.setStandardService(standardService);
		parser.setAssessmentSystemService(assessmentSystemService);
		parser.setNotifiedBodyService(notifiedBodyService);
		parser.setStandardGroupService(standardGroupService);
		parser.setStandardCsnService(standardCsnService);
		parser.parse("http://nv190.peterjurkovic.sk/_nv_190.htm");
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
	
	private Standard getStandard(final long id) throws ItemNotFoundException{
		Standard standard = standardService.getStandardById(id);
		if(standard == null){
			createItemNotFoundError("Norma s ID: "+ id + " se v systému nenachází");
		}
		return standard;
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/preview/standard/{id}")
	public ModelAndView   standards(@PathVariable Long id, ModelMap map) throws ItemNotFoundException, PageNotFoundEception{
		User user = UserUtils.getLoggedUser();
		if(user == null || !user.isPortalUser()){
			throw new PageNotFoundEception();
		}
		final Standard standard  = getStandard(id);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("standards", new ArrayList<Standard>(){{
			add(standard);
		}});
		model.put("standard", standard);
		model.put("async", true);
		model.put("isCprView", standard.isCprCategory());
		map.put("isPreview", true);
		map.put("model", model);
		return new ModelAndView("/public/standard-preview", map);
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
	public String showEditForm1(HttpServletRequest request, @PathVariable Long standardId, ModelMap model) throws ItemNotFoundException {
		setEditFormView("cpr/standard-edit1");
		Standard form = getStandard(standardId);
		appendReferencedStandard(request, model);
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
	public String processSubmit(HttpServletRequest request, @PathVariable Long standardId,  @Valid Standard form, BindingResult result, ModelMap model) throws ItemNotFoundException {
		if(standardId == 0){
			setEditFormView("cpr/standard-add");
		}else{
			setEditFormView("cpr/standard-edit1");
		}
		standardValidator.validate(result, form);
		Long referencedStandardId = null;
		if (! result.hasErrors()) {
        	if(standardService.isStandardIdUnique(form.getStandardId(), form.getId())){
    			form = createOrUpdateBasicInfo(form);
    			if(form.getReplaceStandard() != null && standardService.updateReferencedStandard(form)){
    				referencedStandardId = form.getReplaceStandard().getId();
    				model.put(UPDATED_STANDARD_PARAM, form.getReplaceStandard());
    			}
				model.put("successCreate", true);
        	}else{
        		result.rejectValue("standardId", "cpr.standard.id.error.uniqe");
        	}
        }
		
		if(!result.hasErrors() && standardId == 0){
			String url = "redirect:/admin/cpr/standard/edit/" + form.getId() ;
			if(referencedStandardId != null){
				url += "?" + UPDATED_STANDARD_PARAM + "=1";
			}
			return url;
		}
		appendReferencedStandard(request, model);
		prepareModelForEditBasicInfo(form, model, standardId);
        return getEditFormView();
	}
	
	private void appendReferencedStandard(HttpServletRequest request, ModelMap map){
		String val = request.getParameter(UPDATED_STANDARD_PARAM);
		if(StringUtils.isNotBlank(val)){
			map.put(UPDATED_STANDARD_PARAM, standardService.getStandardById(Long.valueOf(val)));
		}
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
		Standard standard = getStandard(standardId);
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
		Standard standard = getStandard(standardId);
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
	public String removeStandardChange(@PathVariable Long standardId, @PathVariable Long id, ModelMap map, HttpServletRequest request) throws ItemNotFoundException {
		setEditFormView("cpr/standard-edit1");
		Standard standard = getStandard(standardId);
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
		Standard standard = getStandard(standardId);
		model.put("successDelete", true);
		standardService.removeReferences(standard);
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
		Standard standard = getStandard(standardId);
		if(form.getStandardGroup() != null && !standard.getStandardGroups().contains(form.getStandardGroup())){
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
		Standard standard = getStandard(standardId);
		final StandardGroup standardGroup = standardGroupService.getStandardGroupByid(id);
		if(standardGroup == null){
			createItemNotFoundError("Standard group with ID " + id + " was now found");
		}
		if(standard.getStandardGroups().contains(standardGroup)){
			standard.getStandardGroups().remove(standardGroup);
			standardService.saveOrUpdate(standard);
		}
		return "redirect:/admin/cpr/standard/edit/" + standardId;
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
    @RequestMapping(value = "/admin/cpr/standard/edit/{standardId}/csn", method = RequestMethod.GET)
    public String showCsns(@PathVariable Long standardId, ModelMap modelMap) throws ItemNotFoundException {
 		setEditFormView("cpr/standard-edit3");
 		prepareStandardCsnModel(modelMap, getStandard(standardId));
        return getEditFormView();
    }
    
    @RequestMapping(value = "/admin/cpr/standard/edit/{standardId}/csn", method = RequestMethod.POST)
    public String assignStandardCsn(@PathVariable Long standardId, ModelMap modelMap, @Valid StandardCsnForm form, BindingResult result) throws ItemNotFoundException {
 		setEditFormView("cpr/standard-edit3");
 		Standard standard = getStandard(standardId);
 		standardService.addStandardCsn(standard, form.getStandardCsn());
 		prepareStandardCsnModel(modelMap, standard);
        return getEditFormView();
    }
    
    private void prepareStandardCsnModel( ModelMap modelMap, Standard standard) throws ItemNotFoundException{
    	Map<String, Object> model = new HashMap<String, Object>();
 		setEditFormView("cpr/standard-edit3");
 		modelMap.addAttribute("standard", standard);
 		modelMap.addAttribute("standardCsn", new StandardCsnForm());
 		model.put("standardId", standard.getId());
 		model.put("tab", CPR_TAB_INDEX);
 		modelMap.put("model", model);
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
 		Standard standard = getStandard(standardId);
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
 		 Standard standard = getStandard(standardId);
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
    @RequestMapping(value = "/admin/cpr/standard/{standardId}/csn/delete/{csnId}", method = RequestMethod.GET)
    public String deleteCsn(@PathVariable Long standardId, @PathVariable Long csnId, ModelMap modelMap,HttpServletRequest request) throws ItemNotFoundException {
 		StandardCsn standardCsn = standardCsnService.getCsnById(csnId);
 		if(standardCsn == null){
 			createItemNotFoundError("ČSN s ID: " + csnId + " se v systému nenachází");
 		}
 		standardCsnService.deleteCsn(standardCsn);
        return String.format("redirect:/admin/cpr/standard/edit/%s/csn", standardId);
    }
    
    
    
    @RequestMapping(value = "/admin/cpr/standard/{standardId}/csn/unassign/{csnId}", method = RequestMethod.GET)
    public String unassignStandardCsn(@PathVariable Long standardId, @PathVariable Long csnId, ModelMap modelMap,HttpServletRequest request) throws ItemNotFoundException {
 		StandardCsn standardCsn = standardCsnService.getCsnById(csnId);
 		if(standardCsn == null){
 			createItemNotFoundError("ČSN s ID: " + csnId + " se v systému nenachází");
 		}
 		standardService.removeStandardCsn(getStandard(standardId),standardCsn );
        return String.format("redirect:/admin/cpr/standard/edit/%s/csn", standardId);
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
		Standard standard = getStandard(standardId);
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			standardService.unassigenNotifiedBody(Long.valueOf(request.getParameter("id")));
			return String.format("redirect:/admin/cpr/standard/edit/%s/notifiedbodies", standardId);
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
   public String  processNotifiedBodiesSubmit(@PathVariable Long standardId,@Valid  StandardNotifiedBody form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException{
	   setEditFormView("cpr/standard-edit4");
	   Standard standard = getStandard(standardId);
	   if(form.getNotifiedBody() != null && 
		!standardService.hasAssociatedNotifiedBody(form.getNotifiedBody(), standard)){
		   form.setStandard(standard);
		   standard.getNotifiedBodies().add(form);   	
		   standardService.mergeAndSetChanged(standard);
		   modelMap.put("successCreate", true);
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
		Standard standard = getStandard(standardId);
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
	   Standard standard = getStandard(standardId);
		if(form.getAssessmentSystems() != null){
			standard.setAssessmentSystems(form.getAssessmentSystems());
		}else{
			standard.setAssessmentSystems(new HashSet<AssessmentSystem>());
		}
		standardService.saveOrUpdate(standard);
		modelMap.put("successCreate", true);
		
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
			Standard form = getStandard(standardId);
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
		Standard standard = getStandard(standardId);
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
	private Standard createOrUpdateBasicInfo(Standard form) throws ItemNotFoundException{
		Standard standard = null;
	
		if(form.getId() == null || form.getId() == 0){
			standard = new Standard();
		}else{
			standard = getStandard(form.getId());
		}
		standard.setReleased(form.getReleased());
		standard.setCode(CodeUtils.toSeoUrl(form.getStandardId()));
		standard.setStandardId(form.getStandardId());
		standard.setCzechName(form.getCzechName());
		standard.setEnglishName(form.getEnglishName());
		standard.setStartValidity(form.getStartValidity());
		standard.setStopValidity(form.getStopValidity());
		standard.setStandardStatus(form.getStandardStatus());
		standard.setEnabled(form.getEnabled());
		standard.setReplaceStandard(form.getReplaceStandard());
		standard.setStatusDate(form.getStatusDate());
		standard.setStandardCategory(form.getStandardCategory());
		if(standard.getReleased() != null && standard.getStatusDate() == null){
			standard.setStatusDate(form.getReleased());
		}
		standardService.saveOrUpdate(standard);
		return standard;
	}
	
	
	
	private void createOrUpdateCsn(Standard standard, StandardCsn form) throws ItemNotFoundException{
		StandardCsn standardCsn = null;
		if(form.getId() == null || form.getId() == 0){
			standardCsn = new StandardCsn();
			
		}else{
			standardCsn = standard.getStandardCsnId(form.getId());
			if(standardCsn == null){
				createItemNotFoundError("ČSN s ID: " + form.getId() + " se v systému nenachází");
			}
		}
		standardCsn.merge(form);
		if(standardCsn.getId() == null){
			standardCsnService.createCsn(standardCsn);
			standard.getStandardCsns().add(standardCsn);
		}
		standardCsnService.updateReferencedStandard(standardCsn);
		standardService.updateStandard(standard);
	}
	
	
	
	
	private void prepareModelForEditBasicInfo(Standard form, ModelMap modelMap, Long standardId){
		Map<String, Object> model = appendBaseModel(form, modelMap);
		modelMap.addAttribute("standardForm", new StandardForm());
		model.put("standardStatuses", StandardStatus.getAll() );
		model.put("standardGroups", standardGroupService.getFiltredStandardGroups(form));
		model.put("standardCategories", standardCategoryService.getAll());
		modelMap.put("model", model); 
	}
	
	private void prepareModelForStandardChange(Standard standard, StandardChange form, ModelMap modelMap){
		Map<String, Object> model = appendBaseModel(standard, modelMap);
		modelMap.addAttribute("standardChange", form);
		model.put("showStandardChangeForm", true);
		modelMap.put("model", model); 
	}
	
	
	private void prepeareModelForNotifiedBodies(Standard standard, ModelMap modelMap){
		Map<String, Object> model = appendBaseModel(standard, modelMap);
		modelMap.addAttribute("standardNotifiedBody", new StandardNotifiedBody());
		model.put("notifiedBodies", getNotifiedBodies(standard.getNotifiedBodies()) );
		model.put("standard", standard);
		modelMap.put("model", model);
	}
	
	
	private List<NotifiedBody> getNotifiedBodies(Set<StandardNotifiedBody> excludeList) {
		List<NotifiedBody> notifiedBodies = notifiedBodyService.getNotifiedBodiesGroupedByCountry(null);
		for(StandardNotifiedBody snb : excludeList){
			notifiedBodies.remove(snb.getNotifiedBody());
		}
		return notifiedBodies;
	}
	
	private void prepeareModelForAssessmentSystems(Standard standard, ModelMap modelMap){
		Map<String, Object> model = appendBaseModel(standard, modelMap);
		model.put("assessmentSystem", assessmentSystemService.getAllAssessmentSystems());
		modelMap.put("model", model);
	}
	
	private void prepareModelForCsn(Standard standard, StandardCsn form,  ModelMap modelMap){
		Map<String, Object> model = appendBaseModel(standard, modelMap);
 		modelMap.addAttribute("csn", form);
 		model.put("standardName", standard.getStandardId());
 		model.put("standardStatuses", StandardStatus.getAll());
 		model.put("csnId", form.getId());
 		modelMap.put("model", model);
	}

	private Map<String, Object> appendBaseModel(Standard standard, ModelMap modelMap){
		Map<String, Object> model = new HashMap<String, Object>();
		modelMap.addAttribute("standard", standard);
		model.put("standardId", standard.getId());
		model.put("webpage", webpageService.getWebpageByModule(WebpageModule.CPR_EHN_LIST));
		modelMap.put("tab", CPR_TAB_INDEX);
		return model;
	}
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params, int currentPage, int count){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/admin/cpr/standards");
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount( count );
		return paginger.getPageLinks(); 
	}
	
	
	private Standard createEmptyForm(){
		Standard form = new Standard();
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
