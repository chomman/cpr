package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.services.NandoCodeService;
import cz.nlfnorm.web.editors.IdentifiableByLongPropertyEditor;


/**
 * QUASAR controller which serves requests of NANDO codes
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
@Controller
public class NandoCodeController extends QuasarSupportController {
	
	private final static int TAB = 1; 
	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/nando-codes";
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/nando-code/{codeId}";
	
	@Autowired
	private NandoCodeService nandoCodeService;
	
	
	public NandoCodeController(){
		setTableItemsView("nando-codes-list");
		setEditFormView("nando-code-edit");
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(NandoCode.class, new IdentifiableByLongPropertyEditor<NandoCode>( nandoCodeService ));
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String showNandoCodes(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("nandoCodes", nandoCodeService.getFirstLevelCodes());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.GET)
	public String showEditForm(ModelMap modelMap, HttpServletRequest request, @PathVariable long codeId) throws ItemNotFoundException {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		NandoCode form = new NandoCode();
		if(codeId != 0){
			form = nandoCodeService.getById(codeId);
			validateNotNull(form, "Can not dispaly NANDO code edit form, NANDO code was not found.");
		}
		prepareModel(modelMap, form);
		return getEditFormView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.POST)
	public String processSubmit(ModelMap modelMap, @Valid @ModelAttribute("nandoCode") NandoCode form, BindingResult result) throws ItemNotFoundException{
		
		if(result.hasErrors()){
			prepareModel(modelMap, form);
			return getEditFormView();
		}
		final Long id = createOrUpdate(form);
		return successUpdateRedirect(FORM_MAPPING_URL.replace("{codeId}", id+""));
	}
	
	@RequestMapping(value = "/ajax/nando-codes", method = RequestMethod.GET)
	public @ResponseBody List<NandoCode>  getEacCodes(@RequestBody @RequestParam(required = false, value = "term") String query){
		return nandoCodeService.getForProductAssessorA();
	}
	
	private Long createOrUpdate(NandoCode form) throws ItemNotFoundException{
		NandoCode nandoCode = null;
		if(form.getId() == null){
			nandoCode = new NandoCode();
		}else{
			nandoCode = nandoCodeService.getById(form.getId());
			validateNotNull(nandoCode, "Could not create NANDO code. Not found.");
		}
		nandoCode.setCode(StringUtils.trim(form.getCode()));
		nandoCode.setEnabled(form.isEnabled());
		nandoCode.setSpecification(StringUtils.trim(form.getSpecification()));
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
		appendTabNo(model, TAB);
		map.addAttribute("nandoCode", form);
		appendModel(map, model);
	}
		
}
