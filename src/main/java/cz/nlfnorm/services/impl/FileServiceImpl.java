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

import cz.nlfnorm.dto.FileDto;
import cz.nlfnorm.services.FileService;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.validators.admin.ImageValidator;

@Service("fileService")
public class FileServiceImpl implements FileService {
	
	public static final String AVATARS_DIR_NAME = "avatars";
	public static final String IMAGES_DIR_NAME = "images";
	public static final String DOCUMENTS_DIR_NAME = "documents";
	public static final String QUASAR_DIR_NAME = "auth/quasar";
	
	@Value("#{config.file_save_dir}")
	private String fileSaveDir;
	
	@Autowired
	private ImageValidator imageValidator;
	
	
	protected Logger logger = Logger.getLogger(getClass());
	
	
	@PostConstruct
	public void initFileSaveDir(){
		try{
			File f = new File(fileSaveDir);
			if(!f.exists()){
				FileUtils.forceMkdir(f);
			}
			f = new File(getAvatarsDir());
			if(!f.exists()){
				FileUtils.forceMkdir(f);
			}
			f = new File(getDocumentsDir());
			if(!f.exists()){
				FileUtils.forceMkdir(f);
			}
			f = new File(getImagesDir());
			if(!f.exists()){
				FileUtils.forceMkdir(f);
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
	public void createDirectory(final String dirName){
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
	public void removeDirectory(final String dirName){
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
	public String saveFile(String originalFileName, InputStream is, final String intoDir) {
		return saveFile(originalFileName, is, intoDir, false);
	}
	
	@Override
	public String saveFile(final String originalFileName, InputStream is, final String intoDir, final boolean keepOriginalFilenName) {
	    try {
	        byte[] data = new byte[is.available()];
	        is.read(data);
	        is.close();
	        return saveFile(originalFileName, data, intoDir, keepOriginalFilenName);
	    } catch (IOException e) {
	        logger.warn("Obrázek " + originalFileName + " se nepodařilo ulozit.", e);
	        return null;
	    }
	}
	

	@Override
	public String saveFile(final String originalFilename, byte[] content,final String intoDir) {
	  return saveFile(originalFilename, content, intoDir, false);
	}
	
	@Override
	public String saveFile(String originalFilename, byte[] content,final String intoDir, final boolean keepOriginalFileName) {
		if(!keepOriginalFileName){
			originalFilename = CodeUtils.generateProperFilename(originalFilename);
		}
	    String path = fileSaveDir + File.separatorChar + intoDir + File.separatorChar + originalFilename;
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
	    return originalFilename;
	}
	
	@Override
	public List<FileDto> getImagesFromDirectory(final String dirName){
		return readDirectory(dirName, true);
	}

	public List<String> readDir(final String dirName, final String extension){
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
	

	@Override
	public String saveAvatar(String fileName, byte[] content) {
		Validate.notEmpty(fileName);
		if(!isAvatarsNameUniqe(fileName)){
			fileName = System.currentTimeMillis() + fileName; 
		}
		return saveFile(fileName, content, AVATARS_DIR_NAME + File.separatorChar );
	}
	
	
	private boolean isAvatarsNameUniqe(final String fileName){
		File file = new File(getAvatarsDir() + CodeUtils.generateProperFilename(fileName) ) ;
		return !file.exists();
	}
	
	
	private String getAvatarsDir(){
		return getAbsoluteDirLocation(AVATARS_DIR_NAME);
	}
	
	private String getImagesDir(){
		return getAbsoluteDirLocation(IMAGES_DIR_NAME);
	}
	
	private String getDocumentsDir(){
		return getAbsoluteDirLocation(DOCUMENTS_DIR_NAME);
	}
	
	private String getAbsoluteDirLocation(final String dirName){
		return fileSaveDir +  File.separatorChar + dirName + File.separatorChar;
	}
	
	


	@Override
	public boolean removeFile(final String fileName, final String directory) {
		Validate.notEmpty(fileName);
		Validate.notEmpty(directory);
		File file = new File(fileSaveDir +  File.separatorChar + directory + File.separatorChar + fileName);
		if(file.exists()){
			return file.delete();
		}
		return false;
	}
	
	@Override
	public boolean removeFile(final String fileLocation) {
		Validate.notEmpty(fileLocation);
		File file = new File(fileSaveDir +  File.separatorChar + fileLocation);
		if(file.exists()){
			if(file.isFile()){
				return file.delete();
			}else{
				try {
					FileUtils.deleteDirectory(file);
					return true;
				} catch (IOException e) {
					return true;
				}
			}
		}
		return false;
	}


	@Override
	public boolean removeAvatar(final String avatarName) {
		if(StringUtils.isNotBlank(avatarName)){
			File avatar = new File(getAvatarsDir() + avatarName);
			if(avatar.exists()){
				return avatar.delete();
			}
		}
		return false;
	}


	@Override
	public List<FileDto> readDirectory(final String dirName) {
		return readDirectory(dirName, false);
	}
	

	public List<FileDto> readDirectory(final String dirName, final boolean imagesOnly) {
		List<FileDto> fileList = new ArrayList<FileDto>();
		File file = new File(fileSaveDir + "/" + dirName);
		if(file.exists() && file.isDirectory()){
			for(File f : file.listFiles()){
				if(imagesOnly && !imageValidator.validate(f.getName()) && f.isFile()){
					continue;
				}
				FileDto fileDto = new FileDto();
				fileDto.setName(f.getName());
				fileDto.setDir(dirName);
				if(f.isFile()){
					fileDto.setSize(FileUtils.byteCountToDisplaySize(f.length()));
					fileDto.setExtension(FilenameUtils.getExtension(f.getName()));
				}
				fileDto.setDir(f.isDirectory());
				fileList.add(fileDto);
			}
		}
		return fileList;
	}


	@Override
	public File getFile(final String dirName,final String fileName) {
		final String absolutePath = getFileSaveDir() + "/" + dirName + "/"+ fileName;
		File file = new File(absolutePath);
		if(file.exists()){
			return file;
		}
		return null;
	}
	
	@Override
	public File getFile(final String fileLocation) {
		final String absolutePath = getFileSaveDir() + "/" + fileLocation;
		File file = new File(absolutePath);
		if(file.exists() && file.isFile()){
			return file;
		}
		return null;
	}
}
