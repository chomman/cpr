package cz.nlfnorm.quasar.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cz.nlfnorm.quasar.services.AuditLogService;

@Controller
public class AuditLogController extends QuasarSupportController {
	
	private final static String LIST_MAPPING_URL = "/admin/quasar/auditLogs";
	private final static String FORM_MAPPING_URL = "/admin/quasar/auditLog/{id}";

	@Autowired
	private AuditLogService auditLogService;
	
	public AuditLogController(){
		setTableItemsView("audit-log-list");
		setEditFormView("audit-log-edit");
	}
}
