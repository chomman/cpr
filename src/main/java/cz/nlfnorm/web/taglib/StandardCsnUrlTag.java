package cz.nlfnorm.web.taglib;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.AbstractStandardCsn;
import cz.nlfnorm.entities.StandardCsnChange;


public class StandardCsnUrlTag extends RequestContextAwareTag {
	
	private static final long serialVersionUID = -4428223986709009501L;	
	private AbstractStandardCsn object;
	private boolean editable = false;
	private boolean linkOnly = false;
	private String title;
	private String cssClass = "";
	private String id;
	private boolean isChange = false;
	@Value("#{config.csnonlineurl}")
	private String csnOnlineUrl; 
	
	public StandardCsnUrlTag(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(isLinkOnly()){
			pageContext.getOut().print(getCsnOnlineLink());
			return SKIP_PAGE;
		}
		isChange = (object instanceof StandardCsnChange);
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
	
	private void appendAdminUrl(StringBuilder url){
		url.append(getRequestContext().getContextPath());
		url.append("/");
		url.append(Constants.ADMIN_PREFIX);	
		if(isChange){
			StandardCsnChange change = (StandardCsnChange)object;
			url.append("/cpr/standard-csn/");
			url.append(change.getStandardCsn().getId());
			url.append("/change/");
		}else{
			url.append("/cpr/standard-csn/edit/");
		}
		url.append(object.getId());
	}
	
	private void appendTag(StringBuilder url){
		if(StringUtils.isBlank(object.getCsnOnlineId())){
			url.append(object.getCsnName());
			return;
		}
		url.append("<a target=\"_blank\" href=\"")
		   .append(getCsnOnlineLink())
		   .append("\" ");
		appendTitle(url);
		appendCssStyles(url);
		appendId(url);
		url.append(">");
		url.append(object.getCsnName());
		url.append("</a>");
	}
	
	public void appendTitle(StringBuilder url){
		if(StringUtils.isNotBlank(title)){
			url.append(" title=\"").append(title).append("\"");
			
		}
	}
	
	public void appendCssStyles(StringBuilder url){
			url.append(" class=\"")
			   .append(cssClass)
			   .append( isChange ? " csn-pdf-change " :  " csn-pdf ")
			   .append(object.isNew() ? " csn-new " : " ")
			   .append(object.getStatusClass())
			   .append("\"");
	}
	
	public void appendId(StringBuilder url){
		if(StringUtils.isNotBlank(id)){
			url.append(" id=\"").append(id	).append("\"");
			
		}
	}
	
	private String getCsnOnlineLink(){
		return csnOnlineUrl.replace("{0}", object.getCsnOnlineId());
	}
	
	
	public AbstractStandardCsn getObject() {
		return object;
	}

	public void setObject(AbstractStandardCsn object) {
		this.object = object;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isLinkOnly() {
		return linkOnly;
	}

	public void setLinkOnly(boolean linkOnly) {
		this.linkOnly = linkOnly;
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
	
}
