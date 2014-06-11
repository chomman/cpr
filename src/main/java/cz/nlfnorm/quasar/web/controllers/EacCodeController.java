package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.EacCode;
import cz.nlfnorm.quasar.services.EacCodeService;

@Controller
public class EacCodeController extends QuasarSupportController {
	
	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/eac-codes";
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/eac-code/{codeId}";
	
	
	@Autowired
	private EacCodeService eacCodeService;
	
	public EacCodeController(){
		setTableItemsView("eac-codes-list");
		setEditFormView("eac-code-edit");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String showNandoCodes(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("eacCodes", eacCodeService.getAll());
		appendTabNo(model);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	private void appendTabNo(Map<String, Object> model){
		model.put("tab", 2);
	}
	
	private void validateNotNull(final EacCode form, final Long id) throws ItemNotFoundException{
		if(form == null){
			throw new ItemNotFoundException("NANDO code ID: " + id + " was not found");
		}
	}
}
