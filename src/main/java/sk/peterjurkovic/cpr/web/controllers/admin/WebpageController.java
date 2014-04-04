package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.dto.WebpageContentDto;
import sk.peterjurkovic.cpr.dto.WebpageSettingsDto;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.SystemLocale;
import sk.peterjurkovic.cpr.enums.WebpageType;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.WebpageService;


@Controller
public class WebpageController extends SupportAdminController {

	@Autowired
	private WebpageService webpageService;
	
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
		webpageService.createNewWebpage(webpage, nodeId);
		return "redirect:/admin/webpages";
	}
	
	
	@RequestMapping(value = "/admin/webpage/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable Long id, ModelMap map) throws ItemNotFoundException{
		Webpage webpage = getWebpage(id);
		Map<String, Object> model = new HashMap<String, Object>();
		map.put("model", model);
		map.addAttribute("webpageContent", new WebpageContentDto( webpage, SystemLocale.getDefaultLanguage() ) );
		map.addAttribute("webpageSettings", new WebpageSettingsDto( webpage ) );
		return getEditFormView();
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
