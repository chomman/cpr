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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.Mandate;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.MandateService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;

@Controller
public class MandateController extends SupportAdminController {

	public static final int CPR_TAB_INDEX = 5;
	private static final String MANDATE_URL = "/admin/cpr/mandates";
	private static final String SUCCESS_PARAM = "success";
	
	@Autowired
	private MandateService mandateService;
	
	public MandateController(){
		setTableItemsView("cpr/mandates");
		setEditFormView("cpr/mandates-edit");
	}
	
	
	/**
	 * Zobrazi evidovane mandaty
	 * 
	 * @param modelMap model
	 * @param HttpServletRequest poziadavka
	 * @return String view
	 */
	@RequestMapping(MANDATE_URL)
	public String showMandates(ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		
		List<Mandate> mandates = mandateService.getMandatePage(currentPage);
		model.put("mandates", mandates);
		model.put("paginationLinks", paginationLinks);
		model.put("tab", CPR_TAB_INDEX);	
		modelMap.put("model", model);
        return getTableItemsView();
	}
	
	
	
	/**
	 * Zobrazi formular pre pridanie, resp. editovanie mandatu. V pripade ak mandateId je nula,
	 * jedna sa o pridanie noveho mandatu, inak o editovanie.
	 * 
	 * @param Long mandateId
	 * @param ModelMap model
	 * @return String view s editacnym formularom
	 */
	@RequestMapping( value = MANDATE_URL +"/edit/{mandateId}", method = RequestMethod.GET)
	public String showForm(@PathVariable Long mandateId,  ModelMap model, HttpServletRequest request) {
						
		Mandate form = null;
	
		if(mandateId == 0){
			form = createEmptyForm();
		}else{
			form = mandateService.getMandateById(mandateId);
			if(form == null){
				model.put("notFoundError", true);
				return getEditFormView();
			}
		}
		if(request.getParameter(SUCCESS_PARAM) != null){
			model.put("successCreate", true);
		}
		prepareModel(form, model, mandateId);
        return getEditFormView();
	}
	
	
	
	/**
	 * Spracuje odoslany formular. V pripade ak je ID mandatu nula, jedna sa o vytvorenie novej polozky.
	 * Inak sa jedna o editaciu existujucej polozky.
	 * 
	 * @param Long mandateId
	 * @param Mandat form validny formular, ktory sa spracuje a ulozi
	 * @param BindingResult result
	 * @param model 
	 * @return String view
	 * @throws ItemNotFoundException 
	 */
	@RequestMapping( value = MANDATE_URL +"/edit/{mandateId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long mandateId,  @Valid  Mandate form, BindingResult result, ModelMap model) throws ItemNotFoundException {

		if (result.hasErrors()) {
			prepareModel(form, model, mandateId);
        }else{
        	createOrUpdate(form);
        	model.put("successCreate", true);
        	if(mandateId == 0){
        		form = createEmptyForm();
        		prepareModel(form, model, mandateId);
        	}
        }
        return getEditFormView();
	}
	
	
	
	/**
	 * Odtrani mandat, v pripade ak je to mozne. Tz ak sa nenachadza v norme.
	 * 
	 * @param mandateId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping( value = MANDATE_URL +" /delete/{mandateId}", method = RequestMethod.GET)
	public String deleteGroup(@PathVariable Long mandateId,  ModelMap modelMap, HttpServletRequest request) {
						
		Mandate mandate = mandateService.getMandateById(mandateId);
		if(mandate == null){
			modelMap.put("notFoundError", true);
			return getTableItemsView();
		}
		if(mandateService.canBeDeleted(mandate)){
			mandateService.deleteMandate(mandate);
			modelMap.put("successDelete", true);
		}else{
			modelMap.put("isNotEmptyError", true);
		}
		
        return showMandates(modelMap, request);
	}
	
	
	private Mandate createOrUpdate(Mandate form) throws ItemNotFoundException{
		Mandate mandate = null;
			
		if(form.getId() == 0){
			mandate = new Mandate();
		}else{
			mandate = mandateService.getMandateById(form.getId());
			if(mandate == null){
				createItemNotFoundError("Mandát s ID:" + form.getId() + " se v systému nenachází");
				throw new ItemNotFoundException("item not found");
			}
		}
		mandate.setMandateName(form.getMandateName());
		mandate.setMandateFileUrl(form.getMandateFileUrl());
		mandateService.saveOrUpdateMandate(mandate);
		return mandate;
	}	
	
	
	@RequestMapping( value = MANDATE_URL +"/edit/{mandateId}/change/add", method = RequestMethod.POST)
	public String processAssignmentMandate(@PathVariable Long mandateId,  ModelMap model, HttpServletRequest request, @Valid Mandate mandateChange, BindingResult result) throws ItemNotFoundException {
		
		Mandate mandate = mandateService.getMandateById(mandateId);
		
		if(mandate == null){
			createItemNotFoundError("Mandate was not found. [id="+mandateId+"]");
		}
		
		if(!mandate.getChanges().contains(mandateChange) && !mandate.equals(mandateChange)){
			mandate.getChanges().add(mandateChange);
			mandateService.updateMandate(mandate);
			return "redirect:"+MANDATE_URL+"/edit/"+mandateId + "?" +SUCCESS_PARAM+"=1";
		}
		
		return "redirect:"+MANDATE_URL+"/edit/"+mandateId;
	}
	
	
	@RequestMapping( value = MANDATE_URL+ "/edit/{mandateId}/change/delete/{id}")
	public String removeMandate(@PathVariable Long mandateId, @PathVariable Long id) throws ItemNotFoundException {
		Mandate mandate = mandateService.getMandateById(mandateId);
		Mandate mandateChange = mandateService.getMandateById(id);
		if(mandate != null && mandateChange != null){
			if(mandate.getChanges().remove(mandateChange)){
				mandateService.updateMandate(mandate);
				return "redirect:"+MANDATE_URL+"/edit/"+mandateId + "?" +SUCCESS_PARAM+"=1";
			}
		}
		return "redirect:"+MANDATE_URL+"/edit/"+mandateId;
	}
	
	
	
	private void prepareModel(Mandate form, ModelMap map, Long mandateId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("mandate", form);
		map.addAttribute("mandateChange", new Mandate());
		model.put("mandateId", mandateId);
		model.put("mandates", mandateService.getAllMandates());
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	
	
	private Mandate createEmptyForm(){
		Mandate form = new Mandate();
		form.setId(0L);
		return form;
	}
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params,int currentPage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl(MANDATE_URL);
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount( mandateService.getCountOfMandates().intValue() );
		return paginger.getPageLinks(); 
	}
}
