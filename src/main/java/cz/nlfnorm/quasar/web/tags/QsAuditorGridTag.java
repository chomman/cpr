package cz.nlfnorm.quasar.web.tags;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.quasar.dto.EvaludatedQsAuditor;
import cz.nlfnorm.quasar.web.controllers.AuditorController;
import cz.nlfnorm.resolvers.LocaleResolver;
import cz.nlfnorm.utils.RequestUtils;

@SuppressWarnings("serial")
public class QsAuditorGridTag extends RequestContextAwareTag{

	private List<EvaludatedQsAuditor> list;
	
	@Autowired
	private MessageSource messageSource;
	
	public QsAuditorGridTag(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
			
	@Override
	protected int doStartTagInternal() throws Exception {
		pageContext.getOut().print(buildTable().toString());
		return SKIP_PAGE;
	}

	private String buildTable(){
		StringBuilder html = new StringBuilder("<table>");
		buildHead(html);
		buildItems(html);
		return html.append("</table>").toString();
	}
	
	private void buildItems(final StringBuilder html){
		html.append("<tbody>");
		final int size = list.get(0).getCodes().size();
		final String grantedText = messageSource.getMessage("granted", null,ContextHolder.getLocale());
		for(int j = 0; j < size; j++){
			html.append("<tr><td class=\"qs-left-th\"><span class=\"code-label \">")
				.append(list.get(0).getCodes().get(j).getAuditorEacCode().getEacCode().getCode())
				.append("</span></td>");
			for(int a = 0; a < list.size(); a++){
				html.append("<td class=\"qs-fx-width qs-granted-")
				.append(list.get(a).getCodes().get(j).isGrated())
				.append(" \">")
				.append(list.get(a).getCodes().get(j).isGrated() ? grantedText : "")
				.append("</td>");
			}
			html.append("</tr>");
		}
		html.append("</tbody>");
	}
	
	private void buildHead(final StringBuilder html){
		html.append("<thead><tr><th>&nbsp</th>");
		for(EvaludatedQsAuditor a : list){
			html.append("<th class=\"qs-th qs-fx-width\" ><a class=\"rotate\" target=\"_blank\" href=\"");
			appendUrl(html);
			html.append(a.getAuditor().getId())
				.append("\" >")
				.append(a.getAuditor().getName())
				.append("</a></th>");
		}
		html.append("</tr></thead>");
	}

	public void appendUrl(final StringBuilder url){
		url.append(getRequestContext().getContextPath()).append("/");
		url.append(RequestUtils.buildUrl(AuditorController.AUDITOR_DETAIL_URL, LocaleResolver.CODE_CZ));
	}

	public List<EvaludatedQsAuditor> getList() {
		return list;
	}

	public void setList(List<EvaludatedQsAuditor> list) {
		this.list = list;
	}


	
}
