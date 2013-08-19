package sk.peterjurkovic.cpr.parser;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;

import sk.peterjurkovic.cpr.services.FileService;

@Component("wordDocumentParser")
public class WordDocumentParser  {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private FileService fileService;

	public String parse(InputStream content, TikaProcessContext tikaProcessContext) {
		Validate.notNull(content);
		Validate.notNull(tikaProcessContext);
		AutoDetectParser parser = new AutoDetectParser();
		StringWriter sw = new StringWriter();
	    ContentHandler handler = buildContentHandler(sw, tikaProcessContext);
	    Metadata metadata = new Metadata();
	    ParseContext parseContext = new ParseContext();
	    parseContext.set(Parser.class, new TikaImageExtractingParser(fileService, tikaProcessContext));
	   
	    try {
	    	parser.parse( content, handler, metadata, parseContext );
	       } catch(Exception e) {
	    	   logger.warn("Pri spracovavani dokumentu nastala chyba: "+ e.getMessage());  
	    }
	    	
	
		// As a string
		String html = cleanHtml(sw.toString());
	    logger.info("CLEANED OUTPUT: \n" + html);  
	    return html;
	}
	
	
	
	
	private ContentHandler buildContentHandler(Writer output, TikaProcessContext tikaProcessContext){

		
	       SAXTransformerFactory factory = (SAXTransformerFactory)
	       SAXTransformerFactory.newInstance();
	       TransformerHandler handler = null;
	       
	       try {
	          handler = factory.newTransformerHandler();
	       } catch (TransformerConfigurationException e) {
	    	   logger.warn(String.format("SAX Processing neni dostupny: ", e));
	       }
	       
	       handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
	       handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
	       handler.setResult(new StreamResult(output));
	       
	       // Change the image links as they go past
	       ContentHandler contentHandler = new TikaImageRewritingContentHandler(
	             handler, 
	             tikaProcessContext.getNewImgSource(), 
	             tikaProcessContext.getExtractedFilePrefix()
	       );
	       
	       contentHandler = new BodyContentHandler(contentHandler);

	       // All done
	       return contentHandler;
	}
	
	
	
	private String cleanHtml(String html){
		 return  html.replaceAll("<\\?xml.*?\\?>", "")
        .replaceAll("<p xmlns=\"http://www.w3.org/1999/xhtml\"","<p")
        .replaceAll("<h(\\d) xmlns=\"http://www.w3.org/1999/xhtml\"","<h\\1")
        .replaceAll("<div xmlns=\"http://www.w3.org/1999/xhtml\"","<div")
        .replaceAll("<table xmlns=\"http://www.w3.org/1999/xhtml\"","<table")
        .replaceAll("&#13;","")
        .replaceAll("<p class=\"(.*)\"","<p");
	}
	
	
	private String removeHeader(String html){
		return html.substring( html.indexOf("</div>")+6, html.length()  );
	}
	
	private String removeFooter(String html){
		logger.info("Footer found at: " + html.indexOf("<div class=\"footer\">"));
		return html.substring( 0 ,html.indexOf("<div class=\"footer\">") -1 );
	}
	
	private String removeFirstTable(String html){
		return html.substring(0, html.indexOf("</table>"));
	}
	
	
	private void sprlit(String html){
		String[] terminology = html.split("(?<=[<p>\\s<b>\\s\\d\\.\\s)");
	}
	
	
}
