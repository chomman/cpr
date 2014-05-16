package cz.nlfnorm.spring;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.view.AbstractView;

import cz.nlfnorm.export.pdf.PdfXhtmlExporter;
import cz.nlfnorm.utils.RequestUtils;


public class PdfXhtmlRendererView extends AbstractView {
	
	@Autowired
	private PdfXhtmlExporter pdfXhtmlExporter;
	
	private String ftlTemplateName;
	private String outputFileName;
	
	public PdfXhtmlRendererView(){
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
    public void renderMergedOutputModel(final Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream pdfAsOs = pdfXhtmlExporter.generatePdf(ftlTemplateName, model, RequestUtils.getApplicationUrlPrefix(request));
        response.setContentLength(pdfAsOs.size());
        response.setHeader("Content-disposition", "attachment; filename=" + outputFileName);
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        FileCopyUtils.copy(pdfAsOs.toByteArray(), response.getOutputStream());
        pdfAsOs.close();
    }

	public String getFtlTemplateName() {
		return ftlTemplateName;
	}

	public void setFtlTemplateName(String ftlTemplateName) {
		this.ftlTemplateName = ftlTemplateName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
}
