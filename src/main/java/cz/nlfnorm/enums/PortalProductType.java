package cz.nlfnorm.enums;

import java.util.Arrays;
import java.util.List;

public enum PortalProductType {
	
	REGISTRATION(1, "portalProductType.registration"),
	PUBLICATION(2, "portalProductType.publication");
	
	private int id;
	private String code;
	
	private PortalProductType(int id, String code){
		this.id = id;
		this.code = code;
	}
	
	public static List<PortalProductType> getAll(){
		 return Arrays.asList(values());
	}
		
	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

		
}
