package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum StandardStatus {
	
	NORMAL(1,  "standardStatus.normal", "NORMAL", ""),
	CANCELED(2,"standardStatus.canceled" , "CANCELED", "status-canceled"),
	NON_HARMONIZED(3,"standardStatus.nonHarmonized", "NON_HARMONIZED", "status-non-harmonized"),
	CANCELED_HARMONIZED(3,"standardStatus.canceledHarmonized", "CANCELED_HARMONIZED", "status-canceled-harmonized");
	
	private int id;
	private String name;
	private String code;
	private String cssClass;
	
	
	private StandardStatus(int id, String name, String code, String cssClass){
		this.id = id;
		this.name = name;
		this.code = code;
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

	
	public String getCode() {
		return code;
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
