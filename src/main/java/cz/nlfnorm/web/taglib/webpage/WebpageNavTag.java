package cz.nlfnorm.web.taglib.webpage;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.utils.WebpageUtils;

@SuppressWarnings("serial")
public class WebpageNavTag extends WebpageUrlTag {
	
	private Collection<Webpage> webpages;
	private String ulCssClass;
	private String parentLiCssClass;
	private boolean isAuthenticated = false;
	private boolean withSubnav = false;
	
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
			html.append(" class=\"")
				.append(ulCssClass)
				.append(" \"");
		}
		html.append(">");
		for(Webpage webpage : webpages){
			if(webpage.isHomepage()){
				continue;
			}
			
			setWebpage(webpage);
			html.append("<li");
			appendLiCssClass(html);
			html.append(">");
			if(!isAuthenticated && WebpageUtils.isOnlyForRegistraged(webpage)){
				appendLockedWebpage(html);
			}else{
				html.append(buildTag());
			}
			if(withSubnav){
				appendSubnavFor(html, webpage);
			}
			html.append("</li>");
		}
		
		return html.append("</ul>");
	}
	
	protected void appendLockedWebpage(StringBuilder html) {
	
		if(getWebpage() == null){
			logger.warn("Webpage can not be null");
			return;
		}
		html.append("<span class=\"pj-locked\" >")
			.append(WebpageUtils.getLocalizedValue("name", getWebpage()))
			.append("</span>");
	}
	
	private void appendLiCssClass(StringBuilder html){
		String cssClass = "";
		if(parentLiCssClass != null){
			cssClass += parentLiCssClass;		
		}
		if(withSubnav && getWebpage().getPublishedSections().size() > 0){
			cssClass += " has-children";
		}
		if(StringUtils.isNotBlank(cssClass)){
			html.append(" class=\"")
				.append(cssClass).append("\" ");
		}
	}
	
	protected void appendSubnavFor(StringBuilder html, Webpage webpage) {
		List<Webpage> childrenList = webpage.getPublishedSections();
		if(childrenList.size() > 0){
			html.append("<ul>");
			for(Webpage child : childrenList){
				setWebpage(child);
				html.append("<li>");
				if(!isAuthenticated && WebpageUtils.isOnlyForRegistraged(child)){
					appendLockedWebpage(html);
				}else{
					html.append(buildTag());
				}
				html.append("</li>");
					
			}
			html.append("</ul>");
		}
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

	public boolean isWithSubnav() {
		return withSubnav;
	}

	public void setWithSubnav(boolean withSubnav) {
		this.withSubnav = withSubnav;
	}

	public String getParentLiCssClass() {
		return parentLiCssClass;
	}

	public void setParentLiCssClass(String parentLiCssClass) {
		this.parentLiCssClass = parentLiCssClass;
	}

	public boolean getIsAuthenticated() {
		return isAuthenticated;
	}

	public void setIsAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	
	
	
}
