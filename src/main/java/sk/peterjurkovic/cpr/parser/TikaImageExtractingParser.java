package sk.peterjurkovic.cpr.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import sk.peterjurkovic.cpr.services.FileService;

public class TikaImageExtractingParser implements Parser {
	
	private static final long serialVersionUID = -2205312969582379000L;
	private Set<MediaType> types;
	private Logger logger = Logger.getLogger(getClass());
	private TikaProcessContext tikaProcessContext;
	private FileService fileService;
	int count = 0;
	public TikaImageExtractingParser(FileService fileService, TikaProcessContext tikaProcessContext){
        types = new HashSet<MediaType>();
        types.add(MediaType.image("bmp"));
        types.add(MediaType.image("gif"));
        types.add(MediaType.image("jpg"));
        types.add(MediaType.image("jpeg"));
        types.add(MediaType.image("png"));
        types.add(MediaType.image("tiff"));
        types.add(MediaType.image("wmf"));
        this.fileService = fileService;
        this.tikaProcessContext = tikaProcessContext;
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
        
         saveFile(stream, filename, type);
	}
	

	
	private void saveFile(InputStream stream, String filename, String type) {
		count++;
        
        // Give it a sensible name if needed
        if(filename == null) {
           filename = tikaProcessContext.getExtractedFilePrefix() + count + "." + type.substring(type.indexOf('/')+1);
        }
       
        TikaInputStream is = TikaInputStream.get(stream);
        try {
        	logger.info(fileService.getFileSaveDir() + tikaProcessContext.getCsnDir() + File.separator + tikaProcessContext.getExtractedFilePrefix() + filename);
        	File outFile = new File(fileService.getFileSaveDir() + tikaProcessContext.getCsnDir() + File.separator + tikaProcessContext.getExtractedFilePrefix()  + filename);
        	if(outFile.exists()){
        		FileUtils.forceDelete(outFile);
        	}
    		OutputStream os = null;
			os = new FileOutputStream(outFile);
			IOUtils.copy(is, os);
			os.flush();
			tikaProcessContext.incremetCountOfExtractedFiles();
		} catch (IOException e) {
			logger.warn("Subor: " + filename +" sa z CSN ID: " + tikaProcessContext.getCsnId() + " nepodarilo extrahovat, dovod: " + e.getMessage());
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				logger.warn("InputStream sa nepodarilo zatvorit, dovod: " + e.getMessage());
			}
		} 
        
        
    }

}
