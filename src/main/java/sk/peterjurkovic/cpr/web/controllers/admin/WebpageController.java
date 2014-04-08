package sk.peterjurkovic.cpr.web.controllers.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sk.peterjurkovic.cpr.context.ContextHolder;
import sk.peterjurkovic.cpr.dto.AutocompleteDto;
import sk.peterjurkovic.cpr.dto.WebpageContentDto;
import sk.peterjurkovic.cpr.dto.WebpageSettingsDto;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.SystemLocale;
import sk.peterjurkovic.cpr.enums.WebpageType;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.FileService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;
import sk.peterjurkovic.cpr.utils.WebpageUtils;
import sk.peterjurkovic.cpr.validators.admin.ImageValidator;
import sk.peterjurkovic.cpr.web.json.JsonResponse;
import sk.peterjurkovic.cpr.web.json.JsonStatus;


@Controller
public class WebpageController extends SupportAdminController {

	private final static String EDIT_WEBPAGE_MAPPING = "/admin/webpage/edit/{id}";
	private final static String LOCALE_CODE_PARAM = "localeCode";
	
	@Autowired
	private WebpageService webpageService;
	@Autowired
	private FileService fileService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ImageValidator imageValidator;
	
	public WebpageController(){
		setViewName("webpages-add");
		setEditFormView("webpages-edit");
		setTableItemsView("webpages");
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		//binder.registerCustomEditor(WebpageContent.class, this.webpageContentEditor);
		//binder.registerCustomEditor(WebpageCategory.class, this.webpageCategoryEditor);
    }
	

	@RequestMapping("/admin/webpages")
	public String showWebpages(ModelMap modelMap, HttpServletRequest request){
		if(request.getParameter("successDelete") != null){
			modelMap.put("successDelete", true);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpages", webpageService.getTopLevelWepages());
		model.put("tab", 1);
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	@RequestMapping(value = "/admin/webpage/add/{nodeId}", method = RequestMethod.GET)
	public String addWebpage(@PathVariable Long nodeId, ModelMap map) throws ItemNotFoundException{
		prepareModelForCreate(map, new Webpage() , nodeId);
		return getViewName();
	}
	
	
	
	@RequestMapping(value = "/admin/webpage/add/{nodeId}", method = RequestMethod.POST)
	public String processCreate(@PathVariable Long nodeId, Webpage webpage, BindingResult result, ModelMap map) throws ItemNotFoundException{
		if(StringUtils.isBlank(webpage.getDefaultName())){
			result.rejectValue("localized['cs'].name", "webpage.error.name");
			prepareModelForCreate(map, webpage , nodeId);
			return getViewName();
		}
		Long id = webpageService.createNewWebpage(webpage, nodeId);
		return "redirect:/admin/webpage" + id;
	}
	
	
	@RequestMapping(value = EDIT_WEBPAGE_MAPPING , method = RequestMethod.GET)
	public String showEditPage(@PathVariable Long id, ModelMap map, @RequestParam(value = LOCALE_CODE_PARAM, required = false) String langCode) 
			throws ItemNotFoundException{
		Webpage webpage = getWebpage(id);
		if(StringUtils.isBlank(langCode) || SystemLocale.isNotAvaiable(langCode)){
			langCode = SystemLocale.getDefaultLanguage();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpageTypes", WebpageType.getAll());
		model.put("locales", SystemLocale.getAllCodes());
		model.put("usedLocales", WebpageUtils.getUsedLocaleCodes(webpage));
		model.put("notUsedLocales", WebpageUtils.getNotUsedLocales(webpage));
		model.put("langCodeParam", LOCALE_CODE_PARAM);
		model.put("webpage", webpage);
		map.put("model", model);
		map.addAttribute("webpageContent", new WebpageContentDto( webpage, langCode ) );
		map.addAttribute("webpageSettings", new WebpageSettingsDto( webpage ) );
		return getEditFormView();
	}
	
	
	@RequestMapping(value = "/admin/webpage/lang/{id}", method = RequestMethod.POST)
	public String createLanguage(@PathVariable Long id, @RequestParam(value = LOCALE_CODE_PARAM) String localeCode) throws ItemNotFoundException{
		webpageService.createWebpageContent(id, localeCode);
		return buildRedirectUrl(id, localeCode);
	}
	
	@RequestMapping(value = "/admin/webpage/lang/{id}", method = RequestMethod.GET)
	public @ResponseBody JsonResponse getLanguage(@PathVariable Long id, @RequestParam(value = LOCALE_CODE_PARAM) String localeCode) throws ItemNotFoundException{
		JsonResponse response = new JsonResponse();
		final Webpage webpage = getWebpage(id);
		if(webpage.getLocalized().containsKey(localeCode)){
			response.setResult(webpage.getLocalized().get(localeCode));
			response.setStatus(JsonStatus.SUCCESS);
		}
		return response;
	}
	
	
	@RequestMapping(value = "/admin/webpage/async-update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponse  processAjaxSubmit(@Valid @RequestBody  WebpageContentDto form) throws ItemNotFoundException{
		JsonResponse response = new JsonResponse();
		update(form);
		response.setStatus(JsonStatus.SUCCESS);
		return response;
	}
	
	@RequestMapping(value = "/admin/webpage/async-update-settings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponse  updateWebpageSettings(@Valid @RequestBody  WebpageSettingsDto form) throws ItemNotFoundException{
		JsonResponse response = new JsonResponse();
		updateSettings(form);
		response.setStatus(JsonStatus.SUCCESS);
		return response;
	}
	
	@RequestMapping(value = "/admin/webpage/{id}/avatar", method = RequestMethod.POST)
	public @ResponseBody JsonResponse  saveAvatar(@PathVariable Long id, MultipartHttpServletRequest request, HttpServletResponse response) throws ItemNotFoundException{
		JsonResponse res = new JsonResponse();
		 Webpage webpage = getWebpage(id);
		 Iterator<String> itr =  request.getFileNames();
		 if(!itr.hasNext()){
			 res.setResult(messageSource.getMessage("error.file.blank", null, ContextHolder.getLocale()));
			 return res; 
		 }
	     MultipartFile multipartFile = request.getFile(itr.next());
	     if(!imageValidator.validate(multipartFile.getOriginalFilename())){
	    	 res.setResult(messageSource.getMessage("error.image.extetion", null, ContextHolder.getLocale()));
			 return res; 
	     }
	     try {
			final String fileName = fileService.saveAvatar( multipartFile.getOriginalFilename(), multipartFile.getBytes());
			webpage.setAvatar(fileName);
			webpageService.saveOrUpdate(webpage);
			res.setResult(fileName);
			res.setStatus(JsonStatus.SUCCESS);
	     } catch (IOException e) {
			logger.error(e);
		}
		return res;
	}
	
	@RequestMapping(value = "/admin/webpage/{id}/avatar", method = RequestMethod.DELETE)
	public @ResponseBody JsonResponse  deleteAvatar(@PathVariable Long id) throws ItemNotFoundException{
		JsonResponse response = new JsonResponse();
		webpageService.deleteWebpageAvatar(id);
		response.setStatus(JsonStatus.SUCCESS);
		return response;
	}
	

	@RequestMapping(value = "/ajax/autocomplete/webpages", method = RequestMethod.GET)
	public @ResponseBody List<AutocompleteDto> search(@RequestBody @RequestParam("term") String term){
		return webpageService.autocomplete(term);
	}
	
	private void updateSettings(WebpageSettingsDto form) throws ItemNotFoundException{
		Webpage webpage = getWebpage(form.getId());
		final User user = UserUtils.getLoggedUser();
		webpage.setEnabled(form.getEnabled());
		webpage.setWebpageType(form.getWebpageType());
		webpage.setPublishedSince(form.getPublishedSince());
		if(user.isWebmaster()){
			webpage.setLocked(form.getLocked());
		}
		webpageService.saveOrUpdate(webpage);
	}
	
	private void update(WebpageContentDto form) throws ItemNotFoundException{
		Validate.notNull(form);
		Validate.notEmpty(form.getLocale());
		Webpage webpage = getWebpage(form.getId());
		if(!webpage.getLocalized().containsKey(form.getLocale())){
			throw new IllegalArgumentException(String.format("Content of webpage [id=%1$d][lang=%2$s] was not found", form.getId(), form.getLocale()));
		}
		//final User user = UserUtils.getLoggedUser();
		if(!webpage.getLocked()){
			form.getWebpageContent().setUrl(CodeUtils.toSeoUrl(form.getWebpageContent().getUrl()));
		}
		webpage.setRedirectWebpage(form.getRedirectWebpage());
		webpage.setRedirectUrl(form.getRedirectUrl());
		webpage.getLocalized().put(form.getLocale(), form.getWebpageContent());
		webpageService.saveOrUpdate(webpage);
	}
	
	private String buildRedirectUrl(Long id, String localeCode){
		return new StringBuilder("redirect:")
		 	.append(EDIT_WEBPAGE_MAPPING.replace("{id}", id.toString()) )
			.append("?")
			.append(LOCALE_CODE_PARAM)
			.append("=")
			.append(localeCode)
			.toString();
	}

	private void prepareModelForCreate(ModelMap map, Webpage form, Long nodeId) throws ItemNotFoundException{
		Webpage parentWebpage = null;
		if(nodeId != 0){
			parentWebpage = getWebpage(nodeId);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("parentWebpage", parentWebpage);
		model.put("webpageTypes", WebpageType.getAll());
		map.addAttribute("webpage", new Webpage());
		map.put("model", model);
	}
	
	private Webpage getWebpage(Long id) throws ItemNotFoundException{
		final Webpage webpage = webpageService.getWebpageById(id);
		if(webpage == null){
			throw new ItemNotFoundException(String.format("Webpage ID: %s was not found.", id));
		}
		return webpage;
	}
	
	
	
	
	
}
