package cz.nlfnorm.quasar.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.web.controllers.AuditorController;
import cz.nlfnorm.web.taglib.UrlTag;

@SuppressWarnings("serial")
public class AuditorTag extends UrlTag {

	private Auditor auditor;
	private boolean withDegree = false;
	
	@Override
	protected StringBuilder buildTag(){
		StringBuilder url = new StringBuilder();
		if(auditor.isInTraining()){
			url.append("<span class=\"gs-inTraining\" class=\"tt\"  title=\"In training\"></span>");
		}
		url.append(super.buildTag());
		return url;
	}
		
	@Override
	public int doEndTag() throws JspException {
		try {
			if(withDegree){
				pageContext.getOut().print(auditor.getNameWithDegree());
			}else{
				pageContext.getOut().print(auditor.getName());
			}
			pageContext.getOut().print("</a>");
			setCssClass(null);
			setAuditor(null);
			return super.doEndTag();
		} catch (IOException e) {
			throw new JspException(e.getMessage(), e);
		}
	}
	
	@Override
	public String getHref() {
		return AuditorController.AUDITOR_DETAIL_URL + auditor.getId();
	}
	
	@Override
	public void appendUrl(StringBuilder url){
		url.append(getRequestContext().getContextPath()).append("/");
		url.append(getHref());
	}
	
	@Override
	public String getCssClass() {
		String css = "";
		if(StringUtils.isNotBlank(super.getCssClass())){
			css += super.getCssClass();
		}
		css +=  auditor.isIntenalAuditor() ? " qs-internal" : " qs-external"; 
		return css;
	}
	
	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}

	public boolean isWithDegree() {
		return withDegree;
	}

	public void setWithDegree(boolean withDegree) {
		this.withDegree = withDegree;
	}
	
	
	
}
