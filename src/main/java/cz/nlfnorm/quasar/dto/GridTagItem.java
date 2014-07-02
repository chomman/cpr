package cz.nlfnorm.quasar.dto;

import cz.nlfnorm.quasar.entities.Auditor;

public interface GridTagItem {
	
	String getCodeOnPosition(int position);
	
	Auditor getAuditor();
	
	int getCodeSize();
	
	boolean isCodeOnPositionGranted(int position);
	
}
