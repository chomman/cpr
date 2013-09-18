package sk.peterjurkovic.cpr.parser;

import java.beans.Transient;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.constants.ImageFormat;
import sk.peterjurkovic.cpr.entities.CsnTerminologyLog;
import sk.peterjurkovic.cpr.utils.ReversableDateTimeFormat;


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
	
	private LocalDateTime time;
	
	private static final DateTimeFormatter FORMATER = ReversableDateTimeFormat.forPattern("HH:mm:ss.SS");
	
	public TikaProcessingContext(){
		this.log = new CsnTerminologyLog();
		this.time = new LocalDateTime();
		initStart();
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
	
	public void initStart(){
		logInfo("Starting process..");
	}
	
	public void logDomParsing(){
		logInfo("Starting JSOUP parsing..");
	}
	
	public void logInfo(String messsage){
		log.logInfo(time.toString(FORMATER) + " | " + messsage);
	}
	
	public void logError(String messsage){
		log.logError(time.toString(FORMATER) + " | " + messsage);
	}
	
	public void logAlert(String messsage){
		log.logAlert(time.toString(FORMATER) + " | " + messsage);
	}
	
	public void logFinish(){
		logInfo("Process finished.");
	}
	
	public void incrementCZ(){
		log.incrementCZ();
	}
	
	public void incrementEN(){
		log.incrementEN();
	}
	
}
