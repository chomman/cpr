package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum StandardStatus {
	
	NORMAL(1, "standardStatus.normal", ""),
	CANCELED(2,"standardStatus.canceled" ,"status-canceled"),
	NON_HARMONIZED(3,"standardStatus.nonHarmonized","status-non-harmonized");
	
	private int id;
	private String name;
	private String cssClass;
	
	
	private StandardStatus(int id, String name, String cssClass){
		this.id = id;
		this.name = name;
		this.cssClass =  cssClass;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}
		
	public String getCssClass() {
		return cssClass;
	}


	public static List<StandardStatus> getAll() {
        return Arrays.asList(values());
    }
	
	public static StandardStatus getById(final int id) {
        for (StandardStatus i : getAll()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
	
}
