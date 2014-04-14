package sk.peterjurkovic.cpr.web.taglib.webpage;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.utils.WebpageUtils;

public class WebpageBreadcrumbTag extends WebpageUrlTag {
	
	private Collection<Webpage>  webpages;
	private String bcId;
	private String bcCssClass;
	private String separator = "&raquo;";
	
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(CollectionUtils.isNotEmpty(webpages)){
			pageContext.getOut().print(buildBreadcrumb());
		}
		return SKIP_PAGE;
	}
	
	
	private StringBuilder buildBreadcrumb(){
		StringBuilder html = new StringBuilder();
		html.append("<div");
		appendCssStyles(html);
		appendId(html);
		html.append(">");
		Iterator<Webpage> iterator = webpages.iterator();
		while (iterator.hasNext()) {
			 Webpage webpage = iterator.next();
			if(iterator.hasNext()){
				setWebpage(webpage);
				html.append( buildTag() )
					.append(separator);;
			}else{
				html.append("<span>")
				    .append(WebpageUtils.getLocalizedValue("name",webpage) )
				    .append("</span>");
			}
		}
		return html;
	}
	
	
	public void appendCssStyles(StringBuilder url){
		if(StringUtils.isNotBlank(bcCssClass)){
			url.append(" class=\"").append(bcCssClass).append("\" ");
		}
	}
	
	public void appendId(StringBuilder url){
		if(StringUtils.isNotBlank(bcId)){
			url.append(" id=\"").append(bcId).append("\" ");
		}
	}
	
	
	
	public Collection<Webpage> getWebpages() {
		return webpages;
	}
	public void setWebpages(Collection<Webpage> webpages) {
		this.webpages = webpages;
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
