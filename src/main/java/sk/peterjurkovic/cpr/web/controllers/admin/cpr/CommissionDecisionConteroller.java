package sk.peterjurkovic.cpr.web.controllers.admin.cpr;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.services.CommissionDecisionService;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;

@Controller
public class CommissionDecisionConteroller extends SupportAdminController {
	
	private static final int CPR_TAB_INDEX = 9;
	
	private static final String BASE_URL = "/admin/cpr/commission-decisions";
	
	@Autowired
	private CommissionDecisionService commissionDecisionService;

	
	public CommissionDecisionConteroller(){
		setTableItemsView("cpr/commission-decision-list");
		setEditFormView("cpr/commission-decision-edit");
	}
	
	
	@RequestMapping("/admin/cpr/commission-decisions")
	public String showList(ModelMap modelMap){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("commissiondecisions", commissionDecisionService.getAll());
		model.put("tab", CPR_TAB_INDEX);
		model.put("url", BASE_URL);
		modelMap.put("model", model);
		return getTableItemsView();
	}
}
