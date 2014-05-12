package cz.nlfnorm.web.taglib.webpage;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.utils.WebpageUtils;

@SuppressWarnings("serial")
public class WebpageLinkTag  extends RequestContextAwareTag{
		
	private Webpage webpage;
	protected Logger logger = Logger.getLogger(getClass());

	@Override
	protected int doStartTagInternal() throws Exception {
		pageContext.getOut().print(getWebpageUrl());
		return SKIP_PAGE;
	}
	
	protected String getWebpageUrl() {
		if(webpage != null){
			try{
				return WebpageUtils.getUrlFor(webpage, getRequestContext().getContextPath());
			}catch(Exception e){
				logger.warn(e);
			}
		}
		return "";
	}

	public Webpage getWebpage() {
		return webpage;
	}

	public void setWebpage(Webpage webpage) {
		this.webpage = webpage;
	}

	
	
}
