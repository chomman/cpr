package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.utils.RequestUtils;

@Controller
public class AuditLogController extends QuasarSupportController {
	
	private final static int TAB = 8; 
	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/audit-logs";
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/audit-log/{id}";

	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private PartnerService partnerService;
	
	public AuditLogController(){
		setTableItemsView("audit-log-list");
		setEditFormView("audit-log-edit");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handlePartnerListPage(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<>();
		final int currentPage = RequestUtils.getPageNumber(request);
		final Map<String, Object> citeria = RequestUtils.getRequestParameterMap(request);
		final PageDto page = auditLogService.getPage(citeria, currentPage);
		if(page.getCount() > 0){
			model.put("paginationLinks", getPaginationItems(request, citeria, page.getCount(), "/admin/quasar/manage/auditors"));
			model.put("logs", page.getItems());
		}
		model.put("statuses", LogStatus.getAll());
		model.put("params", citeria);
		model.put("partners", partnerService.getAll());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
}
