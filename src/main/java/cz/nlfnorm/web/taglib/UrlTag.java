package cz.nlfnorm.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import cz.nlfnorm.resolvers.LocaleResolver;
import cz.nlfnorm.utils.RequestUtils;


public class UrlTag extends RequestContextAwareTag implements BodyTag{
	
	private String href;
	private String title;
	private String cssClass = "";
	private String id;
	private String extraAttr;
	private boolean linkOnly = false;
	private String fixedLocale;
	private BodyContent bodyContent;	
	private static final long serialVersionUID = -4580484795594351701L;
	protected Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void doInitBody() throws JspException {
		System.out.println(bodyContent);
	}

	@Override
	public void setBodyContent(BodyContent bc) {
		this.bodyContent = bc;
	}
	
	@Override
	public int doEndTag() throws JspException {
		try {
			if(!linkOnly){
				pageContext.getOut().print("</a>");
			}
			setCssClass(null);
			return super.doEndTag();
		} catch (IOException e) {
			throw new JspException(e.getMessage(), e);
		}
	}
	
	

	@Override
	protected int doStartTagInternal() throws Exception {
		if(linkOnly){
			StringBuilder url = new StringBuilder();
			appendUrl(url);
			pageContext.getOut().print(url.toString());
	        return SKIP_PAGE;
		}
		pageContext.getOut().print(buildTag().toString());
        return EVAL_BODY_INCLUDE;
	}
	
	protected StringBuilder buildTag(){
		StringBuilder url = new StringBuilder();
		url.append("<a href=\"");
		appendUrl(url);
		url.append("\" ");
		appendTitle(url);
		appendCssStyles(url);
		appendId(url);
		appendExtraAttr(url);
		url.append(">");
		return url;
	}
	

	public void appendTitle(StringBuilder url){
		if(StringUtils.isNotBlank(title)){
			url.append(" title=\"").append(title).append("\" ");
			
		}
	}
	
	public void appendCssStyles(StringBuilder url){
		if(StringUtils.isNotBlank(cssClass)){
			url.append(" class=\"").append(cssClass).append("\" ");
			
		}
	}
	
	public void appendId(StringBuilder url){
		if(StringUtils.isNotBlank(id)){
			url.append(" id=\"").append(id	).append("\" ");
			
		}
	}
	
	public void appendExtraAttr(StringBuilder url){
		if(StringUtils.isNotBlank(extraAttr)){
			String[] data = extraAttr.split(";");
			if(data.length == 2){
				url.append(data[0])
				   .append("=\"")
				   .append(data[1])
				   .append("\" ");
			}
			
		}
	}
	
	
	
	public void appendUrl(StringBuilder url){
		url.append(getRequestContext().getContextPath()).append("/");
		if(StringUtils.isNotBlank(fixedLocale) && LocaleResolver.isAvailable(fixedLocale)){
			url.append(RequestUtils.buildUrl(getHref(), fixedLocale));
		}else{
			url.append(RequestUtils.buildUrl(getHref()));
		}
		
	}
	
	public String getHref() {
		return href;
	}

	public String getTitle() {
		return title;
	}

	public String getCssClass() {
		return cssClass;
	}

	public String getId() {
		return id;
	}

	public String getExtraAttr() {
		return extraAttr;
	}

	public void setExtraAttr(String extraAttr) {
		this.extraAttr = extraAttr;
	}

	public boolean isLinkOnly() {
		return linkOnly;
	}

	public void setLinkOnly(boolean linkOnly) {
		this.linkOnly = linkOnly;
	}

	public BodyContent getBodyContent() {
		return bodyContent;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFixedLocale() {
		return fixedLocale;
	}

	public void setFixedLocale(String fixedLocale) {
		this.fixedLocale = fixedLocale;
	}

}
