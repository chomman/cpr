package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.entities.Partner;
import cz.nlfnorm.quasar.entities.TrainingLog;
import cz.nlfnorm.quasar.security.AccessUtils;
import cz.nlfnorm.quasar.services.TrainingLogService;
import cz.nlfnorm.utils.UserUtils;

@Controller
public class TrainingLogController extends LogControllerSupport{

	private final static int TAB = 12;
	private final static String LIST_MAPPING_URL = "/admin/quasar/training-logs";
	public final static String EDIT_MAPPING_URL = "/admin/quasar/training-log/{id}";
	
	@Autowired
	private TrainingLogService trainingLogService;
	
	public TrainingLogController(){
		setTableItemsView("training-log-list");
		setEditFormView("training-log-edit");
	}
	
	@InitBinder
	@Override
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handleProfileAuditLogs(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> model = handlePageRequest(request, trainingLogService, LIST_MAPPING_URL);
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleAuditLogEdit(ModelMap modelMap, @PathVariable long id, HttpServletRequest request) throws ItemNotFoundException{
		if(id == 0){
			id = createNewTrainingLog();
			return successUpdateRedirect(EDIT_MAPPING_URL.replace("{id}", id+""));
		}
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		final TrainingLog log = trainingLogService.getById(id);
		prepareModelFor(log, modelMap);
		return getEditFormView();
	}
	
	private void prepareModelFor(final TrainingLog log, ModelMap modelMap) throws ItemNotFoundException{
		final Map<String, Object> model = new HashMap<>();
		final boolean isEditable =  isEditable(log);
		model.put("log", log);
		model.put("statusType", ChangeLogStatusController.ACTION_TRAINING_LOG);
		if(isEditable){
			// TODO load auditors
			// 
		}
		model.put("isEditable", isEditable);
		modelMap.put(COMMAND, log);
		appendModel(modelMap, model);
	}
	
	private boolean isEditable(final TrainingLog log){
		return log.isEditable() && log.getChangedBy().equals(UserUtils.getLoggedUser());
	}
	
	private Long createNewTrainingLog(){
		if(AccessUtils.isLoggedUserPartnerManager()){
			return trainingLogService.createNew();
		}
		return trainingLogService.createNewToLoginedUser();
	}
	
	@Override
	protected void preparePageCriteria(Map<String, Object> criteria) {
		final User user = UserUtils.getLoggedUser();
		if(!user.isQuasarAdmin()){
			final List<Partner> partners = partnerService.getPartnersByManager(user);
			if(CollectionUtils.isNotEmpty(partners)){
				criteria.put(AuditorFilter.PARTNERS, partners);	
			}
			criteria.put(AuditorFilter.CREATED_BY, user.getId());
			criteria.put(AuditorFilter.AUDITOR, user.getId());
		}
	}
}
