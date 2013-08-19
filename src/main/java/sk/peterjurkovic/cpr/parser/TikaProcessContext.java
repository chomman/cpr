package sk.peterjurkovic.cpr.parser;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.constants.ImageFormat;

public class TikaProcessContext {
	
	private Long csnId;
	
	private String extractedFilePrefix = "tika_";
	
	private int coutOfExtractedFiles = 0;
	
	private Long timeOfProcessing;

	private String contextPath;
	
	

	public String getCsnDir() {
		return Constants.CSN_DIR_PREFIX + csnId;
	}

	public Long getCsnId() {
		return csnId;
	}

	public void setCsnId(Long csnId) {
		this.csnId = csnId;
	}

	public int getCoutOfExtractedFiles() {
		return coutOfExtractedFiles;
	}

	public void setCoutOfExtractedFiles(int coutOfExtractedFiles) {
		this.coutOfExtractedFiles = coutOfExtractedFiles;
	}

	public Long getTimeOfProcessing() {
		return timeOfProcessing;
	}

	public void setTimeOfProcessing(Long timeOfProcessing) {
		this.timeOfProcessing = timeOfProcessing;
	}
	

	public String getExtractedFilePrefix() {
		return extractedFilePrefix;
	}

	public void setExtractedFilePrefix(String extractedFilePrefix) {
		this.extractedFilePrefix = extractedFilePrefix;
	}
	
	public void incremetCountOfExtractedFiles(){
		coutOfExtractedFiles++;
	}
	
	public String getNewImgSource(){
		return getContextPath() + "/"+ Constants.IMAGE_URL_PREFIX + ImageFormat.IMAGE_NORMAL +"/"+ getCsnDir();
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath.replaceAll("//", "");
	}
	
	
}
