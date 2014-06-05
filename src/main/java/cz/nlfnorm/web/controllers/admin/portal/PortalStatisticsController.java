package cz.nlfnorm.web.controllers.admin.portal;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.web.controllers.admin.AdminSupportController;

@Controller
public class PortalStatisticsController extends AdminSupportController{
	
	
	public PortalStatisticsController() {
		setViewName("portal/statistics");
	}
	
	@RequestMapping("/admin/portal/statistics")
	public String showStats(ModelMap map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab",6);
		map.put("model", model);
		return getViewName();
	}
}
