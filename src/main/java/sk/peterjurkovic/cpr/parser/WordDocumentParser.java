package sk.peterjurkovic.cpr.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.XHTMLContentHandler;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

@Component("WordDocumentParser")
public class WordDocumentParser implements Parser {
	
	private static final long serialVersionUID = -7507772018981932102L;

	private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.application("msword"));
	 
    public static final String MS_WORD_MIME_TYPE = "application/msword";
     
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	
	@Override
	public Set<MediaType> getSupportedTypes(ParseContext context) {
		return SUPPORTED_TYPES;
	}

	@Override
	public void parse( InputStream stream, ContentHandler handler,
            Metadata metadata, ParseContext context) throws IOException, SAXException, TikaException {
		logger.info("PARSING: start.");
        XHTMLContentHandler xhtml = new XHTMLContentHandler(handler, metadata);
        xhtml.startDocument();
        xhtml.endDocument();
		logger.info("PARSING: SUCCESSFULY FINISHED!");
	}
	

	
}
