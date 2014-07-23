package cz.nlfnorm.quasar.web.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.entities.Partner;
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
