package sk.peterjurkovic.cpr.web.taglib;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.StandardNotifiedBody;
import sk.peterjurkovic.cpr.utils.CommonUtils;

public class NotifiedBodyUrlTag extends RequestContextAwareTag {

	private static final long serialVersionUID = 1917841901997533270L;
	

	@Value("#{config.nandourl}")
	private String nandoUrl;
	
	private StandardNotifiedBody object;
	private String cssClass = "";
	private String id;
	private boolean buildNandoUrl = false;
	private boolean editable = false;
	private boolean codesOnly = true;
	
	public NotifiedBodyUrlTag(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(!(object instanceof StandardNotifiedBody)){
			throw new JspException("Object is NULL or is not instance of StandardNotifiedBody");
		}
		pageContext.getOut().print(buildUrl().toString());
		return SKIP_PAGE;
	}
	
	private StringBuilder buildUrl(){
		StringBuilder url = new StringBuilder();
		if(!isEditable()){
			appendTag(url);
			return url;	
		}
		url.append("<span class=\"a-link\">");
		appendTag(url);
		url.append("<a href=");
		appendAdminUrl(url);
		url.append(" class=\"a-standard\" target=\"_blank\" title=\"Editovat\" ></a></span>");
		return url;
	}
	
	private void appendTag(StringBuilder url){
		if(buildNandoUrl && StringUtils.isBlank(object.getNotifiedBody().getNandoCode())){
			if(CommonUtils.isObjectNew(object.getAssignmentDate())){
				url.append("<span class=\"s-new\">");
				appendName(url);
				url.append("</span>");
			}else{
				appendName(url);
			}
			return;
		}
		url.append("<a href=");
		appendLink(url);
		appendTitle(url);
		if(buildNandoUrl){
			appendTargetBlank(url);
		}
		appendCssStyles(url);
		appendId(url);
		url.append(">");
		appendName(url);
		url.append("</a>");
	}
	
	
	
	
	private void appendAdminUrl(StringBuilder url){
		url.append(getRequestContext().getContextPath())
			.append("/")
			.append(Constants.ADMIN_PREFIX)	
			.append("/cpr/notifiedbodies/edit/")
			.append(object.getNotifiedBody().getId());
	}
	
	private void appendName(StringBuilder url){
		url.append(object.getNotifiedBody().getNoCode());
		if(StringUtils.isNotBlank(object.getNotifiedBody().getAoCode())){
			url.append(" ").append(object.getNotifiedBody().getAoCode());
		}
		if(!codesOnly){
			url.append(" - ").append(object.getNotifiedBody().getName());
		}
	}
	
	private void appendLink(StringBuilder url){
		if(buildNandoUrl){
			url.append(nandoUrl)
			   .append(object.getNotifiedBody().getNandoCode());
		}else{
			url.append(getRequestContext().getContextPath())
				.append("/subjekt/")
				.append(object.getNotifiedBody().getId());
		}
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	public void appendTitle(StringBuilder url){
		url.append(" title=\"").append(object.getNotifiedBody().getName()).append("\"");
	}
	
	private void appendTargetBlank(StringBuilder url){
		url.append(" target=\"_blank\" ");
	}
	
	public void appendCssStyles(StringBuilder url){
		url.append(" class=\"")
			.append(cssClass == null ? cssClass : "")
			.append(CommonUtils.isObjectNew(object.getAssignmentDate()) ? " s-new " : "")
			.append("\"");
	}
	
	public void appendId(StringBuilder url){
		if(StringUtils.isNotBlank(id)){
			url.append(" id=\"").append(id	).append("\"");		
		}
	}

	public String getNandoUrl() {
		return nandoUrl;
	}

	public void setNandoUrl(String nandoUrl) {
		this.nandoUrl = nandoUrl;
	}

	public StandardNotifiedBody getObject() {
		return object;
	}

	public void setObject(StandardNotifiedBody object) {
		this.object = object;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isBuildNandoUrl() {
		return buildNandoUrl;
	}

	public void setBuildNandoUrl(boolean buildNandoUrl) {
		this.buildNandoUrl = buildNandoUrl;
	}

	public boolean isCodesOnly() {
		return codesOnly;
	}

	public void setCodesOnly(boolean codesOnly) {
		this.codesOnly = codesOnly;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	
}
