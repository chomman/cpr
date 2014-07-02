package cz.nlfnorm.quasar.web.tags;

import java.util.List;

import cz.nlfnorm.quasar.dto.EvaludatedQsAuditor;

@SuppressWarnings("serial")
public class QsAuditorGridTag extends AbstractGridTag{

	private List<EvaludatedQsAuditor> list;
	
	public QsAuditorGridTag(){
		super();
	}
	
	protected void buildItems(final StringBuilder html){
		html.append("<tbody>");
		final int size = list.get(0).getCodes().size();
		
		for(int j = 0; j < size; j++){
			appendCodeColumn(html, list.get(0).getCodes().get(j).getAuditorEacCode().getEacCode().getCode());
			for(int a = 0; a < list.size(); a++){
				appendCodeValueColumn(html, list.get(a).getCodes().get(j).isGrated());
			}
			html.append("</tr>");
		}
		html.append("</tbody>");
	}
	
	
	protected void buildHead(final StringBuilder html){
		html.append("<thead><tr><th>&nbsp</th>");
		for(final EvaludatedQsAuditor a : list){
			appendAuditorTh(html, a.getAuditor());
		}
		html.append("</tr></thead>");
	}


	public List<EvaludatedQsAuditor> getList() {
		return list;
	}

	public void setList(List<EvaludatedQsAuditor> list) {
		this.list = list;
	}


	
}
