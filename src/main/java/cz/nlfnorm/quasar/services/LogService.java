package cz.nlfnorm.quasar.services;

import cz.nlfnorm.quasar.entities.AbstractLog;
import cz.nlfnorm.quasar.enums.LogStatus;

public interface LogService {
	
	void setLogStatus(LogStatus newStatus, AbstractLog log, String withComment);
	
	void setPendingStatus(AbstractLog log);
	
	void setRfusedStatus(AbstractLog log);
	
	void setApprovedStatus(AbstractLog log);
}
