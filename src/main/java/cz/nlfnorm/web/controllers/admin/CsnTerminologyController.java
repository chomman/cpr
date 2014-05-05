package cz.nlfnorm.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnTerminology;
import cz.nlfnorm.enums.CsnTerminologyLanguage;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.CsnService;
import cz.nlfnorm.services.CsnTerminologyService;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.validators.admin.CsnTerminologyValidator;
import cz.nlfnorm.web.editors.CsnEditor;
import cz.nlfnorm.web.editors.CsnTerminologyLanguageEditor;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 7, 2013
 *
 */
@Controller
public class CsnTerminologyController extends AdminSupportController {
		
	@Autowired
	private CsnService csnService;
	@Autowired
	private CsnTerminologyService csnTerminologyService;
	@Autowired
	private CsnTerminologyValidator csnTerminologyValidator;
	@Autowired
	private CsnTerminologyLanguageEditor csnTerminologyLanguageEditor;
	@Autowired
	private CsnEditor csnEditor;
	
	private static final String SUCCES_CREATE_PARAM = "successCreate";
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(CsnTerminologyLanguage.class, this.csnTerminologyLanguageEditor);
		binder.registerCustomEditor(Csn.class, this.csnEditor);
    }
	
	public CsnTerminologyController(){
		setEditFormView("csn/csn-terminology-edit");
	}
	
	@RequestMapping( value = "/admin/csn/{idCsn}/terminology/edit/{idTerminology}", method = RequestMethod.GET)
	public String showCsnTerminologyForm(@PathVariable Long idCsn, @PathVariable Long idTerminology, ModelMap modelMap, HttpServletRequest request) throws ItemNotFoundException{
		Csn csn = csnService.getById(idCsn);
		if(csn == null){
			createItemNotFoundError("ČSN with ID: " + idCsn + " was not found.");
		}
		
		CsnTerminology from = null;
		
		if(idTerminology == 0){
			from = new CsnTerminology();
			from.setId(0l);
			from.setCsn(csn);
		}else{
			from = csnTerminologyService.getById(idTerminology);
			if(from == null){
				createItemNotFoundError("ČSN terminology with ID: " + idTerminology + " was not found.");
			}
			
			if(request.getParameter(SUCCES_CREATE_PARAM) != null){
				modelMap.put(SUCCES_CREATE_PARAM, true);
			}
			
			
		}
		
		prepareModel(csn, from , modelMap, idTerminology);
		return getEditFormView();
	} 
	
	@RequestMapping( value = "/admin/csn/{idCsn}/terminology/edit/{idTerminology}", method = RequestMethod.POST)
	public String processCreate(
				@PathVariable Long idCsn, 
				@PathVariable Long idTerminology, 
				ModelMap modelMap,
				CsnTerminology form,
				BindingResult result
		
			) throws ItemNotFoundException{
		
		csnTerminologyValidator.validate(result, form);
		if(!result.hasErrors()){
			Long id = createOrUpdate(form);
			return "redirect:/admin/csn/"+idCsn+"/terminology/edit/"+id + "?"+SUCCES_CREATE_PARAM + "=true";
		}
		prepareModel(form.getCsn(), form , modelMap, idTerminology);
		return getEditFormView();
	} 
	
	
	private Long createOrUpdate(CsnTerminology form) throws ItemNotFoundException{
		Validate.notNull(form);
		
		CsnTerminology csnTerminology = null;
		
		if(form.getId() == null || form.getId() == 0){
			csnTerminology = new CsnTerminology();
		}else{
			csnTerminology = csnTerminologyService.getById(form.getId());
			if(csnTerminology == null){
				createItemNotFoundError("ČSN terminology with ID: " + form.getId() + " was not found.");
			}
		}
		
		csnTerminology.setTitle(form.getTitle());
		csnTerminology.setLanguage(form.getLanguage());
		csnTerminology.setContent(form.getContent());
		csnTerminology.setCode(CodeUtils.toSeoUrl(form.getTitle()));
		csnTerminology.setCsn(form.getCsn());
		csnTerminology.setSection(form.getSection());
		return csnTerminologyService.saveOrUpdate(csnTerminology);
	}
	
	
	private void prepareModel(Csn csn, CsnTerminology form,  ModelMap modelMap, Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", 2);
		model.put("csn", csn);
		model.put("fileDir", Constants.CSN_DIR_PREFIX + csn.getId());
		model.put("csnTerminologyLanguage", CsnTerminologyLanguage.getAll());
		modelMap.put("model", model);
		modelMap.put("id", id);
		modelMap.addAttribute("csnTerminology", form);
	}
}
