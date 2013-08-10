package sk.peterjurkovic.cpr.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadItemDto {
	
	private String saveDir;

	private MultipartFile fileData;
	
	public String getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
	
	
	
	
	
}
