package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import sk.peterjurkovic.cpr.controllers.SupportController;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.services.CountryService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.utils.CodeUtils;


@Controller
@SessionAttributes("notifiedBody")
public class CprNotifiedBodyController extends SupportController {
	
	public static final int CPR_TAB_INDEX = 3;
	
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private ConversionService conversionService;
	
	public CprNotifiedBodyController(){
		setEditFormView("cpr-notifiedbodies-edit");
		setTableItemsView("cpr-notifiedbodies");
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
     
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
	
	
	
	@RequestMapping( value = "/admin/cpr/notifiedbodies/edit/{notifiedBodyId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long notifiedBodyId,  @Valid  NotifiedBody form, BindingResult result, ModelMap model) {
		
		logger.info(form);
		
		if (result.hasErrors()) {
			prepareModel(form, model, notifiedBodyId);
        }else{
        	createOrUpdate(form);
        	model.put("successCreate", true);
        }
		
        return getEditFormView();
	}
	
	
	private NotifiedBody createOrUpdate(NotifiedBody form){
		NotifiedBody notifiedBody = null;
			
		if(form.getId() == 0){
			notifiedBody = new NotifiedBody();
		}else{
			notifiedBody = notifiedBodyService.getNotifiedBodyById(form.getId());
			if(notifiedBody == null){
				createItemNotFountError();
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
		notifiedBody.setAddress(form.getAddress());
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
