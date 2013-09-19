package sk.peterjurkovic.cpr.web.controllers.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.services.CsnTerminologyLogService;


@Controller
public class ImportLogController extends SupportAdminController {

	
	@Autowired
	private CsnTerminologyLogService csnTerminologyLogService;
	
	public ImportLogController(){
		setTableItemsView("csn/csn-terminology-log-list");
	}
	
	
	
	@RequestMapping("/admin/csn/terminology/log")
	private String showLogPage(HttpServletRequest request, ModelMap modelMap){
		
		
		return getTableItemsView();
	}
	
	
}
