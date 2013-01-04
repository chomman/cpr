package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.controllers.SupportController;
import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.services.CountryService;

@Controller
@SessionAttributes("country")
public class CountrySettingsController extends SupportController {
	
	public static final int TAB_INDEX = 3;

	@Autowired
	private CountryService countryService;
	
	
	
	/**
	 * Metoda kontroleru, ktora zobrazi vsetky evidovane clenske staty EU
	 * 
	 * @param modelMap model
	 * @return String view
	 */
	@RequestMapping("/admin/settings/countries")
	public String showCountries(ModelMap modelMap){
		Map<String, Object> model = new HashMap<String, Object>();
		List<Country> countries = countryService.getAllCountries();
		model.put("countries", countries);
		model.put("tab", TAB_INDEX);
		logger.info("showing countires.. size: " + countries);
		modelMap.put("model", model);
        return "/"+ Constants.ADMIN_PREFIX +"/settings-countries";
	}
	
	
	/**
	 * Metoda kontroleru, ktora zobrazi formular na pridanie, resp. editaciu noveho clenskeho statu.
	 * 
	 * 
	 * @param Long countryId, ID clenskeho statu, v pripade ak je 0, jedna sa o pridanie novej polozky
	 * @param model Model
	 * @return String view
	 */
	@RequestMapping( value = "/admin/settings/countries/edit/{countryId}", method = RequestMethod.GET)
	public String showEditForm(@PathVariable Long countryId,  ModelMap model){
		
		Country form = null;
		if(countryId == 0){
			form = new Country();
			form.setId(0);
		}else{
			form = countryService.getCountryById(countryId);
			if(form == null){
				model.put("notFoundError", true);
				return "/"+ Constants.ADMIN_PREFIX +"/settings-countries-edit";
			}
		}
		prepareModel(form, model, countryId);
		return "/"+ Constants.ADMIN_PREFIX +"/settings-countries-edit";
	}
	
	
	/**
	 * Metoda kontroleru, ktora odstrani clensky stat. 
	 * 
	 * @param Long countryId
	 * @param map Model 
	 * @return String view
	 */
	@RequestMapping( value = "/admin/settings/countries/delete/{countryId}", method = RequestMethod.GET)
	public String deleteGroup(@PathVariable Long countryId,  ModelMap modelMap) {
						
		Country country = countryService.getCountryById(countryId);
		if(countryId == null){
			modelMap.put("notFoundError", true);
			return "/"+ Constants.ADMIN_PREFIX +"/settings-countries";
		}
		countryService.deleteCountry(country);
		modelMap.put("successDelete", true);
		
        return showCountries(modelMap);
	}
	
	/**
	 * Metoda kontroleru, ktora ulozi odoslany formular.
	 * 
	 * @param countryId ID skupiny. V prípade ak je hodnota 0, jedna sa o pridanie novej polozky, 
	 * 			inak sa jedna o etidatciu
	 * @param map Model 
	 * @return String view
	 */
	@RequestMapping( value = "/admin/settings/countries/edit/{countryId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long countryId,  @Valid  Country form, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			prepareModel(form, model, countryId);
        }else{
        	createOrUpdateCountry(form);
        	model.put("successCreate", true);
        }
		
        return "/"+ Constants.ADMIN_PREFIX +"/settings-countries-edit";
	}
	
	
	private void prepareModel(Country form, ModelMap map, Long countryId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("country", form);
		model.put("countryId", countryId);
		model.put("tab", TAB_INDEX);
		map.put("model", model); 
	}
	
	
	
	private Country createOrUpdateCountry(Country form){
		Country country = null;
			
		if(form.getId() == 0){
			country = new Country();
		}else{
			country = countryService.getCountryById(form.getId());
			if(country == null){
				createItemNotFountError();
			}
		}
		
		country.setCode(form.getCode());
		country.setCountryName(form.getCountryName());
		countryService.saveOrUpdateCountry(country);
		return country;
	}
}
