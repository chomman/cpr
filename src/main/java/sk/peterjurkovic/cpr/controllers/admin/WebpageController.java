package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.services.WebpageCategoryService;
import sk.peterjurkovic.cpr.services.WebpageService;


@Controller
public class WebpageController extends SupportAdminController {

	@Autowired
	private WebpageService webpageService;
	@Autowired
	private WebpageCategoryService webpageCategoryService;
	
	
	public WebpageController(){
		setEditFormView("webpages-edit");
		setTableItemsView("webpages");
	}
	
	
	@RequestMapping("/admin/webpages")
	public String showWebpages(ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpages", webpageService.getAll());
		model.put("tab", 2);
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	@RequestMapping( value = "/admin/webpages/edit/{webpageId}", method = RequestMethod.GET)
	public String showForm(@PathVariable Long webpageId,  ModelMap model) {		
		Webpage form = null;
		if(webpageId == 0){
			form = new Webpage();
			form.setId(0L);
		}else{
			form = webpageService.getWebpageById(webpageId);
			if(form == null){
				createItemNotFoundError();
				return getEditFormView();
			}
		}
		prepareModel(form, model);
        return getEditFormView();
	}
	
	
	private void prepareModel(Webpage form, ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("webpage", form);
		model.put("webpageId", form.getId());
		model.put("categories", webpageCategoryService.getAll());
		model.put("tab", 1);
		map.put("model", model); 
	}
	
	
	
}
