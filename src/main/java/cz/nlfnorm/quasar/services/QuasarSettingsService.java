package cz.nlfnorm.quasar.services;

import cz.nlfnorm.quasar.entities.QuasarSettings;

public interface QuasarSettingsService {
	
	void update(QuasarSettings quasarSettings);
	
	QuasarSettings getSettings();
	
}
