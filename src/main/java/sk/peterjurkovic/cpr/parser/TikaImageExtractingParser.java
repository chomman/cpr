package sk.peterjurkovic.cpr.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import sk.peterjurkovic.cpr.services.FileService;

public class TikaImageExtractingParser implements Parser {
	
	private static final long serialVersionUID = -2205312969582379000L;
	private Set<MediaType> types;
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private FileService fileService;
	
	private int count = 0;
	
	public TikaImageExtractingParser(){
		// Our expected types
        types = new HashSet<MediaType>();
        types.add(MediaType.image("bmp"));
        types.add(MediaType.image("gif"));
        types.add(MediaType.image("jpg"));
        types.add(MediaType.image("jpeg"));
        types.add(MediaType.image("png"));
        types.add(MediaType.image("tiff"));
	}
	
	@Override
	public Set<MediaType> getSupportedTypes(ParseContext context) {
		return types;
	}

	@Override
	public void parse(InputStream stream, ContentHandler handler,
			Metadata metadata, ParseContext context) throws IOException,
			SAXException, TikaException {
		
		 String filename = metadata.get(Metadata.RESOURCE_NAME_KEY);
         String type = metadata.get(Metadata.CONTENT_TYPE);
         logger.info("Filename: " + filename + " type: " +type);
         
         boolean accept = false;
         if(type != null) {
            for(MediaType mt : types) {
               if(mt.toString().equals(type)) {
                  accept = true;
               }
            }
         }
         
         if(filename != null) {
             for(MediaType mt : types) {
                String ext = "." + mt.getSubtype();
                if(filename.endsWith(ext)) {
                   accept = true;
                }
             }
          }
          
          if(!accept){
        	  return;
          }
          
        handleImage(stream, filename,  type);
	}
	
	private void handleImage(InputStream stream, String filename, String type) {
        count++;
        
        // Give it a sensible name if needed
        if(filename == null) {
           filename = "image-" + count + ".";
           filename += type.substring(type.indexOf('/')+1);
        }
        
        logger.info(filename);
        try{
        fileService.saveFile(filename, stream, "csn-3");
        }catch(Exception e){
        	logger.warn("Can not save file: " + filename + " cause: " + e.getMessage());
        }
    }

}
