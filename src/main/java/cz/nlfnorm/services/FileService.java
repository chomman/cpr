package cz.nlfnorm.services;

import java.io.InputStream;
import java.util.List;



public interface FileService {
	
	
	String getFileSaveDir();
		
	void createDirectory(String dirName);
	
	void removeDirectory(String dirName);
	
	String saveFile(String originalFileName, InputStream image, String intoDir);
	
	String saveFile(String originalFilename, byte[] content, String intoDir);
	
	List<String> getImagesFromDirectory(String dirName);
	
	void convertImage(String i, String o);
	
	String saveAvatar(String fileName, byte[] content);
	
	boolean removeFile(String fileName, String directory);
	
	boolean removeAvatar(String avatarName);
	
}
