package cz.nlfnorm.quasar.enums;

import java.util.Arrays;
import java.util.List;

public enum AuditLogItemType {
	
	// ISO 9001 - MDD / AIMD / IVD
	ISO9001("auditLogItemType.iso9001"),
	// ISO 13485 - the voluntary certification
	ISO13485("auditLogItemType.iso13485");
	
	
	private String code;
	
	private AuditLogItemType(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static List<AuditLogItemType> getAll() {
        return Arrays.asList(values());
    }
}
