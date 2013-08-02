package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.services.CsnCategoryService;

/**
 * Kontroler obsluhujúci požiadavky týkajúc sa odború ČSN
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date 02.08.2013
 */
@Controller
public class CsnCategoryController extends SupportAdminController {
	
	// položka menu
	public static final int TAB_INDEX = 3;
	
	@Autowired
	private CsnCategoryService categoryService;
	
	
	public CsnCategoryController(){
		setTableItemsView("csn/csn-category-list");
		setEditFormView("csn/csn-category-edit");
	}
	
	@RequestMapping("/admin/csn/categories")
	public String showCsnCategories(ModelMap modelMap){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", TAB_INDEX);
		model.put("csnCategories", categoryService.getAll());
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
}
