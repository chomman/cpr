package cz.nlfnorm.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cz.nlfnorm.services.FileService;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.validators.admin.ImageValidator;

@Service("fileService")
public class FileServiceImpl implements FileService {
	
	private static final String AVATARS_DIR_NAME = "avatars";
	
	@Value("#{config.file_save_dir}")
	private String fileSaveDir;
	
	@Autowired
	private ImageValidator imageValidator;
	
	
	protected Logger logger = Logger.getLogger(getClass());
	
	
	@PostConstruct
	public void initFileSaveDir(){
		try{
			File baseFileDir = new File(fileSaveDir);
			if(!baseFileDir.exists()){
				FileUtils.forceMkdir(baseFileDir);
			}
			File avatarsDir = new File(getAvatarsDir());
			if(!avatarsDir.exists()){
				FileUtils.forceMkdir(avatarsDir);
			}
		}catch(IOException e){
			logger.error(e);
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
					logger.error("Adresar '"+dirName +"' nebol vytvoreny: " + e.getMessage());
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
	
	@Override
	public String saveFile(final String originalFileName, InputStream image, final String intoDir) {
	    //Validate.notNull(originalFileName);
	    //Validate.notNull(image);
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
	
	@Override
	public String saveFile(final String originalFilename, byte[] content,final String intoDir) {
	    String filename = CodeUtils.generateProperFilename(originalFilename);
	    String path = fileSaveDir + File.separatorChar + intoDir + File.separatorChar + filename;
	    try {
	    	File dir = new File(fileSaveDir + File.separatorChar + intoDir);
	    	
	    	if(!dir.exists()){
	    		createDirectory(intoDir);
	    	}
	    	
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
	
	@Override
	public List<String> getImagesFromDirectory(String dirName){
		List<String> files = new ArrayList<String>();
			File file = new File(fileSaveDir + "/" + dirName);
			
			if(file.exists() && file.isDirectory()){
				for(File f : file.listFiles()){
					if(imageValidator.validate(f.getName())){
						files.add(dirName+"/"+f.getName());
					}
				}
			}
			
		return files;
	}

	public List<String> readDir(String dirName, String extension){
		List<String> files = new ArrayList<String>();
			File file = new File(fileSaveDir + "/" + dirName);
			
			if(file.exists() && file.isDirectory()){
				for(File f : file.listFiles()){
					if(FilenameUtils.isExtension(f.getName(), extension)){
						files.add(dirName+"/"+f.getName());
					}
				}
			}
			
		return files;
	}
	
	
	public void convertImage(String inputSrc, String outpuSrc){
		logger.info(inputSrc + " => " + outpuSrc);
		
	}


	@Override
	public String saveAvatar(String fileName, byte[] content) {
		Validate.notEmpty(fileName);
		if(!isAvatarsNameUniqe(fileName)){
			fileName = System.currentTimeMillis() + fileName; 
		}
		return saveFile(fileName, content, AVATARS_DIR_NAME + File.separatorChar );
	}
	
	
	private boolean isAvatarsNameUniqe(String fileName){
		File file = new File(getAvatarsDir() + CodeUtils.generateProperFilename(fileName) ) ;
		return !file.exists();
	}
	
	
	private String getAvatarsDir(){
		return fileSaveDir +  File.separatorChar + AVATARS_DIR_NAME + File.separatorChar;
	}


	@Override
	public boolean removeFile(String fileName, String directory) {
		Validate.notEmpty(fileName);
		Validate.notEmpty(directory);
		File file = new File(fileSaveDir +  File.separatorChar + directory + File.separatorChar + fileName);
		if(file.exists()){
			return file.delete();
		}
		return false;
	}


	@Override
	public boolean removeAvatar(String avatarName) {
		if(StringUtils.isNotBlank(avatarName)){
			File avatar = new File(getAvatarsDir() + avatarName);
			if(avatar.exists()){
				return avatar.delete();
			}
		}
		return false;
	}
}
