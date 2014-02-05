package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.peterjurkovic.cpr.dto.WebpageDto;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.entities.WebpageCategory;
import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.resolvers.LocaleResolver;
import sk.peterjurkovic.cpr.services.WebpageCategoryService;
import sk.peterjurkovic.cpr.services.WebpageContentService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.utils.UserUtils;
import sk.peterjurkovic.cpr.utils.WebpageUtils;
import sk.peterjurkovic.cpr.validators.admin.WebpageValidator;
import sk.peterjurkovic.cpr.web.editors.WebpageCategoryEditor;
import sk.peterjurkovic.cpr.web.editors.WebpageContentEditor;
import sk.peterjurkovic.cpr.web.json.JsonResponse;
import sk.peterjurkovic.cpr.web.json.JsonStatus;


@Controller
public class WebpageController extends SupportAdminController {

	@Autowired
	private WebpageService webpageService;
	@Autowired
	private WebpageCategoryService webpageCategoryService;
	@Autowired
	private WebpageContentService webpageContentService;
	
	@Autowired
	private WebpageContentEditor webpageContentEditor;
	@Autowired
	private WebpageCategoryEditor webpageCategoryEditor;
	
	@Autowired
	private WebpageValidator webpageValidator;
	
	public WebpageController(){
		setViewName("webpages-add");
		setEditFormView("webpages-edit");
		setTableItemsView("webpages");
	}
	
	
	/**
	 * Koverzia formulara (select boxov) na objekty
	 * 
	 * @param binder
	 */
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(WebpageContent.class, this.webpageContentEditor);
		binder.registerCustomEditor(WebpageCategory.class, this.webpageCategoryEditor);
    }
	
	
	/**
	 * Zobrazi verejne sekcie systemu
	 * 
	 * @param ModelMap
	 * @param request
	 * @return String JSP stranka
	 */
	@RequestMapping("/admin/webpages")
	public String showWebpages(ModelMap modelMap, HttpServletRequest request){
		if(request.getParameter("successDelete") != null){
			modelMap.put("successDelete", true);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpages", webpageService.getAll());
		model.put("tab", 1);
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	/**
	 * Zobrazi formular, pre priadeni resp. editaciu verejnej sekcie sysstemu. V pripade
	 * ak ID je rovne nule, jedna sa o pridanie novej sekcie, inak o editaciu
	 * 
	 * @param Long webpageId
	 * @param Model model
	 * @param HttpServletRequest request
	 * @return String view
	 * @throws ItemNotFoundException, ak sa verejna sekcia s danym ID v systeme nenachadza
	 */
	@RequestMapping( value = "/admin/webpages/edit/{webpageId}", method = RequestMethod.GET)
	public String showForm(@PathVariable Long webpageId,  ModelMap model, HttpServletRequest request) throws ItemNotFoundException {		
		Webpage form = null;
		if(webpageId == 0){
			form = createEmptyWebpageForm();
		}else{
			if(request.getParameter("successCreate") != null){
				model.put("successCreate", true);
			}
			form = webpageService.getWebpageById(webpageId);
			if(form == null){
				createItemNotFoundError("Vežejná sekce s ID: "+ webpageId + " se v systému nenachází");
				return getEditFormView();
			}
		}
		prepareModel(form, model);
		if(webpageId == 0){
			return getViewName();
		}
        return getEditFormView();
	}
	
	/**
	 * Ostrani verejniu sekciu na zaklade daneho IS
	 * 
	 * @param Long webpageId
	 * @return String view
	 * @throws ItemNotFoundException, ak sa verejna sekcia s danym ID v systeme nenachadza
	 */
	@RequestMapping( value = "/admin/webpages/delete/{webpageId}", method = RequestMethod.GET)
	public String processDelete(@PathVariable Long webpageId) throws ItemNotFoundException {		
		Webpage webpage = webpageService.getWebpageById(webpageId);
		if(webpage == null){
			throw new ItemNotFoundException("Webová sekce s ID: " + webpageId + " se v systému nenachází.");
		}
		webpageService.deleteWebpage(webpage);
		return "redirect:/admin/webpages?successDelete=1";
	}
	
	
	@RequestMapping(value = "/admin/webpages/async-edit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponse  processAjaxSubmit(@RequestBody  WebpageDto form, @RequestParam(required = false) String changeLang) throws ItemNotFoundException{
		JsonResponse response = new JsonResponse();
		final List<String> errors = webpageValidator.validate(form);
		
		if(errors.size() > 0){
			response.setResult(errors);
		}else{
			Webpage webpage = update(form);
			if(StringUtils.isNotBlank(changeLang)){
				WebpageDto webpageDtoInLang = WebpageUtils.toDTO(webpage, changeLang);
				webpageDtoInLang.setWebpageCategory(null);
				webpageDtoInLang.setWebpageContent(null);
				response.setData(webpageDtoInLang);
			}
			response.setStatus(JsonStatus.SUCCESS);
		}
		
		response.setStatus(JsonStatus.SUCCESS);
		return response;
	}
	
	
	/**
	 * Spracuje odoslany formualr s odoslanou verejnou sekciu, v pripade ak je ID sekcie nula, 
	 * jedna sa o pripadnie novej verejnej sekcie, inak o editaciu 
	 * 
	 * @param Long webpageId
	 * @param BindingResultWebpageform
	 * @param result
	 * @param Model model
	 * @return String view
	 * @throws ItemNotFoundException, ak sa verejna sekcia s danym ID v systeme nenachadza
	 */
	@RequestMapping( value = "/admin/webpages/edit/{webpageId}", method = RequestMethod.POST)
	public String pocessSubmit(@PathVariable Long webpageId, @Valid  Webpage form, BindingResult result, ModelMap model) throws ItemNotFoundException {		
		if(!result.hasErrors()){
			Long generatedId = createOrUpdate(form);
			model.put("successCreate", true);
			if(webpageId == 0){
				return "redirect:/admin/webpages/edit/"+generatedId +"?successCreate=1";
			}
		}
		prepareModel(form, model);
        return getViewName();
	}
	
	
	private Long createOrUpdate(Webpage form) throws ItemNotFoundException{
		Webpage webpage = null;
		
		if(form.getId() == null || form.getId() == 0){
			webpage =  new Webpage();
		}else{
			webpage = webpageService.getWebpageById(form.getId());
			if(webpage == null){
				createItemNotFoundError("Vežejná sekce s ID: "+ form.getId() + " se v systému nenachází");
			}
			
		}
		User loggerUser = UserUtils.getLoggedUser();
		if((!loggerUser.isWebmaster() && (form.getId() == null || form.getId() == 0)) || 
		 (loggerUser.isWebmaster() && StringUtils.isBlank(form.getCode())) ){
			webpage.setCode( webpageService.getSeoUniqueUrl(form.getName()));
		}else if(loggerUser.isWebmaster()){
			webpage.setCode(form.getCode());
		}
		webpage.setNameCzech(form.getNameCzech());
		webpageService.saveOrUpdate(webpage);
		return webpage.getId();
	}
	
	
	private void prepareModel(Webpage form, ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		if(form.getId() == 0){
			map.addAttribute("webpage", form);
		}else{
			map.addAttribute("webpage", WebpageUtils.toCzechDto(form));
		}
		model.put("webpageId", form.getId());
		model.put("categories", webpageCategoryService.getAll());
		model.put("contents", webpageContentService.getAll());
		model.put("tab", 1);
		map.put("model", model); 
	}
	
	
	private Webpage update(WebpageDto webpageDto) throws ItemNotFoundException{
		Webpage webpage = webpageService.getWebpageById(webpageDto.getId());
		if(webpage == null){
			throw new ItemNotFoundException("Webpage with id [" + webpageDto.getId() + "] was not found");
		}
		if(StringUtils.isNotBlank(webpageDto.getCode())){
			webpage.setCode(webpage.getCode());
		}
		webpage.setWebpageCategory(webpageDto.getWebpageCategory());
		webpage.setWebpageContent(webpageDto.getWebpageContent());
		
		if(StringUtils.isBlank(webpageDto.getLocale()) || !LocaleResolver.isAvailable(webpageDto.getLocale())){
			throw new IllegalArgumentException("Unsupported locale: " + 
						webpageDto.getLocale() + " for webpage id: " + webpage.getId());
		}
		
		if(webpageDto.getLocale().equals(LocaleResolver.CODE_CZ)){
			webpage.setTitleCzech(webpageDto.getTitle());
			webpage.setNameCzech(webpageDto.getName());
			webpage.setDescriptionCzech(webpageDto.getDescription());
			webpage.setTopTextCzech(webpageDto.getTopText());
			webpage.setBottomTextCzech(webpageDto.getBottomText());
		}else if(webpageDto.getLocale().equals(LocaleResolver.CODE_EN)){
			webpage.setTitleEnglish(webpageDto.getTitle());
			webpage.setNameEnglish(webpageDto.getName());
			webpage.setDescriptionEnglish(webpageDto.getDescription());
			webpage.setTopTextEnglish(webpageDto.getTopText());
			webpage.setBottomTextEnglish(webpageDto.getBottomText());
		}
		webpageService.saveOrUpdate(webpage);
		return webpage;
	}
	
		
	private Webpage createEmptyWebpageForm(){
		Webpage form = new Webpage();
		form.setId(0L);
		return form;
	}
}
