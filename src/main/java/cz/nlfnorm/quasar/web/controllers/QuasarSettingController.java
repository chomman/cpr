package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.quasar.entities.QuasarSettings;
import cz.nlfnorm.quasar.services.QuasarSettingsService;

@Controller
public class QuasarSettingController extends QuasarSupportController {
	
	private final static int TAB = 7; 
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/settings";

	@Autowired
	private QuasarSettingsService quasarSettingsService;
	
	public QuasarSettingController(){
		setEditFormView("quasar-settings");
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.GET)
	public String handleForm(ModelMap modelMap) {
		prepareModel(modelMap, quasarSettingsService.getSettings());
		return getEditFormView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.POST)
	public String handleSubmit(ModelMap modelMap, @Valid @ModelAttribute QuasarSettings form, BindingResult result) {
		if(!result.hasErrors()){
			quasarSettingsService.update(form);
			appendSuccessCreateParam(modelMap);
		}
		prepareModel(modelMap, form);
		return getEditFormView();
	}
	
	private void prepareModel(ModelMap map, final QuasarSettings form){
		Map<String, Object> model = new HashMap<>();
		appendTabNo(model, TAB);
		model.put("yearAgo", new LocalDate().minusDays(365));
		model.put("wholeLastYear", new LocalDate().minusYears(1));
		model.put("today", new LocalDate());
		map.addAttribute("quasarSettings", form);
		if(form.isUse365DaysInterval()){
			model.put("oneYearAgo", new LocalDate().minusYears(1));
			model.put("threeYearsAgo", new LocalDate().minusYears(3));
		}else{
			model.put("oneYearAgo", new LocalDate().minusYears(1).withDayOfMonth(1).withMonthOfYear(1));
			model.put("threeYearsAgo", new LocalDate().minusYears(3).withDayOfMonth(1).withMonthOfYear(1));
		}
		appendModel(map, model);
	}
}
