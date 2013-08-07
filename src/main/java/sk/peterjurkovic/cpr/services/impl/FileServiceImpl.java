package sk.peterjurkovic.cpr.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sk.peterjurkovic.cpr.services.FileService;
import sk.peterjurkovic.cpr.utils.CodeUtils;

@Service("fileService")
public class FileServiceImpl implements FileService {
	
	@Value("#{config.file_save_dir}")
	private String fileSaveDir;
	
	
	protected Logger logger = Logger.getLogger(getClass());
	
	
	@PostConstruct
	public void initFileSaveDir(){
		try{
		File dir = new File(fileSaveDir);
		if(!dir.exists()){
			FileUtils.forceMkdir(dir);
		}
		}catch(IOException e){
			logger.info("Adresar nebol vytvoreny: " + e.getMessage());
		}
	}


	@Override
	public String getFileSaveDir() {
		return fileSaveDir;
	}
	
	@Override
	public void createDirectory(String dirName){
		if(StringUtils.isNotBlank(dirName)){
			File dir = new File(fileSaveDir + "/" + dirName);
			if(!dir.exists()){
				try {
					FileUtils.forceMkdir(dir);
				} catch (IOException e) {
					logger.info("Adresar '"+dirName +"' nebol vytvoreny: " + e.getMessage());
				}
			}
		}
	}
	
	@Override
	public void removeDirectory(String dirName){
		if(StringUtils.isNotBlank(dirName)){
			File dir = new File(fileSaveDir + "/" + dirName);
			if(dir.exists()){
				try {
					for (File file : dir.listFiles()) {
					    FileDeleteStrategy.FORCE.delete(file);
					} 
					FileUtils.deleteDirectory(dir);
				} catch (IOException e) {
					logger.info("Adresar '"+dirName +"' sa nepodarilo ostranit: " + e.getMessage());
				}
			}
		}
	}
	

	public String saveFile(final String originalFileName, InputStream image,final String intoDir) {
	    Validate.notNull(originalFileName);
	    Validate.notNull(image);
	    try {
	        byte[] data = new byte[image.available()];
	        image.read(data);
	        image.close();
	        return saveFile(originalFileName, data, intoDir);
	    } catch (IOException e) {
	        logger.warn("Obrázek " + originalFileName + " se nepodařilo ulozit.", e);
	        return null;
	    }
	}
	
	
	public String saveFile(final String originalFilename, byte[] content,final String intoDir) {
	    String filename = CodeUtils.toSeoUrl(originalFilename);
	    String path = fileSaveDir + File.separatorChar + intoDir + File.separatorChar + filename;
	    try {
	        File file = new File(path);
	        if(file.exists()){
	        	FileUtils.forceDelete(file);
	        }
	        FileUtils.writeByteArrayToFile(file, content);
	    } catch (IOException ioe) {
	        logger.info("Soubor se nepodarilo ulozit: " + ioe.getMessage(), ioe);
	        ioe.printStackTrace();
	    }
	    return filename;
	}
	
}
