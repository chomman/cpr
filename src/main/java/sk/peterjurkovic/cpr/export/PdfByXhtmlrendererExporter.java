package sk.peterjurkovic.cpr.export;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;
import org.xml.sax.InputSource;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component("pdfByXhtmlrendererExporter")
public class PdfByXhtmlrendererExporter {

    private List<Resource> fonts = new ArrayList<Resource>();
    
    private Configuration configuration;
    
    private ITextRenderer renderer = new ITextRenderer();

   
    public ByteArrayOutputStream generatePdf(String ftlTemplate, Map<String, Object> model, String baseUrl) {
        try {
            Template temp = configuration.getTemplate(ftlTemplate, "utf-8");
            ByteArrayOutputStream htmlAsOs = new ByteArrayOutputStream();
            temp.process(model, new BufferedWriter(new OutputStreamWriter(htmlAsOs, "utf-8")));
            htmlAsOs.close();
            ByteArrayOutputStream pdfAsOs = generatePdf(new ByteArrayInputStream(htmlAsOs.toString().getBytes()), baseUrl);
            return pdfAsOs;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

   
    public ByteArrayOutputStream generatePdf(InputStream xhtml, String baseUrl) {
        ByteArrayOutputStream out = null;
        Document document = null;
        try {
            for (int i = 0; i < fonts.size(); i++) {
                renderer.getFontResolver().addFont(fonts.get(i).getFile().getPath(), "cp1250", true);
            }
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            builder.setEntityResolver(FSEntityResolver.instance());
            document = builder.parse(new InputSource(new InputStreamReader(xhtml, "utf-8")));
            renderer.setDocument(document, baseUrl);
            out = new ByteArrayOutputStream();
            renderer.layout();
            renderer.createPDF(out);
            out.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return out;
    }

    public String getFontPath(String fontFileName) {
        try {
            for (int i = 0; i < fonts.size(); i++) {
                if (fonts.get(i).getFilename().equals(fontFileName)) {
                    return fonts.get(i).getFile().getPath();
                }
            }
        } catch (Exception e) {}
        return null;
    }


    public List<Resource> getFonts() {
        return fonts;
    }

    public void setFonts(List<Resource> fonts) {
        this.fonts = fonts;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public ITextRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(ITextRenderer renderer) {
        this.renderer = renderer;
    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.configuration = freeMarkerConfigurer.getConfiguration();
    }
	
	
}
