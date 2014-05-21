package cz.nlfnorm.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cz.nlfnorm.entities.ExceptionLog;

/**
 * Rozhranie pre pracu vzniknutych vinimiek
 * 
 * @author peto
 *
 */
public interface ExceptionLogService {
	
	/**
	 * Zaeviduje vzniknutu vinimku do databaze
	 * 
	 * @param exceptionLog
	 */
	void create(ExceptionLog exceptionLog);
	
	
	/**
	 * Extrahuje potrebne data pre identifikaciu danej vinimku a ulozi do databaze
	 * 
	 * @param exceptionLog
	 */
	void logException(HttpServletRequest request, Exception exception);
	
	

	/**
	 * Metoda vrati stranku zalogovanych vynimiek
	 * 
	 * @param int cislo stranky
	 * @param kriteria, podla ktorych sa vykona filter
	 * 
	 * @return zoznam zalogovanych vynimiek vyhovujucich danym kriteriam
	 */
	List<ExceptionLog> getExceptionLogPage(int pageNumber, Map<String, Object> criteria);
	
	
	
	/**
	 * Vrati pocet zalogovanych vynimiek, vyhovujucich danym kriteriam
	 * 
	 * @param Map<String, Object> kriteria, podla ktorych sa aplikuje filter
	 * @return Long pocet vyhovujucich zaznamov
	 */
	Long getCountOfLogs(Map<String, Object> criteria);
	
	
	/**
	 * Vrati zalogovanu vynimku na zaklade daneho identifikatora
	 * 
	 * @param id
	 * @return exceptionLog
	 */
	ExceptionLog getExceptionLogById(Long id);
	
	
	/**
	 * Odstrani danu vynimku zo systemu
	 * 
	 * @param exceptionLog
	 */
	void deleteException(ExceptionLog exceptionLog);
	
	
	void logException(Exception exception);
	
	
	
	ExceptionLog getLastException();
}
