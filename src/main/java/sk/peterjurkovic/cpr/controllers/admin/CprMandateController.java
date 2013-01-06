package sk.peterjurkovic.cpr.controllers.admin;

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
import sk.peterjurkovic.cpr.pagination.PageLink;
import sk.peterjurkovic.cpr.pagination.PaginationLinker;
import sk.peterjurkovic.cpr.services.MandateService;
import sk.peterjurkovic.cpr.utils.RequestUtils;

@Controller
public class CprMandateController extends SupportAdminController {

	public static final int CPR_TAB_INDEX = 5;
	
	@Autowired
	private MandateService mandateService;
	
	public CprMandateController(){
		setTableItemsView("cpr-mandates");
		setEditFormView("cpr-mandates-edit");
	}
	
	
	/**
	 * Zobrazi evidovane mandaty
	 * 
	 * @param modelMap model
	 * @param HttpServletRequest poziadavka
	 * @return String view
	 */
	@RequestMapping("/admin/cpr/mandates")
	public String showMandates(ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		
		List<PageLink> paginationLinks = new PaginationLinker(request, params).getNavigatorLinsk();
		
		
		List<Mandate> mandates = mandateService.getMandatePage(1);
		logger.info("pocet mandatu: " + mandates.size());
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
	@RequestMapping( value = "/admin/cpr/mandates/edit/{mandateId}", method = RequestMethod.GET)
	public String showForm(@PathVariable Long mandateId,  ModelMap model) {
						
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
	 */
	@RequestMapping( value = "/admin/cpr/mandates/edit/{mandateId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long mandateId,  @Valid  Mandate form, BindingResult result, ModelMap model) {

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
	
	
	private void createOrUpdate(Mandate form){
		Mandate mandate = null;
			
		if(form.getId() == 0){
			mandate = new Mandate();
		}else{
			mandate = mandateService.getMandateById(form.getId());
			if(mandate == null){
				createItemNotFoundError();
			}
		}
		mandate.setMandateName(form.getMandateName());
		mandate.setMandateFileUrl(form.getMandateFileUrl());
		mandateService.saveOrUpdateMandate(mandate);
	}	
	
	
	private void prepareModel(Mandate form, ModelMap map, Long mandateId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("mandate", form);
		model.put("mandateId", mandateId);
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	
	
	private Mandate createEmptyForm(){
		Mandate form = new Mandate();
		form.setId(0L);
		return form;
	}
}
