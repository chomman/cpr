package cz.nlfnorm.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.entities.BasicSettings;
import cz.nlfnorm.services.BasicSettingsService;

@Controller
public class BasicSettingsController extends AdminSupportController {
		
	@Autowired
	private BasicSettingsService basicSettingsService;
	
	public BasicSettingsController(){
		setViewName("settings");
	}
	
	
	/**
	 * Zobrazi formular so zakladnym nastavenim systemu
	 * 
	 * @param ModelMap model
	 * @return String JSP stranka
	 */
	@RequestMapping("/admin/settings/basic")
	public String showBasicSettingsForm(ModelMap modelMap){
		setEditFormView("settings-basic");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab",1);
		modelMap.addAttribute("basicSettings", basicSettingsService.getBasicSettings());
		modelMap.put("model", model);
		return getEditFormView();
	}
	
	
	/**
	 * Spracuje formular so zakladnym nastavenim systemu 
	 * 
	 * @param BasicSettingsform
	 * @param BindingResult result
	 * @param ModelMap model
	 * @return String jsp stranka
	 */
	@RequestMapping(value = "/admin/settings/basic", method = RequestMethod.POST)
	public String processSubmit(@Valid BasicSettings form, BindingResult result, ModelMap modelMap){
		setEditFormView("settings-basic");
		if(!result.hasErrors()){
			updateSettings(form);
			modelMap.put("successCreate", true);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab",1);
		modelMap.addAttribute("basicSettings", form);
		modelMap.put("model", model);
		return getEditFormView();
	}
	
	
	
	public void updateSettings(BasicSettings form){
		BasicSettings settings = basicSettingsService.getBasicSettings();
		settings.setSystemEmail(form.getSystemEmail());
		if(StringUtils.isNotBlank(form.getVersion())){
			settings.setVersion(form.getVersion());
		}
		basicSettingsService.updateBasicSettings(settings);
	}
	
	
}
