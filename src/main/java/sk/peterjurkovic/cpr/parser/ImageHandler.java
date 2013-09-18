package sk.peterjurkovic.cpr.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tika.extractor.EmbeddedResourceHandler;
import org.apache.tika.mime.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.services.FileService;

public class ImageHandler implements EmbeddedResourceHandler {

	@Autowired
	private FileService fileService;
	
	private String saveDir;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void handle(String fileName, MediaType mediaType, InputStream stream) {
		logger.error("Hanled image" + fileName); 
		File outFile = new File(fileService.getFileSaveDir() + File.separator + saveDir + File.separator + fileName);
		OutputStream os = null;
		try{
			os = new FileOutputStream(outFile);
			IOUtils.copy(stream, os);
			os.flush();
			
		}catch (Exception e) {
			logger.error("Handle error: " + e.getMessage()); 
		}

		fileService.saveFile(fileName, stream, saveDir);
	}

	public String getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}

	
}
