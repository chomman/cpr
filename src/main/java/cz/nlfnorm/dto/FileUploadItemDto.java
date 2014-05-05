package cz.nlfnorm.dto;

import org.springframework.web.multipart.MultipartFile;

import cz.nlfnorm.entities.Csn;

public class FileUploadItemDto {
	
	private String saveDir;

	private MultipartFile fileData;
	
	private Csn csn;
	
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

	public Csn getCsn() {
		return csn;
	}

	public void setCsn(Csn csn) {
		this.csn = csn;
	}
	
	
	
	
	
}
