package cz.nlfnorm.web.controllers.admin.portal;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.entities.BasicSettings;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;

@Controller
public class PortalSettingsController extends AdminSupportController {
	
	private final static String EDIT_MAPPING_URL = "/admin/portal/settings";
	
	@Autowired
	private BasicSettingsService basicSettingsService;
	
	public PortalSettingsController(){
		setEditFormView("portal/settings");
	}
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleSettingsEdit(ModelMap map) throws ItemNotFoundException{
		prepareModel(map, basicSettingsService.getBasicSettings());
		return getEditFormView();
	}
	
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.POST)
	public String handleProcessSubmit(@Valid @ModelAttribute("settings") BasicSettings settings, BindingResult result, ModelMap map) throws ItemNotFoundException{
		if(result.hasErrors()){
			prepareModel(map, settings);
			return getEditFormView();
		}
		updateSettings(settings);
		map.put(SUCCESS_CREATE_PARAM, true);
		return getEditFormView();
	}
	
	public void updateSettings(BasicSettings form){
		Validate.notNull(form);
		BasicSettings settings = basicSettingsService.getBasicSettings();
		Validate.notNull(settings);

		settings.setCompanyName(form.getCompanyName());
		settings.setCity(form.getCity());
		settings.setStreet(form.getStreet());
		settings.setZip(form.getZip());
		
		settings.setIco(form.getIco());
		settings.setDic(form.getDic());
		
		settings.setCzAccountNumber(form.getCzAccountNumber());
		settings.setCzIban(form.getCzIban());
		settings.setCzSwift(form.getCzSwift());
		
		settings.setEuAccountNumber(form.getEuAccountNumber());
		settings.setEuIban(form.getEuIban());
		settings.setEuSwift(form.getEuSwift());
		
		settings.setInvoiceEmai(form.getInvoiceEmai());
		settings.setPhone(form.getPhone());
		
		basicSettingsService.updateBasicSettings(settings);
	}
	
	
	private void prepareModel(ModelMap map, BasicSettings settings) throws ItemNotFoundException{
		map.addAttribute("settings", settings);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab",4);
		map.put("model", model);
	}
	
}
