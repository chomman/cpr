package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 7, 2013
 *
 */
@Controller
public class CsnTerminologyController extends SupportAdminController {
		
	@Autowired
	private CsnService csnService;
	@Autowired
	private CsnTerminologyService csnTerminologyService;
	
	
	public CsnTerminologyController(){
		setEditFormView("csn/csn-terminology-edit");
	}
	
	@RequestMapping( value = "/admin/csn/{idCsn}/terminology/edit/{idTerminology}", method = RequestMethod.GET)
	public String showCsnTerminologyForm(@PathVariable Long idCsn, @PathVariable Long idTerminology, ModelMap modelMap) throws ItemNotFoundException{
		Csn csn = csnService.getById(idCsn);
		if(csn == null){
			createItemNotFoundError("ČSN with ID: " + idCsn + " was not found.");
		}
		
		CsnTerminology from = null;
		
		if(idTerminology == 0){
			from = new CsnTerminology();
			from.setId(0l);
		}else{
			from = csnTerminologyService.getById(idTerminology);
			if(from == null){
				createItemNotFoundError("ČSN terminology with ID: " + idTerminology + " was not found.");
			}
		}
		
		prepareModel(csn, from , modelMap, idTerminology);
		return getEditFormView();
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
