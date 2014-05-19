package cz.nlfnorm.dto;

import java.util.ArrayList;
import java.util.List;

public class PageDto {

	private Long count = 0l;
	
	private List<Object> items = new ArrayList<Object>();

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
