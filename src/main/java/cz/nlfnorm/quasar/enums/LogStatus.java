package cz.nlfnorm.quasar.enums;

import java.util.Arrays;
import java.util.List;

import cz.nlfnorm.quasar.hibernate.PersistentEnum;

public enum LogStatus implements PersistentEnum {
	
	
	PENDING(1,"logStatus.pending"),
	REFUSED(2,"logStatus.refused"),
	DRAFT(3,"logStatus.draft"),
	APPROVED(4,"logStatus.approved");
	
	private int id;
	private String code;
	
	private LogStatus(final int id, final String code){
		this.code = code;
		this.id = id;
	}

	public int getId() {
		return id;
	}
	public String getCode() {
		return code;
	}

	public static List<LogStatus> getAll() {
        return Arrays.asList(values());
    }
	
	public boolean isLocked(){
		return getId() == 1 || getId() ==  4;
	}
}
