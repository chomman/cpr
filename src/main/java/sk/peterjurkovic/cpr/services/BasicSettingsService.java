package sk.peterjurkovic.cpr.services;

import sk.peterjurkovic.cpr.entities.BasicSettings;

public interface BasicSettingsService {
	
	
	BasicSettings getBasicSettings();
	

	void updateBasicSettings(BasicSettings basicSettings);
	
}
