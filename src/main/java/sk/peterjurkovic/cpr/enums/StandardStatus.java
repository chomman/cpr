package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum StandardStatus {
	
	NORMAL(1, "standardStatus.normal"),
	CANCELED(2,"standardStatus.replaced" ),
	NON_HARMONIZED(3,"standardStatus.nonHarmonized");
	
	private int id;
	private String name;
	
	
	private StandardStatus(int id, String name){
		this.id = id;
		this.name = name;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
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
