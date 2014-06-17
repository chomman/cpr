package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.entities.Country;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.CountryService;

@Controller
public class CountryController extends QuasarSupportController {

	private final static int TAB = 6; 
	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/countries";
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/country/{cId}";
	
	@Autowired
	private CountryService countryService;
	
	public CountryController(){
		setTableItemsView("country-list");
		setEditFormView("country-edit");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String showNandoCodes(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("countries", countryService.getAllCountries());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.GET)
	public String showEditForm(ModelMap modelMap, HttpServletRequest request, @PathVariable long cId) throws ItemNotFoundException {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		Country form = new Country();
		if(cId != 0){
			form = countryService.getById(cId);
			validateNotNull(form, "Country was not found.");
		}
		prepareModel(modelMap, form);
		return getEditFormView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.POST)
	public String processSubmit(ModelMap modelMap, @Valid @ModelAttribute(COMMAND) Country form, BindingResult result) throws ItemNotFoundException{
		if(result.hasErrors()){
			prepareModel(modelMap, form);
			return getEditFormView();
		}
		final Long id = createOrUpdate(form);
		return successUpdateRedirect(FORM_MAPPING_URL.replace("{cId}", id+""));
	}
	
	
	private Long createOrUpdate(Country form) throws ItemNotFoundException{
		Country country = null;
		if(form.getId() == null){
			country = new Country();
		}else{
			country = countryService.getById(form.getId());
			validateNotNull(country, "Could not create/update country. Not found.");
		}
		country.merge(form);
		countryService.saveOrUpdate(country);
		return country.getId();
	}
	
	private void prepareModel(ModelMap map, final Country form){
		Map<String, Object> model = new HashMap<>();
		appendTabNo(model, TAB);
		map.addAttribute(COMMAND, form);
		appendModel(map, model);
	}
	
}
