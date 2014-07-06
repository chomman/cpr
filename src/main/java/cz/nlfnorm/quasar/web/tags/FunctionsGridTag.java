package cz.nlfnorm.quasar.web.tags;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.quasar.dto.EvaluatedAuditorFunctions;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.web.controllers.AuditorController;
import cz.nlfnorm.resolvers.LocaleResolver;
import cz.nlfnorm.utils.RequestUtils;

@SuppressWarnings("serial")
public class FunctionsGridTag extends AbstractGridTag {
	
	private List<EvaluatedAuditorFunctions> list;
	
	public FunctionsGridTag(){
		super();
	}
	
	@Override
	protected void buildItems(final StringBuilder html) {
		html.append("<tbody>");
		if(CollectionUtils.isNotEmpty(list)){
			for(final EvaluatedAuditorFunctions function : list){
				html.append("<tr>");
				appendTdCollumn(html, function.getAuditor().getItcId());
				appendTdCollumn(html, function.getAuditor());
				appendTdCollumn(html, function.getAuditor().getPartner() == null ? "-" :  function.getAuditor().getPartner().getName());
				appendTdCollumn(html, function.getQsAuditor().isFunctionGranted());
				appendTdCollumn(html, function.getProductAssessorA().isFunctionGranted());
				appendTdCollumn(html, function.getProductAssessorR().isFunctionGranted());
				appendTdCollumn(html, function.getProductSpecialist().isFunctionGranted());
				html.append("</tr>");
			}
		}
		html.append("</tbody>");
	}

	@Override
	protected void buildHead(final StringBuilder html) {
		if(CollectionUtils.isNotEmpty(list)){
			html.append("<thead><tr>");
			appendThCollumn(html, "auditor.itcId");
			appendThCollumn(html, "auditor.name");
			appendThCollumn(html, "auditor.partner");
			appendThCollumn(html, "auditor.qsAuditor");
			appendThCollumn(html, "auditor.productAssessorA");
			appendThCollumn(html, "auditor.productAssessorR");
			appendThCollumn(html, "auditor.productSpecialist");
			html.append("</tr></thead>");
		}
	}
	

	public List<EvaluatedAuditorFunctions> getList() {
		return list;
	}

	public void setList(List<EvaluatedAuditorFunctions> list) {
		this.list = list;
	}
	
	private void appendTdCollumn(final StringBuilder html, final Auditor auditor){
		html.append("<td class=\"qs-name\">")
			.append(auditor.isInTraining() ? "<span class=\"gs-inTraining\"></span>":"")
			.append("<a target=\"blank\" class=\"")
			.append(auditor.isIntenalAuditor() ? "qs-internal" : "qs-external")
			.append("\" href=\"")
			.append(getRequestContext().getContextPath()).append("/")
			.append(RequestUtils.buildUrl(AuditorController.AUDITOR_DETAIL_URL, LocaleResolver.CODE_CZ))
			.append(auditor.getId())
			.append("\">")
			.append( auditor.getNameWithDegree() )
			.append("</a></td>");
	}
	
	private void appendTdCollumn(final StringBuilder html, final String value){
		html.append("<td>")
			.append( value )
			.append("</td>");
	}
	
	private void appendTdCollumn(final StringBuilder html, final Integer value){
		html.append("<td>")
			.append( value )
			.append("</td>");
	}
	
	private void appendTdCollumn(final StringBuilder html, final boolean isGranted){
		html.append("<td class=\"qs-granted-").append(isGranted).append("\">")
			.append( isGranted ? "YES" : "")
			.append("</td>");
	}
	
	private void appendThCollumn(final StringBuilder html, final String code){
		html.append("<th class=\"").append(code.toLowerCase().replace("auditor.", "")).append("\" >")
			.append( messageSource.getMessage(code, null, ContextHolder.getLocale()))
			.append("</th>");
	}
	

}
