package sk.peterjurkovic.cpr.web.taglib.webpage;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import sk.peterjurkovic.cpr.entities.Webpage;

@SuppressWarnings("serial")
public class WebpageNavTag extends WebpageUrlTag {
	
	private Collection<Webpage> webpages;
	private String ulCssClass;
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(CollectionUtils.isNotEmpty(webpages)){
			pageContext.getOut().print(buildItems().toString() );
		}		
        return SKIP_PAGE;
	}
	
	protected StringBuilder buildItems() {
		StringBuilder html = new StringBuilder("<ul");
		if(StringUtils.isNotBlank(ulCssClass)){
			html.append(" class=")
				.append(ulCssClass)
				.append("");
		}
		html.append(">");
		for(Webpage webpage : webpages){
			if(webpage.isHomepage()){
				continue;
			}
			setWebpage(webpage);
			html.append("<li>")
				.append(buildTag())
				.append("</li>");
		}
		
		return html.append("</ul>");
	}
	
	
	public Collection<Webpage> getWebpages() {
		return webpages;
	}

	public void setWebpages(Collection<Webpage> webpages) {
		this.webpages = webpages;
	}

	public String getaCssClass() {
		return getaCssClass();
	}

	public void setaCssClass(String aCssClass) {
		setCssClass(aCssClass);
	}

	public String getUlCssClass() {
		return ulCssClass;
	}

	public void setUlCssClass(String ulCssClass) {
		this.ulCssClass = ulCssClass;
	}
	
	
	
}
