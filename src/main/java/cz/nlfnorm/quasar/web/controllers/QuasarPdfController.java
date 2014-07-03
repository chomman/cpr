package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContext;

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.spring.PdfXhtmlRendererView;
import cz.nlfnorm.utils.CodeUtils;
import freemarker.log.Logger;

@Controller
public class QuasarPdfController {
	
	private final static String FILE_NAME_KEY = "fileName";
	private final static int ACTION_AUDITOR_SUMMARY = 1;
	private static Logger logger = Logger.getLogger(QuasarPdfController.class.getName());
	
	@Autowired
	private AuditorService auditorService;
	
	@RequestMapping(value = "/admin/quasar/pdf/{action}", method = RequestMethod.GET)
	public PdfXhtmlRendererView downloadSummary(
			@PathVariable int action, 
			ModelMap map, 
			HttpServletRequest request, 
			HttpServletResponse response) throws ItemNotFoundException, PageNotFoundEception{
		
		final Map<String, Object> model = prepareModelFor(action, request);
		PdfXhtmlRendererView pdfView = new PdfXhtmlRendererView();
		pdfView.setFtlTemplateName(getTemplateNameFor(action));
		pdfView.setOutputFileName(getFileNameSavedInModel(model));
		try {
			pdfView.setRenderOutput(true);
			pdfView.renderMergedOutputModel(model, request, response);
		} catch (Exception e) {
			logger.warn("Nepodarilo sa vygenerovat PDF [action="+action+"]", e);
		}
		return pdfView;
	}
	
	
	private String getTemplateNameFor(final int action) throws PageNotFoundEception{
		switch (action) {
		case ACTION_AUDITOR_SUMMARY:
			return "quasar-auditor-summary.ftl";
		default:
			throw new PageNotFoundEception();
		}
	}
	
	private Map<String, Object> prepareModelFor(final int action, HttpServletRequest request) throws PageNotFoundEception{
		switch (action) {
		case ACTION_AUDITOR_SUMMARY:
			return prepareInvoiceModel(request);
		default:
			throw new PageNotFoundEception();
		}
	}
	
	
	public Map<String, Object> prepareInvoiceModel(HttpServletRequest request) throws PageNotFoundEception{
		final Auditor auditor = getAuditorOrThrown(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("springMacroRequestContext", new RequestContext(request));
		model.put("eFunctions", auditorService.getEvaludatedAuditorFunctions(auditor));
		model.put("reassessmentDate", auditor.getReassessmentDate() == null ? "-" : auditor.getReassessmentDate().toString("dd.MM.yyyy"));
		String fileName = auditor.getReassessmentDate() == null ? "" : auditor.getReassessmentDate().toString("yyyy-MM-dd");
		fileName += CodeUtils.generateProperFilename(auditor.getName());
		model.put(FILE_NAME_KEY, fileName);
		return model;
	}
	
	private String getFileNameSavedInModel(final Map<String, Object> model){
		if(model.get(FILE_NAME_KEY) != null){ 
			return (String) model.get(FILE_NAME_KEY);
		}
		return "output.pdf";
	}
	
	private Auditor getAuditorOrThrown(HttpServletRequest request) throws PageNotFoundEception{
		Long auditorId = null;
		try{
			 auditorId = Long.valueOf(request.getParameter("id"));
		}catch(NumberFormatException e){
			throw new PageNotFoundEception();
		}
		final Auditor auditor = auditorService.getById(auditorId);
		if(auditor == null){
			throw new PageNotFoundEception();
		}
		return auditor;
	}
}
