package cz.nlfnorm.parser;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.constants.ImageFormat;
import cz.nlfnorm.entities.CsnTerminologyLog;
import cz.nlfnorm.utils.ReversableDateTimeFormat;


/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 20, 2013
 *
 */
public class TikaProcessingContext {
	
	private Long csnId;
	
	private String extractedFilePrefix = "tika_";

	private String contextPath;
	
	private CsnTerminologyLog log;
	

	private static final DateTimeFormatter FORMATER = ReversableDateTimeFormat.forPattern("HH:mm:ss.SSS");
	
	public TikaProcessingContext(){
		this.log = new CsnTerminologyLog();
	}
	
	public String getCsnDir() {
		return Constants.CSN_DIR_PREFIX + csnId;
	}

	public Long getCsnId() {
		return csnId;
	}

	public void setCsnId(Long csnId) {
		this.csnId = csnId;
	}

	
	public String getExtractedFilePrefix() {
		return extractedFilePrefix;
	}

	public void setExtractedFilePrefix(String extractedFilePrefix) {
		this.extractedFilePrefix = extractedFilePrefix;
	}
		
	public String getNewImgSource(){
		return getContextPath() + Constants.IMAGE_URL_PREFIX + ImageFormat.IMAGE_NORMAL +"/"+ getCsnDir();
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		if (contextPath.length() > 1 && contextPath.endsWith("/")) {
		    contextPath = contextPath.substring(0, contextPath.length()-1);
		  }
		this.contextPath = contextPath;
	}

	public CsnTerminologyLog getLog() {
		return log;
	}

	public void setLog(CsnTerminologyLog log) {
		this.log = log;
	}
	
	public void incrementExtractedFile(){
		log.incremetImageCount();
	}
	
	
	public void logDomParsing(){
		logInfo("Začátek čtení termínů.");
	}
	
	public void logInfo(String messsage){
		log.logInfo(new LocalDateTime().toString(FORMATER) + " | " + messsage);
	}
	
	public void logError(String messsage){
		log.logError(new LocalDateTime().toString(FORMATER) + " | " + messsage);
	}
	
	public void logAlert(String messsage){
		log.logAlert(new LocalDateTime().toString(FORMATER) + " | " + messsage);
	}
	
	public void logFinish(){
		logInfo("Konec čtení termínů.");
	}
	
	public void incrementCZ(){
		log.incrementCZ();
	}
	
	public void incrementEN(){
		log.incrementEN();
	}
	
}
