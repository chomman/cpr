package sk.peterjurkovic.cpr.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileDto {
	
	private List<MultipartFile> files;
	
	private String saveDir;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public String getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}
	
	
	
	
	
}
