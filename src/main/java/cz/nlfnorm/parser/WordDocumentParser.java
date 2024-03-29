package cz.nlfnorm.parser;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;

import cz.nlfnorm.services.FileService;

@Component("wordDocumentParser")
public class WordDocumentParser  {

	private Logger logger = Logger.getLogger(getClass());
	
	public static final String TERMINOLOGY_SEPARATOR = "####";
	
	@Autowired
	private FileService fileService;
	
	
	public String parse(InputStream content, TikaProcessingContext tikaProcessingContext) {
		Validate.notNull(content);
		Validate.notNull(tikaProcessingContext);
		//AutoDetectParser parser = new AutoDetectParser();
		OfficeParser parser = new OfficeParser();
		StringWriter sw = new StringWriter();
	    ContentHandler handler = buildContentHandler(sw, tikaProcessingContext);
	    Metadata metadata = new Metadata();
	    ParseContext parseContext = new ParseContext();
	    parseContext.set(Parser.class, new TikaImageExtractingParser(fileService, tikaProcessingContext));
	   
	    String html = "";
	    try {
	    	parser.parse( content, handler, metadata, parseContext );
	    	tikaProcessingContext.logDomParsing();
	    	html = cleanHtml(sw.toString());
	    } catch(Exception e) {
	    	tikaProcessingContext.logError("při zpracování nastala chyba: "+ e.getMessage()); 
	    	logger.warn(e);
	    	return "";
	    }	
    	html = removeContentBefore(html);
    	html = removeContentAfter(html);
    	tikaProcessingContext.logInfo("dokument byl úspěšně zpracován. Počet znaků dokumentu po přečištění: "+ html.length());
	    return html;
	}
	
	
	private ContentHandler buildContentHandler(Writer output, TikaProcessingContext tikaProcessingContext){

		
	       SAXTransformerFactory factory = (SAXTransformerFactory)
	       SAXTransformerFactory.newInstance();
	       TransformerHandler handler = null;
	       
	       try {
	          handler = factory.newTransformerHandler();
	       } catch (TransformerConfigurationException e) {
	    	   tikaProcessingContext.logError("při zpracování nastala chyba (SAX error): "+ e.getMessage());
	    	   logger.warn(e);
	    	   return null;
	       }
	       
	       handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
	       handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
	       handler.getTransformer().setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	       handler.setResult(new StreamResult(output));
	       
	       // Change the image links as they go past
	       ContentHandler contentHandler = new TikaImageRewritingContentHandler(
	             handler, 
	             tikaProcessingContext.getNewImgSource(), 
	             tikaProcessingContext.getExtractedFilePrefix()
	       );
	       
	       contentHandler = new BodyContentHandler(contentHandler);

	       // All done
	       return contentHandler;
	}
	
	
	
	private String cleanHtml(String html){
		if(StringUtils.isBlank(html)){
			return "";
		}
		Whitelist whitelist =  Whitelist.relaxed();
		whitelist.preserveRelativeLinks(true);
		html =  Jsoup.clean(html, "http://www.nlfnorm.cz/", whitelist );
		return  html	 
				.replaceAll("<\\?xml.*?\\?>", "")
				.replaceAll("<b></b>", "")
				.replaceAll("</b>\\p{Z}*(,|.|;)?\\p{Z}*<b>", "")
				.replaceAll("<p>(\\pZ)*</p>", "")
				.replaceAll("(<div>(\\pZ)*</div>)", "")
				.replaceAll("</b>\\p{Z}*<b>", "")
				.replaceAll("&#13;","")
				.replaceAll("&#8804;", "&le;")
				.replaceAll("h\\d>", "b>")
				.replaceAll("&#x2264;", "&le;");
	}
	
	
	
	private String removeContentBefore(String html){
		if(StringUtils.isBlank(html)){
			return "";
		}
		html = html.substring( html.indexOf(TERMINOLOGY_SEPARATOR)+4, html.length());
		html = html.substring( html.indexOf("<table>"), html.length());
		return html;
	}
	
	private String removeContentAfter(String html){
		if(StringUtils.isBlank(html)){
			return "";
		}
		html = html.substring(0 , html.lastIndexOf(TERMINOLOGY_SEPARATOR));
		html = html.substring(0, html.lastIndexOf("</table>") + 8 );
		return html;
	}
	
}
