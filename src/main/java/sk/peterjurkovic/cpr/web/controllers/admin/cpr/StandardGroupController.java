package sk.peterjurkovic.cpr.web.controllers.admin.cpr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import sk.peterjurkovic.cpr.entities.CommissionDecision;
import sk.peterjurkovic.cpr.entities.Mandate;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.parser.cpr.StandardGroupParser;
import sk.peterjurkovic.cpr.services.CommissionDecisionService;
import sk.peterjurkovic.cpr.services.MandateService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;
import sk.peterjurkovic.cpr.web.editors.CommissionDecisionEditor;
import sk.peterjurkovic.cpr.web.editors.MandatePropertyEditor;
import sk.peterjurkovic.cpr.web.forms.admin.MandateForm;

@Controller
@SessionAttributes("standardGroup")
public class StandardGroupController extends SupportAdminController {
	
	
	public static final int CPR_TAB_INDEX = 2;
	private static final String DEFAULT_URL = "/admin/cpr/groups";
	private static final String SUCCESS_PARAM = "successCreate";

	@Autowired
	private StandardGroupService standardGroupService;
	@Autowired
	private CommissionDecisionService commissionDecisionService;
	@Autowired
	private CommissionDecisionEditor commissionDecisionEditor;
	@Autowired
	private MandateService mandateService;
	@Autowired
	private MandatePropertyEditor mandatePropertyEditor;

	public StandardGroupController(){
		setTableItemsView("cpr/standard-group-list");
		setEditFormView("cpr/standard-group-edit");
		setViewName("cpr/standard-group-import");
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(CommissionDecision.class, this.commissionDecisionEditor);
		binder.registerCustomEditor(Mandate.class, this.mandatePropertyEditor);
	}
	
	/**
	 * Metoda kontroleru, ktora zobrazi skupiny výrobku
	 * 
	 * @param modelMap model
	 * @return String view, ktore bude interpretovane
	 */
	@RequestMapping(DEFAULT_URL)
    public String showCprGroupsPage(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<StandardGroup> groups = standardGroupService.getAllStandardGroups();
		model.put("groups", groups);
		model.put("tab", CPR_TAB_INDEX);
		modelMap.put("model", model);
        return getTableItemsView();
    }
	
	
	
	
	/**
	 * Metoda kontroleru, ktora odstrani skupinu vyrobku. V pripade ak skupina obsahuje 1 a viac vyrobkov,
	 * zobrazi sa chybove upozornenie a nedovoli odstranit polozku
	 * 
	 * @param Long standardGroupId, id skupiny
	 * @param map Model 
	 * @return String view
	 */
	@RequestMapping( value = DEFAULT_URL + "/delete/{standardGroupId}", method = RequestMethod.GET)
	public String deleteGroup(@PathVariable Long standardGroupId,  ModelMap modelMap) {
						
		StandardGroup standardGroup = standardGroupService.getStandardGroupByid(standardGroupId);
		if(standardGroup == null){
			modelMap.put("notFoundError", true);
			return getTableItemsView();
		}
		
		if(standardGroupService.getCountOfStandardsInGroup(standardGroup) > 0){
			modelMap.put("isNotEmptyError", true);
		}else{
			standardGroupService.deleteStandardGroup(standardGroup);
			modelMap.put("successDelete", true);
		}
		
        return showCprGroupsPage(modelMap);
	}
	
	
	
	/**
	 * Metoda kontroleru, ktora zobrazi formulár pre pridanie, resp. editovanie skupiny vyrobkov.
	 * Ak je ID skupiny 0, tak sa jedna o pridanie novej polozky.
	 * 
	 * @param standardGroupId ID skupiny. V prípade ak je 0, jedna sa o pridanie novej skupiny
	 * @param modelMap model, data
	 * @return String view obsahujuce formular
	 * @throws ItemNotFoundException 
	 */
	@RequestMapping( value = DEFAULT_URL + "/edit/{standardGroupId}", method = RequestMethod.GET)
	public String showForm(@PathVariable Long standardGroupId,  ModelMap model, HttpServletRequest request) throws ItemNotFoundException {
						
		StandardGroup form = null;
	
		if(standardGroupId == 0){
			form = createEmptyForm();
		}else{
			form = standardGroupService.getStandardGroupByid(standardGroupId);
			if(form == null){
				createItemNotFoundError("Standard group was not found [id="+standardGroupId+"]");
			}
		}
		String successParam = request.getParameter(SUCCESS_PARAM);
		if(successParam != null && successParam.equals("1")){
			model.put(SUCCESS_PARAM, true);
		}
		prepareModel(form, model, standardGroupId);
        return getEditFormView();
	}
	
	
	
	
	/**
	 * Metoda kontroleru, ktora ulozi odoslany formular.
	 * 
	 * @param standardGroupId ID skupiny. V prípade ak je hodnota 0, jedna sa o pridanie novej polozky, 
	 * 			inak sa jedna o etidatciu
	 * @param map Model 
	 * @return String view
	 * @throws ItemNotFoundException 
	 */
	@RequestMapping(  value = DEFAULT_URL + "/edit/{standardGroupId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long standardGroupId, @Valid StandardGroup form, BindingResult result, ModelMap model) throws ItemNotFoundException {

		if (result.hasErrors()) {
			prepareModel(form, model, standardGroupId);
        }else{
        	createOrUpdate(form);
        	model.put("successCreate", true);
        	if(standardGroupId == 0){
        		return "redirect:"+DEFAULT_URL+"/edit/"+form.getId() + "?" +SUCCESS_PARAM+"=1";
        	}else{
        		prepareModel(form, model, form.getId());
        	}
        }
        return getEditFormView();
	}
	
	
	@RequestMapping(value = DEFAULT_URL +"/edit/{standardGroupId}/mandate/add", method =  RequestMethod.POST)
	public String processAssignmentMandate(@PathVariable Long standardGroupId,  ModelMap model, HttpServletRequest request, @Valid MandateForm mandateChangeForm, BindingResult result) throws ItemNotFoundException {
		
		StandardGroup standardGroup = standardGroupService.getStandardGroupByid(standardGroupId);
		final Mandate mandate = mandateChangeForm.getMandate();
		if(mandate == null){
			 createItemNotFoundError("Mandate was not found");
		}
		
		if(!standardGroup.getMandates().contains(mandate)){
			standardGroup.getMandates().add(mandate);
			standardGroupService.updateStandardGroup(standardGroup);
			return "redirect:"+DEFAULT_URL+"/edit/"+standardGroupId + "?" +SUCCESS_PARAM+"=1";
		}
		
		return "redirect:"+DEFAULT_URL+"/edit/"+standardGroupId;
	}
	
	
	@RequestMapping( value = DEFAULT_URL+ "/edit/{standardGroupId}/mandate/delete/{id}")
	public String removeMandate(@PathVariable Long standardGroupId, @PathVariable Long id) throws ItemNotFoundException {
		StandardGroup standardGroup = standardGroupService.getStandardGroupByid(standardGroupId);
		Mandate mandate = mandateService.getMandateById(id);
		if(standardGroup != null && mandate != null){
			if(standardGroup.getMandates().remove(mandate)){
				mandateService.updateMandate(mandate);
				return "redirect:"+DEFAULT_URL+"/edit/"+standardGroupId + "?" +SUCCESS_PARAM+"=1";
			}
		}
		return "redirect:"+DEFAULT_URL+"/edit/"+standardGroupId;
	}
	
	
	@RequestMapping( value = DEFAULT_URL+ "/import", method = RequestMethod.GET)
	public String showImportPage(ModelMap modelMap) {
		return getViewName();
	}
	
	@RequestMapping( value =  DEFAULT_URL +"/import", method = RequestMethod.POST)
	public String processImport(ModelMap modelMap) {
		StandardGroupParser parser = new StandardGroupParser();
		parser.setCommissionDecisionService(commissionDecisionService);
		parser.setMandateService(mandateService);
		parser.setStandardGroupService(standardGroupService);
		parser.parse("http://nv190.peterjurkovic.sk/skupiny.htm");
		modelMap.put("successCreate", true);
		return getViewName();
	}
	
	
	
	private void prepareModel(StandardGroup form, ModelMap map, Long standardGroupId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("standardGroup", form);
		model.put("standardGroupId", standardGroupId);
		model.put("commissionDecisions", commissionDecisionService.getAll());
		if(standardGroupId != 0){
			map.addAttribute("mandateForm", new MandateForm());
			model.put("mandates", mandateService.getFiltredMandates(form));
		}
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	
	
	
	private StandardGroup createOrUpdate(StandardGroup form) throws ItemNotFoundException{
		StandardGroup standardGroup = null;
			
		if(form.getId() == null || form.getId() == 0){
			standardGroup = new StandardGroup();
		}else{
			standardGroup = standardGroupService.getStandardGroupByid(form.getId());
			if(standardGroup == null){
				createItemNotFoundError("Norma s ID: "+ form.getId() + " se v systému nenachází");
			}
		}
		
		standardGroup.setCode(form.getCode());
		standardGroup.setCzechName(form.getCzechName());
		standardGroup.setEnglishName(form.getEnglishName());;
		standardGroup.setCommissionDecision(form.getCommissionDecision());
		standardGroup.setEnabled(form.getEnabled());
		standardGroupService.saveOrdUpdateStandardGroup(standardGroup);
		form.setId(standardGroup.getId());
		return standardGroup;
	}
	
	
	
	private StandardGroup createEmptyForm(){
		StandardGroup form = new StandardGroup();
		form.setId(0L);
		return form;
	}
}
