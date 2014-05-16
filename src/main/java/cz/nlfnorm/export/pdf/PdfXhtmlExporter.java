package cz.nlfnorm.export.pdf;

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

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.lowagie.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component("pdfXhtmlExporter")
public class PdfXhtmlExporter {
	
	private static final String ENCODING = "utf-8";
	
	private final static Logger logger  = Logger.getLogger(PdfXhtmlExporter.class);
    private List<Resource> fonts = new ArrayList<Resource>();    
    private Configuration configuration;    
    private ITextRenderer renderer = new ITextRenderer();    
    
    public ByteArrayOutputStream generatePdf(final String ftlTemplate,final Map<String, Object> model,final String basePath) {
        try {
            Template temp = configuration.getTemplate(ftlTemplate, ENCODING);
            ByteArrayOutputStream htmlAsOs = new ByteArrayOutputStream();
            temp.process(model, new BufferedWriter(new OutputStreamWriter(htmlAsOs, ENCODING)));
            htmlAsOs.close();
            String content = htmlAsOs.toString().replaceAll("&(?!amp;|nbsp;|#)", "&amp;");
            logger.debug("Vysledný HTML dokument:" +  content);
            ByteArrayOutputStream pdfOutputStream = generatePdf(new ByteArrayInputStream(content.getBytes()), basePath);
            logger.info("Generovanie PDF je uspesne dokoncene.");
            return pdfOutputStream;
        } catch (Exception e) {
        	logger.error("Generovanie PDF zlyhalo ["+ftlTemplate+"].", e);
        	throw new RuntimeException("Some error occures. Can not generate PDF.", e);
        }
    }

   
    public ByteArrayOutputStream generatePdf(final InputStream xhtml, final String baseUrl) {
        ByteArrayOutputStream out = null;
        Document document = null;
        try {
            for (int i = 0; i < fonts.size(); i++) {
                renderer.getFontResolver().addFont(fonts.get(i).getFile().getPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            builder.setEntityResolver(FSEntityResolver.instance());
            InputSource is = new InputSource(new InputStreamReader(xhtml, ENCODING));
            document = builder.parse(is);
            renderer.setDocument(document, baseUrl);
            out = new ByteArrayOutputStream();
            renderer.layout();
            renderer.createPDF(out);
            out.close();
        }catch(SAXException e){ 
        	logger.error("Nepodaril sa parsovat výsledny HTML dokument", e);
            throw new RuntimeException();
        }catch (Exception e) {
        	logger.error("Generovanie PDF zlyhalo", e);
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
