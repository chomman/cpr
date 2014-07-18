package cz.nlfnorm.quasar.web.forms;

import cz.nlfnorm.quasar.entities.Company;

public interface LogItemForm {
	
	String getCompanyName();
	
	void setCompany(Company company);
	
	Company getCompany();
	
	String getStringNandoCodes();
	
	String getStringEacCodes();
}
