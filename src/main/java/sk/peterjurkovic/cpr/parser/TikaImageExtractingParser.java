package sk.peterjurkovic.cpr.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.extractor.ParserContainerExtractor;
import org.apache.tika.io.TikaInputStream;
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

          TikaInputStream tis = TikaInputStream.get(stream);
          
          
          logger.info("LENGHT: "+tis.getLength() + "AFP: " + tis.getFile().getAbsoluteFile());
        if(stream == null){
        	
        	logger.info(filename  + " stream is NULL");
        }
        

        try{  
        	handleImage(tis, filename,  type);
        }catch(Exception e){
        	logger.error("ERR " + e.getMessage());
        }
	}
	
	private void handleImage(InputStream stream, String filename, String type) {
        count++;
        
        // Give it a sensible name if needed
        if(filename == null) {
           filename = "image-" + count + ".";
           filename += type.substring(type.indexOf('/')+1);
        }
        
        TikaInputStream is = TikaInputStream.get(stream);
        ParserContainerExtractor containerExtractor = new ParserContainerExtractor();
        try {
        	logger.info(fileService.getFileSaveDir() + File.separator + "csn-3" + File.separator + filename);
        	File outFile = new File(fileService.getFileSaveDir() + File.separator + "csn-3" + File.separator + filename);
    		OutputStream os = null;
    		try{
    			os = new FileOutputStream(outFile);
    			IOUtils.copy(is, os);
    			os.flush();
    			
    		}catch (Exception e) {
    			logger.error("ERR: " + e.getMessage()); 
    		}
    		
        	logger.info("Extracting...");
			if(containerExtractor.isSupported(is)){
				logger.info("TIKA IS: supported.");
				containerExtractor.extract(is, new ParserContainerExtractor(), new ImageHandler());
			}else{
				logger.info("TIKA IS: not supported.");
			}
        	
			
		} catch (IOException | TikaException e1) {
			logger.info("IMAGE HANDLING ERR " + e1.getMessage());
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
        
    }

}
