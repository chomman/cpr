package cz.nlfnorm.quasar.enums;

import java.util.Arrays;
import java.util.List;

public enum AuditLogStatus {
	
	DRAFT("auditLog.draft"),
	PENDING("auditLog.pending"),
	REFUSED("auditLog.pending"),
	APPROVED("auditLog.approved");
	
	private String code;
	
	private AuditLogStatus(final String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static List<AuditLogStatus> getAll() {
        return Arrays.asList(values());
    }
}
