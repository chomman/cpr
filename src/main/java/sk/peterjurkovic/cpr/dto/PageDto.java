package sk.peterjurkovic.cpr.dto;

import java.util.List;

public class PageDto {

	private Long count;
	
	private List<Object> items;

	public int getCount() {
		return count.intValue();
	}
	
	public void setCount(Long count) {
		if(count == null){
			this.count = 0l;
		}else{
			this.count = count;
		}
	}

	public List<Object> getItems() {
		return items;
	}

	public void setItems(List<Object> items) {
		this.items = items;
	}
	
	
}
