package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.services.DossierReportService;
import cz.nlfnorm.quasar.services.TrainingLogService;
import cz.nlfnorm.utils.UserUtils;

@Controller
public class QuasarDashboard extends QuasarSupportController {
	
	private final static int TYPE_AUDIT_LOGS = 1;
	private final static int TYPE_DOSSIER_REPORTS = 2;
	private final static int TYPE_TRAINING_LOGS = 3;
	
	public static final String QUASAR_DASHBOARD_URL = "/admin/quasar/dashboard";
	
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private DossierReportService dossierReportService;
	@Autowired
	private TrainingLogService trainingLogService;
	
	@RequestMapping(QUASAR_DASHBOARD_URL)
	public String showDashboard(ModelMap modelMap){
		if(UserUtils.getLoggedUser().isQuasarAdmin()){
			return getViewDir() + "dashboard" ;
		}
		return getViewDir() + "profile/dashboard" ;
	}
	
	
	@RequestMapping(value =  "/admin/quasar/async/widget/{type}" )
	public ModelAndView handleWidgetRequest(@PathVariable int type) throws PageNotFoundEception{
		return prepareModelAndView(type);
	}
	
	private ModelAndView prepareModelAndView(final int type) throws PageNotFoundEception{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("isQuasarAdmin", UserUtils.getLoggedUser().isQuasarAdmin());
		switch (type) {
		case TYPE_AUDIT_LOGS:
			return getAuditLogModelAndView(model);
		case TYPE_DOSSIER_REPORTS:
			return getDosierReportsLogModelAndView(model);
		case TYPE_TRAINING_LOGS:
			return getTrainingLogModelAndView(model);
		}
		throw new PageNotFoundEception();
	}
	
	
	private ModelAndView getAuditLogModelAndView(Map<String, Object> model){
		return getView(model, "table-rows-audit-log", getAuitLogItems());
	}
	
	private ModelAndView getDosierReportsLogModelAndView(Map<String, Object> model){
		return getView(model, "table-rows-dossier-report", getDossierRerportItems());
	}
	
	private ModelAndView getTrainingLogModelAndView(Map<String, Object> model){
		return getView(model, "table-rows-training-log", getTrainingLogs());
	}
	
	private ModelAndView getView(Map<String, Object> model, String view, List<Object> items){
		ModelMap map = new ModelMap();
		model.put(LOG_ITEMS_KEY, items);
		appendModel(map, model);
		return new ModelAndView(getViewDir() + "logs/" + view, map);
	}
		
	private List<Object> getAuitLogItems(){
		return auditLogService.getPage(getCriteria()).getItems();
	}
	
	private List<Object> getDossierRerportItems(){
		return dossierReportService.getPage(getCriteria()).getItems();
	}
	
	private List<Object> getTrainingLogs(){
		return trainingLogService.getPage( getTrainingLogCriteria()).getItems();
	}
	
	private Map<String, Object> getCriteria(){
		Map<String, Object> criteria = new HashMap<>();
		if(!UserUtils.getLoggedUser().isQuasarAdmin()){
			criteria.put(AuditorFilter.AUDITOR, UserUtils.getLoggedUser().getId());
		}
		return criteria;
	}
	
	private Map<String, Object> getTrainingLogCriteria(){
		Map<String, Object> criteria = new HashMap<>();
		final User user = UserUtils.getLoggedUser();
		if(!user.isQuasarAdmin()){
			criteria.put(AuditorFilter.CREATED_BY, user.getId());
			criteria.put(AuditorFilter.AUDITOR, user.getId());
		}
		return criteria;
	}
	
}
