package cz.nlfnorm.web.controllers.admin.cpr;

import java.util.Map;

import org.springframework.ui.ModelMap;

import cz.nlfnorm.web.controllers.admin.AdminSupportController;

public class CprSupportController extends AdminSupportController{
	
	@Override
	protected String getViewDir() {
		return super.getViewDir() + "cpr/";
	}
	
	
	protected void appendModel(ModelMap map, Map<String, Object> model) {
		map.put("model", model);
	}
	
	protected void appendTabNo(Map<String, Object> model, final int tabNo){
		model.put("tab", tabNo);
	}
	
}
