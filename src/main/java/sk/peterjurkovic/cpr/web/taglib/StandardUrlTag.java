package sk.peterjurkovic.cpr.web.taglib;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.Standard;

public class StandardUrlTag extends UrlTag {
	
	private static final long serialVersionUID = -1890295392747629115L;
	private boolean editable = false;
	private Standard standard;
	
	
	
	@Override
	protected int doStartTagInternal() throws Exception {
		setupValues(standard);
		StringBuilder url = new StringBuilder();
		if(!isEditable() || isLinkOnly()){
			appendBaseUrl(url);
			pageContext.getOut().print(url.toString());
			return SKIP_PAGE;
		}
		url.append("<span class=\"a-link\">");
		appendBaseUrl(url);
		url.append("<a href=");
		url.append(getRequestContext().getContextPath());
		url.append("/");
		url.append(Constants.ADMIN_PREFIX);
		url.append("/cpr/standard/edit/");
		url.append(standard.getId());
		url.append(" class=\"a-standard\" target=\"_blank\" title=\"Editovat\" ></a></span>");
		pageContext.getOut().print(url.toString());
		return SKIP_PAGE;
	}
		
	private void appendBaseUrl(StringBuilder url){
		url.append(buildTag());
		url.append(standard.getStandardId());
		url.append("</a>");
	}
	
	private void setupValues(Standard standard){
		setHref(String.format("/ehn/%s", standard.getId()));
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public Standard getStandard() {
		return standard;
	}

	public void setStandard(Standard standard) {
		this.standard = standard;
	}
	
	@Override
	public void appendCssStyles(StringBuilder url){
		url.append(" class=\"")
		.append(getCssClass())
		.append(standard.isNew() ? " s-new " : " ")
		.append(standard.getStatusClass())
		.append("\"");
			
		
	}
}
