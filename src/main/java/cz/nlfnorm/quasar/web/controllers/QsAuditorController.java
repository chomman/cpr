package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.services.QsAuditorAssesmentService;


public class QsAuditorController extends QuasarSupportController{

	private final static String QS_AUDITOR = "qs-auditor";
	
	private final static int TAB = 3; 
	private final static int SUB_TAB_QS_ADUTITOR = 2;
	
	private final static String MAPPING_URL = "/admin/quasar/manage/auditor/{auditorId}/is/{function}";
	
	@Autowired
	private AuditorService auditorService;
	@Autowired
	private QsAuditorAssesmentService qsAuditorAssesmentService;
	
	public QsAuditorController(){
		setViewName("auditor-edit");
	}
	
	@RequestMapping(MAPPING_URL)
	public String handleQsAuditor(ModelMap modelMap, @PathVariable Long auditorId, @PathVariable String function) throws ItemNotFoundException {
		final Auditor auditor = auditorService.getById(auditorId);
		validateNotNull(auditor, "Auditor was not found");
		if(function.equals(QS_AUDITOR)){
			evaluateQsAuditor(modelMap, auditor);
		}
		return getViewName();
	}
	
	
	private void evaluateQsAuditor(ModelMap modelMap, final Auditor auditor){
		prepareModel(modelMap,  auditor);
	}
	
	private void prepareModel(ModelMap map, final Auditor auditor){
		Map<String, Object> model = new HashMap<>();
		model.put("auditor", auditor);
		model.put("evaludatedAuditor", auditorService.getQsAuditorById(auditor.getId()));
		model.put("subTab", SUB_TAB_QS_ADUTITOR);
		appendTabNo(model, TAB);
		appendModel(map, model);
	}
}
