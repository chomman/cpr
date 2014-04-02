package sk.peterjurkovic.cpr.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import sk.peterjurkovic.cpr.entities.Webpage;

public class WebpageUrlTag extends TagSupport {
	
	private Webpage webpage;
	private String anchorText;
	private String cssClass = "";
	private String id;
	private Logger logger = Logger.getLogger(getClass());

	
	@Override
	public int doStartTag() throws JspException {
		
		if(webpage == null){
			throw new JspException("Webpage can not be NULL.");
		}
		
		if(!(webpage instanceof Webpage)) {
			logger.error("Given object can not be instance of String");
			return SKIP_BODY;
		}
		
		return SKIP_BODY;
	}
}
