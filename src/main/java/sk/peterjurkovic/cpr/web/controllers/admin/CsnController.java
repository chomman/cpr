package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.services.CsnService;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date 27.07.2013
 */
@Controller
public class CsnController extends SupportAdminController {
	
	// položka menu
	public static final int TAB_INDEX = 1;
	
	
	
	@Autowired
	private CsnService csnService;
	
	public CsnController(){
		setTableItemsView("csn/csn-list");
		setEditFormView("csn/csn-edit");
	}
	
	
	@RequestMapping("/admin/csn")
	public String showCsn(ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", TAB_INDEX);
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	@RequestMapping( value = "/admin/csn/edit/{id}", method = RequestMethod.GET)
	public String showCsnForm(@PathVariable Long id, ModelMap modelMap){
		Csn form = null;
		if(id == 0){
			form = createEmptyForm();
		}else{
			form = csnService.getById(id);
		}
		
		if(form == null){
			modelMap.put("notFoundError", true);
			return getEditFormView();
		}
		prepareModel(form, modelMap, id);
		return getEditFormView();
	}
	
	
	
	private void prepareModel(Csn form, ModelMap modelMap, Long id){
		Map<String, Object> model = new HashMap<String, Object>();;
		model.put("tab", 2);
		modelMap.put("model", model);
		modelMap.put("id", id);
		modelMap.addAttribute("csn", form);
	}
	
	
	private Csn createEmptyForm(){
		Csn form = new Csn();
		form.setId(0l);
		return form;
	}
}
