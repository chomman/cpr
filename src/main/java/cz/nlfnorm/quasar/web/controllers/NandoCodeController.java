package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.services.NandoCodeService;


/**
 * QUASAR controller which serves requests of NANDO codes
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
@Controller
public class NandoCodeController extends QuasarSupportController {
	
	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/nando-codes";
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/nando-code/{codeId}";
	
	@Autowired
	private NandoCodeService nandoCodeService;
	
	public NandoCodeController(){
		setTableItemsView("nando-codes-list");
		setEditFormView("nando-code-edit");
	}
	
	
	@RequestMapping(LIST_MAPPING_URL)
	public String showNandoCodes(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("nandoCodes", nandoCodeService.getFirstLevelCodes());
		appendTabNo(model);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.GET)
	public String showEditForm(ModelMap modelMap, HttpServletRequest request) {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		prepareModel(modelMap, new NandoCode());
		return getEditFormView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.POST)
	public String processSubmit(ModelMap modelMap, @Valid @ModelAttribute("nandoCode") NandoCode form, BindingResult result){
		
		if(result.hasErrors()){
			prepareModel(modelMap, form);
			return getEditFormView();
		}
		final Long id = createOrUpdate(form);
		return successDeleteRedirect(FORM_MAPPING_URL.replace("{codeId}", id+""));
	}
	
	private Long createOrUpdate(NandoCode form){
		NandoCode nandoCode = null;
		if(form.getId() == null){
			nandoCode = new NandoCode();
		}else{
			nandoCode = nandoCodeService.getById(form.getId());
		}
		nandoCode.setCode(form.getCode());
		nandoCode.setEnabled(form.isEnabled());
		nandoCode.setSpecification(form.getSpecification());
		nandoCode.setParent(form.getParent());
		nandoCode.setForProductAssesorA(form.isForProductAssesorA());
		nandoCode.setForProductAssesorR(form.isForProductAssesorR());
		nandoCode.setForProductSpecialist(form.isForProductSpecialist());
		nandoCodeService.createOrUpdate(nandoCode);
		return nandoCode.getId();
	}
	
	private void prepareModel(ModelMap map, NandoCode form){
		Map<String, Object> model = new HashMap<>();
		model.put("firstLevelCodes", nandoCodeService.getFirstLevelCodes());
		appendTabNo(model);
		map.addAttribute("nandoCode", form);
	}
	
	
	private void appendTabNo(Map<String, Object> model){
		model.put("tab", 1);
	}
	
	
}
