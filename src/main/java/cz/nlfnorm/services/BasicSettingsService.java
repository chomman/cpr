package cz.nlfnorm.services;

import cz.nlfnorm.entities.BasicSettings;

/**
 * Rozhranie pre pracu so zakladnym nastavenim systemu
 * 
 * @author peter.jurkovic
 *
 */
public interface BasicSettingsService {
	
	
	/**
	 * Vrati zakladne nastavenie systemu
	 * 
	 * @return
	 */
	BasicSettings getBasicSettings();
	
	/**
	 * Aktualizuje zakladne nastavenia systemu
	 * 
	 * @param basicSettings
	 */
	void updateBasicSettings(BasicSettings basicSettings);
	
	String getCsnOnlineUrl();
	
}
