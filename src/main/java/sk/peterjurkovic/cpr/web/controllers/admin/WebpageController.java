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

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.enums.WebpageType;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.utils.CodeUtils;


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
	public String addWebpage(@PathVariable Long nodeId, ModelMap map){
		prepareModelForCreate(map, new Webpage() , nodeId);
		return getViewName();
	}
	
	
	@RequestMapping(value = "/admin/webpage/add/{nodeId}", method = RequestMethod.POST)
	public String processCreate(@PathVariable Long nodeId, Webpage webpage, BindingResult result, ModelMap map){
		if(StringUtils.isBlank(webpage.getDefaultName())){
			result.rejectValue("localized['cs'].name", "webpage.error.name");
			prepareModelForCreate(map, webpage , nodeId);
			return getViewName();
		}
		createWebpage(webpage, nodeId);
		return "redirect:/admin/webpages";
	}
	
	
	private void createWebpage(Webpage form, Long nodeId){
		Webpage parentWebpage = null;
		Webpage webpage = null;
		if(nodeId != 0){
			parentWebpage = webpageService.getWebpageById(nodeId);
		}		
		if(parentWebpage != null){
			webpage = new Webpage(parentWebpage);
			webpage.setOrder(webpageService.getNextOrderValue(parentWebpage.getId()));
		}else{
			webpage = new Webpage();
			webpage.setOrder(webpageService.getNextOrderValue( null ));
		}
		webpage.setWebpageType(form.getWebpageType());
		WebpageContent formContent = form.getDefaultWebpageContent();
		WebpageContent webpageContent = webpage.getDefaultWebpageContent();
		webpageContent.setName(formContent.getName());
		webpageContent.setTitle(formContent.getName());
		webpageContent.setUrl( CodeUtils.toSeoUrl( formContent.getName() ));
		webpageService.createWebpage(webpage);
	}
	
	
	private void prepareModelForCreate(ModelMap map, Webpage form, Long nodeId){
		Webpage parentWebpage = null;
		if(nodeId != 0){
			parentWebpage = webpageService.getWebpageById(nodeId);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("parentWebpage", parentWebpage);
		model.put("webpageTypes", WebpageType.getAll());
		map.addAttribute("webpage", new Webpage());
		map.put("model", model);
	}
	
	
	
	
	
	
	
}
