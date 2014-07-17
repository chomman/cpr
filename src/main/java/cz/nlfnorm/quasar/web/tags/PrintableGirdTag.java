package cz.nlfnorm.quasar.web.tags;

import org.apache.commons.collections.CollectionUtils;

import cz.nlfnorm.quasar.dto.GridTagItem;
import cz.nlfnorm.quasar.entities.Auditor;

@SuppressWarnings("serial")
public class PrintableGirdTag extends GridTag {
	
	
	@Override
	protected void buildHead(StringBuilder html) {
		if(CollectionUtils.isNotEmpty(list)){
			final int size = list.get(0).getCodeSize();
			html.append("<thead><tr class=\"qs-print\"><th>&nbsp</th>");
			for(int i = 0; i < size; i++){
				appendCodeColumn(html, list.get(0).getCodeOnPosition(i));
			}
			html.append("</tr></thead>");
		}
	}
	
	@Override
	protected void buildItems(StringBuilder html) {
		html.append("<tbody>");
		if(CollectionUtils.isNotEmpty(list)){
			grantedText = "X";
			for(final GridTagItem item : list){
				html.append("<tr class=\"qs-print\">");
				appendPrintableAuditorColumn(html, item.getAuditor());
				final int size = list.get(0).getCodeSize();
				for(int i = 0; i < size; i++){
					appendCodeValueColumn(html, item.isCodeOnPositionGranted(i));
				}
				html.append("</tr>");
			}
		}
		html.append("</tbody>");
	
	}
	
	@Override
	protected void appendCodeColumn(final StringBuilder html, final String code) {
		html.append("<th class=\"qs-th-code\">")
		.append(code)
		.append("</th>");
	}
	
	@Override
	protected String getColumnClass() {
		return "qspv";
	}
	public void appendPrintableAuditorColumn(final StringBuilder html, final Auditor auditor){
		html.append("<td class=\"qs-th-auditor\" >")
			.append(auditor.getName())
			.append("</td>");
	}
	
}
