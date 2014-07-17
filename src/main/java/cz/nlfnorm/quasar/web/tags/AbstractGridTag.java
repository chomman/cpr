package cz.nlfnorm.quasar.web.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.web.controllers.AuditorController;
import cz.nlfnorm.resolvers.LocaleResolver;
import cz.nlfnorm.utils.RequestUtils;

@SuppressWarnings("serial")
public abstract class AbstractGridTag extends RequestContextAwareTag {
	
	@Autowired
	protected MessageSource messageSource;
	protected String grantedText;
	
	public AbstractGridTag(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected int doStartTagInternal() throws Exception {
		grantedText = messageSource.getMessage("granted", null,ContextHolder.getLocale());
		pageContext.getOut().print(buildTable().toString());
		return SKIP_PAGE;
	}
	
	protected String buildTable(){
		StringBuilder html = new StringBuilder("<table>");
		buildHead(html);
		buildItems(html);
		return html.append("</table>").toString();
	}
	

	public void appendUrl(final StringBuilder url){
		url.append(getRequestContext().getContextPath()).append("/");
		url.append(RequestUtils.buildUrl(AuditorController.AUDITOR_DETAIL_URL, LocaleResolver.CODE_CZ));
	}
	
	protected void appendCodeColumn(final StringBuilder html, final String code) {
		html.append("<tr><td class=\"qs-left-th\"><span class=\"code-label \">")
		.append(code)
		.append("</span></td>");
	}
	
	protected void appendCodeValueColumn(final StringBuilder html, final boolean isGranted) {
		html.append("<td class=\""+getColumnClass()+" qs-granted-")
		.append(isGranted)
		.append(" \">")
		.append(isGranted ? grantedText : "")
		.append("</td>");
	}
	
	protected String getColumnClass() {
		return "qs-fx-width";
	}
	
	protected void appendAuditorTh(final StringBuilder html, final Auditor auditor) {
		html.append("<th class=\"qs-th qs-fx-width\" ><a class=\"rotate\" target=\"_blank\" href=\"");
		appendUrl(html);
		html.append(auditor.getId())
			.append("\" ><span class=\"qs-n\">")
			.append(auditor.getName())
			.append("</span><span class=\"qs-id\">Personal ID: ")
			.append(auditor.getItcId())
			.append("</span><span class=\"qs-e\">Internal auditor: ")
			.append(auditor.isIntenalAuditor() ? "Yes" : "No")
			.append("</span>")
			.append("</a></th>");
	}
	
	protected abstract void buildItems(StringBuilder html);
	
	protected abstract void buildHead(StringBuilder html);
	
}
