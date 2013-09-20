package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum ImportStatus {
	
	SUCCESS("SUCCESS", "import.status.success"),
	
	INCOMPLETE("INCOMPLETE", "import.status.incomplete"),
	
	FAILED("FAILED", "import.status.failed");
	
	private String id;
		
	private String key;
	
	
	private ImportStatus(String id, String key){
		this.id = id;
		this.key = key;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}
	
	public static List<ImportStatus> getAll() {
        return Arrays.asList(values());
    }
	
	public static ImportStatus getById(String id) {
        for (ImportStatus i : getAll()) {
            if (i.getId().equals(id)) {
                return i;
            }
        }
        return null;
    }
	
}
