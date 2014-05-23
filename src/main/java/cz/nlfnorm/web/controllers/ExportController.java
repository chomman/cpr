package cz.nlfnorm.web.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContext;

import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.export.pdf.PdfXhtmlExporter;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.spring.PdfXhtmlRendererView;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;

@Controller
public class ExportController extends AdminSupportController {
	
	public final static String INVOICE_FLT_TEMPLATE = "invoice-portal.ftl";
	
	@Autowired
	private PortalOrderService portalOrderService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private BasicSettingsService basicSettingsService;
	@Autowired
	private PdfXhtmlExporter pdfXhtmlExporter;
	
	
	
	@RequestMapping(value = "/auth/order/pdf/{code}", method = RequestMethod.GET)
	public PdfXhtmlRendererView downloadPDF(
			@PathVariable String code, ModelMap map, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			@RequestParam("type") int type) throws ItemNotFoundException, PageNotFoundEception{
		
		Map<String, Object> model = preparePDFModel(code, new RequestContext(request), type);
		PdfXhtmlRendererView pdfView = new PdfXhtmlRendererView();
		pdfView.setFtlTemplateName(INVOICE_FLT_TEMPLATE);
		pdfView.setOutputFileName(portalOrderService.getFileNameFor(type, (PortalOrder)model.get("portalOrder")));
		try {
			pdfView.setRenderOutput(true);
			pdfView.renderMergedOutputModel(model, request, response);
		} catch (Exception e) {
			logger.warn("Nepodarilo sa vygenerovat PDF [type="+type+"][oid="+code+"]", e);
		}
		return pdfView;
	}
	
	@RequestMapping(value = "/auth/order/print/{code}", method = RequestMethod.GET)
	public String printPDF(
			@PathVariable String code, ModelMap map, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			@RequestParam("type") int type) throws ItemNotFoundException, PageNotFoundEception{
		
		Map<String, Object> model =  preparePDFModel(code, new RequestContext(request), type);
		try {
			map.put("content",  pdfXhtmlExporter.geneareXhtml(INVOICE_FLT_TEMPLATE, model));
		} catch (Exception e) {
			logger.warn("Nepodarilo sa vygenerovat XHTML [type="+type+"][oid="+code+"]", e);
		}
		return "/include/print";
	}
	
	
	private Map<String, Object> preparePDFModel(final String code, RequestContext reqContext, int type) throws ItemNotFoundException, PageNotFoundEception{
		final PortalOrder portalOrder = portalOrderService.getByCode(code);
		if(portalOrder == null){
			throw new PageNotFoundEception();
		}
		final User user = UserUtils.getLoggedUser();
		if(user.isAdministrator() || user.equals(portalOrder.getUser())){
			return portalOrderService.prepareInvoiceModel(portalOrder, basicSettingsService.getBasicSettings(), type, reqContext);
		}
		throw new AccessDeniedException("User [id="+user.getId()+"] tried to access order [oid="+ portalOrder.getId() +"]");
	}
	
	
}
