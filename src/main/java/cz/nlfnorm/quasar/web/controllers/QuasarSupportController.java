package cz.nlfnorm.quasar.web.controllers;

import java.util.Map;

import org.springframework.ui.ModelMap;

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;

/**
 * QUASAR support controller
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
public class QuasarSupportController extends AdminSupportController {
	
	protected final static String COMMAND = "command";
		
	@Override
	protected String getViewDir() {
		return super.getViewDir() + "quasar/";
	}
	
	
	protected void appendModel(ModelMap map, Map<String, Object> model) {
		map.put("model", model);
	}
	
	protected void appendTabNo(Map<String, Object> model, final int tabNo){
		model.put("tab", tabNo);
	}
	
	
	protected void validateNotNull(final Object form, final String errorMessage) throws ItemNotFoundException{
		if(form == null){
			throw new ItemNotFoundException(errorMessage);
		}
	}
}
