package sk.peterjurkovic.cpr.export;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private final Log logger = LogFactory.getLog(getClass().getName());

    public static final String TIMES_FONT_NAME = "times.ttf";
    public static final String TIMES_BOLD_FONT_NAME = "timesbd.ttf";

    
    private List<Resource> fonts = new ArrayList<Resource>();
    
    private Configuration configuration;
    
    private ITextRenderer renderer = new ITextRenderer();

   
    public ByteArrayOutputStream generatePdf(String ftlTemplate, Map model, String baseUrl) {
        logger.info("Zahajeno generovani PDF z Freemarker sablony a modelu");
        try {
            // get freemarker template
            logger.debug("Zpracovani sablony a modelu zahajeno");
            Template temp = configuration.getTemplate(ftlTemplate, "utf-8");
            // process html template and create html version of report
            ByteArrayOutputStream htmlAsOs = new ByteArrayOutputStream();
            temp.process(model, new BufferedWriter(new OutputStreamWriter(htmlAsOs, "utf-8")));
            htmlAsOs.close();
            logger.debug("Zpracovani sablony a modelu dokonceno");

            logger.info("Osetreni generovaneho kodu na vyskyt problematickych znaku &");
            // v pripade potreby, ze by se neco nekde chybne prevadelo, je to zde potreba upravit a dat dalsi vyjimku
            String content = htmlAsOs.toString().replaceAll("&(?!amp;|nbsp;|#)", "&amp;");
            logger.debug("Vysledek konverze sablony do XHTML je: \n" + content);

            // create pdf as output stream
            ByteArrayOutputStream pdfAsOs = generatePdf(new ByteArrayInputStream(content.getBytes()), baseUrl);
            logger.info("Dokonceno generovani PDF na zaklade Freemarker sablony a modelu");
            return pdfAsOs;
        } catch (Exception e) {
            logger.error("Chyba pri generovani XHTML z Freemarkeru pri generovani PDF: " + e.getMessage(), e);
            throw new RuntimeException("Chyba pri generovani XHTML z Freemarkeru pri generovani PDF", e);
        }
    }

   
    public ByteArrayOutputStream generatePdf(InputStream xhtml, String baseUrl) {
        logger.info("Zahajen prevod XHTML -> PDF");
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
            logger.info("Uspesne ukoncen prevod XHTML -> PDF");
        } catch (Exception e) {
            logger.error("Chyba pri generovani PDF z XHTML: " + e.getMessage(), e);
            throw new RuntimeException("Chyba pri generovani PDF z XHTML", e);
        }
        return out;
    }

    public String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine() method. We iterate until the BufferedReader return null
         * which means there's no more data to read. Each line will appended to a StringBuilder and returned as String.
         */
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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
