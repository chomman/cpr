package cz.nlfnorm.quasar.web.tags;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cz.nlfnorm.quasar.dto.GridTagItem;

@SuppressWarnings("serial")
public class GridTag extends AbstractGridTag{

	private List<GridTagItem> list;
	
	public GridTag(){
		super();
	}
	
	protected void buildItems(final StringBuilder html){
		html.append("<tbody>");
		if(CollectionUtils.isNotEmpty(list)){
			final int size = list.get(0).getCodeSize();
			for(int j = 0; j < size; j++){
				appendCodeColumn(html, list.get(0).getCodeOnPosition(j));
				for(int a = 0; a < list.size(); a++){
					appendCodeValueColumn(html, list.get(a).isCodeOnPositionGranted(j));
				}
				html.append("</tr>");
			}
		}
		html.append("</tbody>");
	}
	
	
	protected void buildHead(final StringBuilder html){
		if(CollectionUtils.isNotEmpty(list)){
			html.append("<thead><tr><th>&nbsp</th>");
			for(final GridTagItem a : list){
				appendAuditorTh(html, a.getAuditor());
			}
			html.append("</tr></thead>");
		}
	}


	public List<GridTagItem> getList() {
		return list;
	}

	public void setList(List<GridTagItem> list) {
		this.list = list;
	}


	
}
