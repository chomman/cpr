package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum PortalProductInterval {
	
	YEAR(1, "portalProductInterval.year"),
	MONTH(2, "portalProductInterval.month");
	
	private int id;
	private String code;
	
	
	private PortalProductInterval(int id, String code){
		this.id = id;
		this.code = code;
	}
	
	public static List<PortalProductInterval> getAll(){
		 return Arrays.asList(values());
	}
	
	public static PortalProductInterval getBy(Long id) {
      for (PortalProductInterval i : getAll()) {
          if (i.getId() == id) {
              return i;
          }
      }
      return null;
	}
	
	public String getCode() {
		return code;
	}

	public int getId() {
		return id;
	}

	
	
	
}
