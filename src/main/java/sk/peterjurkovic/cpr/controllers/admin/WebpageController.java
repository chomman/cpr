package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
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

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.entities.WebpageCategory;
import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.exceptions.CollisionException;
import sk.peterjurkovic.cpr.services.WebpageCategoryService;
import sk.peterjurkovic.cpr.services.WebpageContentService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.web.editors.WebpageCategoryEditor;
import sk.peterjurkovic.cpr.web.editors.WebpageContentEditor;


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
	
	public WebpageController(){
		setEditFormView("webpages-edit");
		setTableItemsView("webpages");
	}
	
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(WebpageContent.class, this.webpageContentEditor);
		binder.registerCustomEditor(WebpageCategory.class, this.webpageCategoryEditor);
    }
	
	
	
	@RequestMapping("/admin/webpages")
	public String showWebpages(ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpages", webpageService.getAll());
		model.put("tab", 1);
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	
	@RequestMapping( value = "/admin/webpages/edit/{webpageId}", method = RequestMethod.GET)
	public String showForm(@PathVariable Long webpageId,  ModelMap model) {		
		Webpage form = null;
		if(webpageId == 0){
			form = createEmptyWebpageForm();
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
	
	
	@RequestMapping( value = "/admin/webpages/edit/{webpageId}", method = RequestMethod.POST)
	public String rocessSubmit(@PathVariable Long webpageId, @Valid  Webpage form, BindingResult result, ModelMap model) {		
		if(!result.hasErrors()){
			try{
				createOrUpdate(form);
				model.put("successCreate", true);
				if(webpageId == 0){
					form = createEmptyWebpageForm();
				}
			}catch(CollisionException e){
				result.rejectValue("timestamp", "error.collision", e.getMessage());
			}
		}
		prepareModel(form, model);
        return getEditFormView();
	}
	
	
	private void createOrUpdate(Webpage form) throws CollisionException{
		Webpage webpage = null;
		
		if(form.getId() == null || form.getId() == 0){
			webpage =  new Webpage();
		}else{
			webpage = webpageService.getWebpageById(form.getId());
			if(webpage == null){
				createItemNotFoundError();
			}
			validateCollision(webpage, form);
		}
		
		webpage.setTitle(form.getTitle());
		webpage.setName(form.getName());
		webpage.setCode(form.getCode());
		webpage.setDescription(form.getDescription());
		webpage.setEnabled(form.getEnabled());
		webpage.setTopText(form.getTopText());
		webpage.setBottomText(form.getBottomText());
		webpage.setWebpageCategory(form.getWebpageCategory());
		if(form.getWebpageContent() != null){
			webpage.setWebpageContent(form.getWebpageContent());
		}
		
		webpageService.saveOrUpdate(webpage);
	}
	
	
	
	private void prepareModel(Webpage form, ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("webpage", form);
		model.put("webpageId", form.getId());
		model.put("categories", webpageCategoryService.getAll());
		model.put("contents", webpageContentService.getAll());
		model.put("tab", 1);
		map.put("model", model); 
	}
	
	
	
	private void validateCollision(Webpage persitedWebpage, Webpage form) throws CollisionException{
		if(form.getTimestamp() != null && persitedWebpage.getChanged().isAfter(form.getTimestamp())){
			throw new CollisionException();
		}
	}
	
	
	private Webpage createEmptyWebpageForm(){
		Webpage form = new Webpage();
		form.setId(0L);
		return form;
	}
}
