package cz.nlfnorm.enums;

import java.util.Arrays;
import java.util.List;

public enum PortalCountry {
	
	CR(1, "portalCountry.cr"),
	SK(2, "portalCountry.sr");
	
	private int id;
	private String code;
	
	private PortalCountry(int id, String code){
		this.id = id;
		this.code = code;
	}
	
	public static List<PortalCountry> getAll() {
        return Arrays.asList(values());
    }
	
	public static PortalCountry getById(final int id){
		for(PortalCountry country : getAll()){
			if(country.getId() == id){
				return country;
			}
		}
		return null;
	}
	
	
	public int getId() {
		return id;
	}
	
	
	public String getCode() {
		return code;
	}
	
	
}	
