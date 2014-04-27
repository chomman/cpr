package sk.peterjurkovic.cpr.web.controllers.admin.portal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.PortalService;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.PortalServiceService;
import sk.peterjurkovic.cpr.validators.CustomMessageInterpolator;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;

@Controller
public class PortalServiceController extends SupportAdminController {
	
	private final static String EDIT_MAPPING_URL = "/admin/portal/service/{serviceId}";
	
	
	@Autowired
	private PortalServiceService portalServiceService;
	
	
	
	public PortalServiceController(){
		setTableItemsView("portal/service-list");
		setEditFormView("portal/service-edit");
	}
	
	@RequestMapping("/admin/portal/services")
	public String handleServicesList(ModelMap modelMap){
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("services", portalServiceService.getAll());
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
		
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleServicesEdit(@PathVariable Long serviceId, ModelMap map, HttpServletRequest request) throws ItemNotFoundException{
		PortalService service =  new PortalService();
		if(serviceId != 0){
			service  = getPortalService(serviceId);
		}
		if(request.getParameter(SUCCESS_CREATE_PARAM) != null){
			map.put(SUCCESS_CREATE_PARAM, true);
		}
		prepareModel(map, service);
		return getEditFormView();
	}
	
	
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.POST)
	public String processSubmitPortalService(@PathVariable Long serviceId, @ModelAttribute("service") @Valid PortalService form, BindingResult result,  ModelMap map) throws ItemNotFoundException{
		if(result.hasErrors()){
			prepareModel(map, form);
			return getEditFormView();
		}
		Long id = createOrUpdate(form);
		return "redirect:" + EDIT_MAPPING_URL.replace("{serviceId}", id.toString()) + "?" + SUCCESS_CREATE_PARAM + "=1";
	}
	
	
	
	private void prepareModel(ModelMap map, PortalService service){
		map.addAttribute("service", service);
	}
	
	
	private Long createOrUpdate(PortalService form) throws ItemNotFoundException{
		PortalService service = null;
		if(form.getId() == null){
			service = new PortalService();
		}else{
			service = getPortalService(form.getId());
		}
		service.setCzechName(form.getCzechName());
		service.setEnglishName(form.getEnglishName());
		service.setPrice(form.getPrice());
		service.setDescription(form.getDescription());
		portalServiceService.createOrUpdate(service);
		return service.getId();
	}
	
	
	
	private PortalService getPortalService(final Long id) throws ItemNotFoundException{
		PortalService service = portalServiceService.getById(id);
		if(service == null){
			throw new ItemNotFoundException("Služba s ID: " + id + " se v systému nenachádzi");
		}
		return service;
	}
	
}
