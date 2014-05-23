package cz.nlfnorm.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import cz.nlfnorm.dto.FileDto;



public interface FileService {
	
	
	String getFileSaveDir();
		
	void createDirectory(String dirName);
	
	void removeDirectory(String dirName);
	
	String saveFile(String originalFileName, InputStream image, String intoDir);
	
	String saveFile(String originalFilename, byte[] content, String intoDir);
	
	List<String> getImagesFromDirectory(String dirName);
	
	List<FileDto> readDirectory(String dirName);
		
	String saveAvatar(String fileName, byte[] content);
	
	boolean removeFile(String fileName, String directory);
	
	boolean removeFile(final String fileLocation);
	
	boolean removeAvatar(String avatarName);
	
	File getFile(String dirName, String fileName);
	
}
