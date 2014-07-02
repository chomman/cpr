package cz.nlfnorm.quasar.web.tags;

import java.util.List;

import cz.nlfnorm.quasar.dto.EvaluatedAuditorNandoFunctionDto;

@SuppressWarnings("serial")
public class NandoFunctionGridTag extends AbstractGridTag {

	private List<EvaluatedAuditorNandoFunctionDto> list;
	
	public NandoFunctionGridTag(){
		super();
	}
	
	@Override
	protected void buildItems(final StringBuilder html) {
		html.append("<tbody>");
		final int size = list.get(0).getCodes().size();
		
		for(int j = 0; j < size; j++){
			appendCodeColumn(html, list.get(0).getCodes().get(j).getAuditorNandoCode().getNandoCode().getCode());
			for(int a = 0; a < list.size(); a++){
				appendCodeValueColumn(html, list.get(a).getCodes().get(j).isGrated());
			}
			html.append("</tr>");
		}
		html.append("</tbody>");
	}

	@Override
	protected void buildHead(final StringBuilder html) {
		html.append("<thead><tr><th>&nbsp</th>");
		for(final EvaluatedAuditorNandoFunctionDto a : list){
			appendAuditorTh(html, a.getAuditor());
		}
		html.append("</tr></thead>");
	}

	public List<EvaluatedAuditorNandoFunctionDto> getList() {
		return list;
	}

	public void setList(List<EvaluatedAuditorNandoFunctionDto> list) {
		this.list = list;
	}

	
	
}
