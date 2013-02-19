package sk.peterjurkovic.cpr.export;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@Component("pdfView")
public class PdfByXhtmlrendererView extends AbstractView {
	
	@Autowired
	private PdfByXhtmlrendererExporter pdfByXhtmlrendererExporter;
	
	private String ftlTemplateName;
	
	private String outputFileName;
	
	@Override
    public void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream pdfAsOs = pdfByXhtmlrendererExporter.generatePdf(ftlTemplateName, model, "http://localhost:8080/cpr/");
        response.setContentLength(pdfAsOs.size());
        response.setHeader("Content-disposition", "attachment; filename=" + outputFileName);
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
