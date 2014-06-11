package cz.nlfnorm.quasar.web.controllers;

import java.util.Map;

import org.springframework.ui.ModelMap;

import cz.nlfnorm.web.controllers.admin.AdminSupportController;

/**
 * QUASAR support controller
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
public class QuasarSupportController extends AdminSupportController {
	
		
	@Override
	protected String getViewDir() {
		return super.getViewDir() + "quasar/";
	}
	
	
	protected void appendModel(ModelMap map, Map<String, Object> model) {
		map.put("model", model);
	}
}
