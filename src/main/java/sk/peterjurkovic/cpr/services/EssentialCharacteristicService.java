package sk.peterjurkovic.cpr.services;

import sk.peterjurkovic.cpr.entities.EssentialCharacteristic;

/**
 * Rozhranie pre pracu so zakladnym charakteristikama 
 * 
 * @author Peter Jurkovic
 *
 */
public interface EssentialCharacteristicService {
	
	/**
	 * Vrati zakladnu charakteristiku na zaklade daneho identifikatora
	 * 
	 * @param identifikator 
	 * @return zakladna charakteristika
	 */
	EssentialCharacteristic getEssentialCharacteristicById(Long id);
}
