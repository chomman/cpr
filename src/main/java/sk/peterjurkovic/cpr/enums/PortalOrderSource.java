package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum PortalOrderSource{
	
	NLFNORM(1, "portalOrderSource.nlfnorm"),
	PLASTIC_PORTAL(2, "portalOrderSource.plasticPortal");
	
	private int id;
	private String code;
	
	private PortalOrderSource(int id, String code){
		this.id = id;
		this.code = code;
	}
	
	public static List<PortalOrderSource> getAll() {
        return Arrays.asList(values());
    }
	
	public static PortalOrderSource getById(int id) {
        for (PortalOrderSource i : getAll()) {
            if (i.getId() == id) {
                return i;
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
