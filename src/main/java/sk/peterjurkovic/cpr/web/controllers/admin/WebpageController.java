package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.resolvers.LocaleResolver;
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
		model.put("webpages", webpageService.getAllOrderedWebpages());
		model.put("tab", 1);
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	@RequestMapping("/admin/webpage/add/{nodeId}")
	public String addWebpage(@PathVariable Long nodeId, ModelMap map){
		
		final Webpage parentWebpage = webpageService.getWebpageById(nodeId);
		
		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("webpage", webpage);
		
		return getViewName();
	}
	
	
	
	
	
}
