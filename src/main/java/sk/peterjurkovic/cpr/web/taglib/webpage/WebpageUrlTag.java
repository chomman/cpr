package sk.peterjurkovic.cpr.web.taglib.webpage;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

import org.apache.commons.lang.StringUtils;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.context.ContextHolder;
import sk.peterjurkovic.cpr.enums.WebpageType;
import sk.peterjurkovic.cpr.utils.WebpageUtils;


@SuppressWarnings("serial")
public class WebpageUrlTag  extends WebpageLinkTag implements BodyTag{
	
	private final String CURRENT_WEBPAGE_CALSS = "pj-active";
	private final String TARGET_BLANK = "_blank";
	
	private String id;
	private String cssClass;
	private String title;
	private String target;
	private String extraAttr;
	private String params;
	private boolean isPreview = false;
	private Long activePageId;
	private boolean withName = true;
	private BodyContent bodyContent;
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(withName){
			pageContext.getOut().print( buildTag().toString() );
	        return SKIP_PAGE;
		}
		pageContext.getOut().print(buildTag().toString());
        return EVAL_BODY_INCLUDE;
	}
	
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
			if(!withName){
				pageContext.getOut().print("</a>");
			}
			return EVAL_PAGE;
		} catch (IOException e) {
			throw new JspException(e.getMessage(), e);
		}
	}
	
	protected StringBuilder buildTag(){
		StringBuilder url = new StringBuilder();
		url.append("<a href=\"");
		appendUrl(url);
		appendParams(url);
		url.append("\" ");
		appendTitle(url);
		appendCssStyles(url);
		appendId(url);
		appendExtraAttr(url);
		appendTarget(url);
		url.append(">");
		if(withName){
			url.append(WebpageUtils.getLocalizedValue("name", getWebpage()))
				.append("</a>");
		}
		return url;
	}
	
	private void appendParams(StringBuilder url){
		String params = "";
		if(isPreview){
			params += "&amp;" + Constants.PREVIEW_PARAM + "=1";
		}
		if(this.params != null){
			params += this.params;
		}
		if(StringUtils.isNotBlank(params)){
			url.append("?").append(params);
		}
	}
	
	public void appendUrl(StringBuilder url){
		url.append(getWebpageUrl());		
	}
	
	public void appendTitle(StringBuilder url){
		String titleValue = getValueForTitle();
		if(titleValue != null){
			url.append(" title=\"").append(titleValue).append("\" ");
		}
	}
	
	public void appendCssStyles(StringBuilder url){
		if(activePageId != null && activePageId == getWebpage().getId()){
			cssClass += " " + CURRENT_WEBPAGE_CALSS;
		}
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
	
	private void appendTarget(StringBuilder url){
		final String target = getValueForTarget();
		if(target != null){
			url.append(" target=\"").append(target).append("\" ");
		}
	}
	
	private String getValueForTitle(){
		if(StringUtils.isNotBlank(title)){
			return title;
		}
		String webpageTitle = WebpageUtils.getValueInLocale("title", getWebpage(), ContextHolder.getLocale());
		if(StringUtils.isNotBlank(webpageTitle)){
			return webpageTitle;
		}
		return null;
	}
	
	private String getValueForTarget(){
		if(StringUtils.isNotBlank(target)){
			return target;
		}
		if(getWebpage().getWebpageType().equals(WebpageType.REDIRECT) && 
		 StringUtils.isNotBlank(getWebpage().getRedirectUrl())){
			return TARGET_BLANK;
		}
		return null;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getExtraAttr() {
		return extraAttr;
	}
	public void setExtraAttr(String extraAttr) {
		this.extraAttr = extraAttr;
	}

	public Long getActivePageId() {
		return activePageId;
	}

	public void setActivePageId(Long activePageId) {
		this.activePageId = activePageId;
	}

	public boolean isWithName() {
		return withName;
	}

	public void setWithName(boolean withName) {
		this.withName = withName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public boolean getIsPreview() {
		return isPreview;
	}
	
	public boolean isPreview() {
		return isPreview;
	}

	public void setIsPreview(boolean isPreview) {
		this.isPreview = isPreview;
	}
	
	
	
}
