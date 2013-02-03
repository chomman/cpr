package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import sk.peterjurkovic.cpr.entities.Address;
import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.CountryService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.validators.admin.NotifiedBodyValidator;
import sk.peterjurkovic.cpr.web.editors.CountryEditor;


@Controller
@SessionAttributes("notifiedBody")
public class CprNotifiedBodyController extends SupportAdminController {
	
	public static final int CPR_TAB_INDEX = 3;
	
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private CountryEditor countryEditor;
	@Autowired
	private NotifiedBodyValidator notifiedBodyValidator;
	
	
	public CprNotifiedBodyController(){
		setEditFormView("cpr-notifiedbodies-edit");
		setTableItemsView("cpr-notifiedbodies");
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Country.class, this.countryEditor);
    }
	
	/**
	 * Metoda kontroleru, ktora zobrazi notifikovane/autorizovane osoby
	 * 
	 * @param modelMap
	 * @return String view
	 */
	@RequestMapping("/admin/cpr/notifiedbodies")
    public String showNotifiedBodiesPage(ModelMap modelMap) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<NotifiedBody> groups = notifiedBodyService.getAllNotifiedBodies();
		model.put("notifiedBodies", groups);
		model.put("tab", CPR_TAB_INDEX);
		modelMap.put("model", model);
        return getTableItemsView();
        
    }
	
	
	
	/**
	 * Metoda kontroleru, ktora zobrazi formular pre editovanie, resp pridanie novej notifikovanej/autorizovanej osoby.
	 * Ak je notifiedBodyId == 0, jedna sa o udalost pridania novej osoby, inak sa jedna o editaciu.
	 * 
	 * 
	 * @param Long notifiedBodyId 
	 * @param Model model
	 * @return String view
	 */
	@RequestMapping( value = "/admin/cpr/notifiedbodies/edit/{notifiedBodyId}", method = RequestMethod.GET)
	public String showForm(@PathVariable Long notifiedBodyId,  ModelMap model) {
						
		NotifiedBody form = null;
	
		// vytvorenie novej polozky
		if(notifiedBodyId == 0){
			form = new NotifiedBody();
			form.setId(0L);
		}else{
			// editacia polozky
			form = notifiedBodyService.getNotifiedBodyById(notifiedBodyId);
			if(form == null){
				model.put("notFoundError", true);
				return getEditFormView();
			}
		}
		prepareModel(form, model, notifiedBodyId);
        return getEditFormView();
	}
	
	
	/**
	 * Ulozi odostalny zvalidovany formular (Notifikovanu osobu)
	 * 
	 * @param notifiedBodyId
	 * @param form NotifiedBody notifikovana osoba
	 * @param result
	 * @param model
	 * @return String view
	 * @throws ItemNotFoundException 
	 */
	@RequestMapping( value = "/admin/cpr/notifiedbodies/edit/{notifiedBodyId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long notifiedBodyId,  @Valid  NotifiedBody form, BindingResult result, ModelMap model) throws ItemNotFoundException {
		
		if (!result.hasErrors()) {
        	notifiedBodyValidator.validate(result, form);
			if(!result.hasErrors()){
				createOrUpdate(form);
	        	model.put("successCreate", true);
			}
        }
		prepareModel(form, model, notifiedBodyId);
        return getEditFormView();
	}
	
	
	/**
	 * Odstraní notifikovanú osobu ak nie je nikde použitá.
	 * 
	 * @param notifiedBodyId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping( value = "/admin/cpr/notifiedbodies/delete/{notifiedBodyId}", method = RequestMethod.GET)
	public String deleteGroup(@PathVariable Long notifiedBodyId,  ModelMap modelMap) {
						
		NotifiedBody notifiedBody = notifiedBodyService.getNotifiedBodyById(notifiedBodyId);
		if(notifiedBody == null){
			modelMap.put("notFoundError", true);
			return getTableItemsView();
		}
		
		if(notifiedBodyService.canBeDeleted(notifiedBody)){
			notifiedBodyService.deleteNotifiedBody(notifiedBody);
			modelMap.put("successDelete", true);
		}else{
			modelMap.put("isNotEmptyError", true);
		}
		
        return showNotifiedBodiesPage(modelMap);
	}
	
	
	
	private NotifiedBody createOrUpdate(NotifiedBody form) throws ItemNotFoundException{
		NotifiedBody notifiedBody = null;
		Address address = new Address();	
		if(form.getId() == 0){
			notifiedBody = new NotifiedBody();
		}else{
			notifiedBody = notifiedBodyService.getNotifiedBodyById(form.getId());
			if(notifiedBody == null){
				createItemNotFoundError("NO/AO s ID: " + form.getId() + " se v systému nenachází");
			}else{
				address = notifiedBody.getAddress();
			}
		}
		
		notifiedBody.setCode(CodeUtils.toSeoUrl(form.getName()));
		notifiedBody.setName(form.getName());
		notifiedBody.setNotifiedBodyCode(form.getNotifiedBodyCode());
		notifiedBody.setEtaCertificationAllowed(form.getEtaCertificationAllowed());
		notifiedBody.setPhone(form.getPhone());
		notifiedBody.setFax(form.getFax());
		notifiedBody.setWebpage(form.getWebpage());
		notifiedBody.setEmail(form.getEmail());
		notifiedBody.setDescription( form.getDescription());
		notifiedBody.setCountry(form.getCountry());
		address.setCity(form.getAddress().getCity());
		address.setZip(form.getAddress().getZip());
		address.setStreet(form.getAddress().getStreet());
		notifiedBody.setAddress(address);
		notifiedBodyService.saveOrUpdateNotifiedBody(notifiedBody);
		return notifiedBody;
	}
	
	
	
	
	private void prepareModel(NotifiedBody form, ModelMap map, Long notifiedBodyId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("notifiedBody", form);
		model.put("notifiedBodyId", notifiedBodyId);
		model.put("countries", countryService.getAllCountries());
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	
	
}
