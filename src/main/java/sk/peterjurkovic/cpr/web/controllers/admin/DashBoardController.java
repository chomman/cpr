package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.services.StandardService;


@Controller
public class DashBoardController extends SupportAdminController {
    
	private static final int COUNT_OF_LAST_EDITED_STANDARDS = 8;
	
	@Autowired
	private StandardService standardService;
	
	
	/**
	 * Zobrazi hlavny ovladaci panel informacneho systemu
	 * 
	 */
	public DashBoardController(){
		setViewName("dashboard");
	}
	
    
	/**
	 * Zobrazi posledne aktualizovane normy v administracnej casti iformacneho systemu
	 * 
	 * @param ModelMap model
	 * @return String JSP stranka
	 */
	@RequestMapping("/admin/")
    public String showDashBoard(ModelMap map) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("standards", standardService.getLastEditedOrNewestStandards(COUNT_OF_LAST_EDITED_STANDARDS, Boolean.TRUE));
		map.put("model", model);
		return getViewName();
	}
    
}
