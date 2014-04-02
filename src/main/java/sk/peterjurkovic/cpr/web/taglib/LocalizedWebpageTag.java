package sk.peterjurkovic.cpr.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.SystemLocale;
import sk.peterjurkovic.cpr.utils.WebpageUtils;

/**
 * JSP tag, ktory ziska lokalizavanu hodnotu atributu objektu.
 * 
 * @author peter.jurkovic
 * @date 02.04.2014
 */
public class LocalizedWebpageTag extends TagSupport {

	private static final long serialVersionUID = 6133102816924962677L;
	private Logger logger = Logger.getLogger(getClass());
	private Webpage webpage;
	private String fieldName;
	private boolean useDefaultLocale = false;
	
	@Override
	public int doStartTag() throws JspException {
				
		if(webpage == null){
			return SKIP_BODY;
		}
		
		if(StringUtils.isBlank(fieldName)){
			logger.error("fieldName may not be Empty");
			return SKIP_BODY;
		}
		
		try {
			this.pageContext.getOut().println(getLocalizedvalue());
		} catch (IOException e) {
			this.logger.error(e);
		}
		return SKIP_BODY;
	}
	
	protected String getLocalizedvalue() {
		if(useDefaultLocale){
			return WebpageUtils.getValueInLocale(getFieldName(), webpage, SystemLocale.getDefault());
		}else{
			return WebpageUtils.getLocalizedValue(fieldName, webpage);
		}
	}
	
	
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Webpage getWebpage() {
		return webpage;
	}
	public void setWebpage(Webpage webpage) {
		this.webpage = webpage;
	}
	public boolean isUseDefaultLocale() {
		return useDefaultLocale;
	}
	public void setUseDefaultLocale(boolean useDefaultLocale) {
		this.useDefaultLocale = useDefaultLocale;
	}
	
	
}
