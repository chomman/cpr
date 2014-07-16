package cz.nlfnorm.web.controllers.admin.cpr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import cz.nlfnorm.entities.Country;
import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.CountryService;
import cz.nlfnorm.services.NotifiedBodyService;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.validators.admin.NotifiedBodyValidator;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;
import cz.nlfnorm.web.editors.CountryEditor;


@Controller
@SessionAttributes("notifiedBody")
public class NotifiedBodyController extends AdminSupportController {
	
	public static final int CPR_TAB_INDEX = 11;
	
	private final static String CPR_NB_LIST_URL = "/admin/cpr/notifiedbodies";
	private final static String QUASAR_NB_LIST_URL = "/admin/quasar/manage/notifiedbodies";
	private final static String CPR_NB_EIDT_URL = "/admin/cpr/notifiedbodies/edit/";
	public  final static String QUASAR_NB_EIDT_URL = "/admin/quasar/manage/notifiedbody/";
	private final static String CPR_NB_DELETE_URL = "/admin/cpr/notifiedbodies/delete/";
	private final static String QUASAR_NB_DELETE_URL = "/admin/quasar/manage/notifiedbody/delete/";
	
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private CountryEditor countryEditor;
	@Autowired
	private NotifiedBodyValidator notifiedBodyValidator;
		

	public NotifiedBodyController(){
		setEditFormView("cpr/notifiedbodies-edit");
		setTableItemsView("cpr/notifiedbodies");
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
	@RequestMapping({CPR_NB_LIST_URL, QUASAR_NB_LIST_URL})
    public String showNotifiedBodiesPage(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<NotifiedBody> groups = notifiedBodyService.getAllNotifiedBodies();
		model.put("notifiedBodies", groups);
		model.put("tab", CPR_TAB_INDEX);
		modelMap.put("model", model);
		setUrls(request, modelMap);
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
	@RequestMapping( value = {CPR_NB_EIDT_URL+"{nbID}",	QUASAR_NB_EIDT_URL +"{nbID}"}, 
			method = RequestMethod.GET)
	public String showForm(@PathVariable Long nbID,  ModelMap map, HttpServletRequest request) {
		NotifiedBody form = null;
		// vytvorenie novej polozky
		if(nbID == 0){
			form = new NotifiedBody();
			form.setId(0L);
		}else{
			// editacia polozky
			form = notifiedBodyService.getNotifiedBodyById(nbID);
			if(form == null){
				map.put("notFoundError", true);
				return getEditFormView();
			}
		}
		setUrls(request, map);
		prepareModel(form, map, nbID);
        return getEditFormView();
	}
	
	
	/**
	 * Ulozi odostalny zvalidovany formular (Notifikovanu osobu)
	 * 
	 * @param nbID
	 * @param form NotifiedBody notifikovana osoba
	 * @param result
	 * @param map
	 * @return String view
	 * @throws ItemNotFoundException 
	 */
	@RequestMapping( value = {CPR_NB_EIDT_URL+"{nbID}",	QUASAR_NB_EIDT_URL +"{nbID}"}, 
			method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long nbID,  @Valid  NotifiedBody form, BindingResult result, ModelMap map, HttpServletRequest request) throws ItemNotFoundException {
		setUrls(request, map);
		if (!result.hasErrors()) {
        	notifiedBodyValidator.validate(result, form);
			if(!result.hasErrors()){
				createOrUpdate(form);
	        	map.put("successCreate", true);
			}
        }
		prepareModel(form, map, nbID);
        return getEditFormView();
	}
	
	
	/**
	 * Odstraní notifikovanú osobu ak nie je nikde použitá.
	 * 
	 * @param ndID
	 * @param modelMap
	 * @return
	 */
	@RequestMapping( value =  {CPR_NB_DELETE_URL + "{ndID}", QUASAR_NB_DELETE_URL + "{ndID}"}, method = RequestMethod.GET)
	public String deleteGroup(@PathVariable Long ndID,  ModelMap modelMap, HttpServletRequest request) {
		NotifiedBody notifiedBody = notifiedBodyService.getNotifiedBodyById(ndID);
		setUrls(request, modelMap);
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
        return showNotifiedBodiesPage(modelMap, request);
	}
	
	
	
	private NotifiedBody createOrUpdate(NotifiedBody form) throws ItemNotFoundException{
		NotifiedBody notifiedBody = null;	
		if(form.getId() == 0){
			notifiedBody = new NotifiedBody();
		}else{
			notifiedBody = notifiedBodyService.getNotifiedBodyById(form.getId());
			if(notifiedBody == null){
				createItemNotFoundError("NO/AO s ID: " + form.getId() + " se v systému nenachází");
			}
		}
		
		notifiedBody.setCode(CodeUtils.toSeoUrl(form.getName()));
		notifiedBody.setName(form.getName());
		notifiedBody.setNoCode(form.getNoCode());
		notifiedBody.setAoCode(form.getAoCode());
		notifiedBody.setEtaCertificationAllowed(form.getEtaCertificationAllowed());
		notifiedBody.setPhone(form.getPhone());
		notifiedBody.setFax(form.getFax());
		notifiedBody.setWebpage(form.getWebpage());
		notifiedBody.setEmail(form.getEmail());
		notifiedBody.setDescription( form.getDescription());
		notifiedBody.setCountry(form.getCountry());
		notifiedBody.setNandoCode(form.getNandoCode());
		notifiedBody.setCity(form.getCity());
		notifiedBody.setZip(form.getZip());
		notifiedBody.setStreet(form.getStreet());
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
	
	private void setUrls(HttpServletRequest request, ModelMap map){
		final String url = RequestUtils.getPartOfURLOnPosition(request, 2);
		if(url.equals("quasar")){
			map.put("listUrl", QUASAR_NB_LIST_URL);
			map.put("deleteUrl", QUASAR_NB_DELETE_URL);
			map.put("editUrl", QUASAR_NB_EIDT_URL);
		}else{
			map.put("listUrl", CPR_NB_LIST_URL);
			map.put("deleteUrl", CPR_NB_DELETE_URL);
			map.put("editUrl", CPR_NB_EIDT_URL);
		}
		map.put("quasarView",url.equals("quasar"));
	}
	
}
