package sk.peterjurkovic.cpr.web.taglib.webpage;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import sk.peterjurkovic.cpr.context.ContextHolder;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.utils.WebpageUtils;

public class WebpageBreadcrumbTag extends WebpageUrlTag {
	
	private String bcId;
	private String bcCssClass;
	private String separator = "&raquo;";
	
	@Autowired
	private MessageSource messageSource;
	
	public WebpageBreadcrumbTag(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(getWebpage() != null || !getWebpage().isHomepage()){
			pageContext.getOut().print(buildBreadcrumb());
		}
		return SKIP_PAGE;
	}
	
	
	private StringBuilder buildBreadcrumb(){
		StringBuilder html = new StringBuilder();
		html.append("<div");
		appendBcCssStyles(html);
		appendBcId(html);
		html.append(">");
		boolean isFirst = true;
		List<Webpage> webpageList = WebpageUtils.getBreadcrumbFor(getWebpage());
		Iterator<Webpage> iterator = webpageList.iterator();
		while (iterator.hasNext()) {
			if(isFirst){
				String locationLabel = messageSource.getMessage("location", null, ContextHolder.getLocale());
				html.append("<span class=\"bc-info\">")
					.append(locationLabel)
					.append(": </span>");
				isFirst = false;
			}
			final Webpage webpage = iterator.next();
			setWebpage(webpage);
			if(iterator.hasNext()){
				html.append( buildTag() )
					.append(separator);;
			}else{
				html.append("<span>")
				    .append(WebpageUtils.getLocalizedValue("name", webpage) )
				    .append("</span>");
			}
		}
		return html.append("</div>");
	}
	
	
	public void appendBcCssStyles(StringBuilder url){
		if(StringUtils.isNotBlank(bcCssClass)){
			url.append(" class=\"").append(bcCssClass).append("\" ");
		}
	}
	
	public void appendBcId(StringBuilder url){
		if(StringUtils.isNotBlank(bcId)){
			url.append(" id=\"").append(bcId).append("\" ");
		}
	}
	

	public String getBcId() {
		return bcId;
	}


	public void setBcId(String bcId) {
		this.bcId = bcId;
	}


	public String getBcCssClass() {
		return bcCssClass;
	}


	public void setBcCssClass(String bcCssClass) {
		this.bcCssClass = bcCssClass;
	}


	public String getSeparator() {
		return separator;
	}


	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
}
