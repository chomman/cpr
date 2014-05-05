package cz.nlfnorm.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.StandardService;


@Controller
public class DashBoardController extends AdminSupportController {
    
	private static final int COUNT_OF_LAST_EDITED_STANDARDS = 8;
	
	@Autowired
	private StandardService standardService;
	@Autowired
	private BasicSettingsService basicSettingsService;
	
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
		model.put("version", basicSettingsService.getBasicSettings().getVersion());
		map.put("model", model);
		return getViewName();
	}
    
}
