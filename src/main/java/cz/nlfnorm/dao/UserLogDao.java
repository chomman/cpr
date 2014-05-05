package cz.nlfnorm.dao;

import java.util.List;
import java.util.Map;

import cz.nlfnorm.entities.UserLog;

/**
 * Rozhranie pre pracu s uzivatelskymi pristupami
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface UserLogDao extends BaseDao<UserLog, Long>{
	
	
	/**
	 * Metoda vrati zalogovany zaznam podla zadanej session ID.
	 * 
	 * @param String sessionID
	 * @return UserLog zalogovany zaznam Uzivatela
	 */
	UserLog getBySessionId(String sessionId);
	
	
	
	/**
	 * Metoda vrati stranku pristupov do administracnej sekcie systemu
	 * 
	 * @param int cislo stranky
	 * @param kriteria, podla ktorych sa vykona filter
	 * 
	 * @return stranka uzivatelskych pristpov
	 */
	List<UserLog> getLogPage(int pageNumber, Map<String, Object> criteria);
	
	
	
	/**
	 * Vrati pocet uzivatelskych pristupov, vyhovujucich danym kriteriam
	 * 
	 * @param Map<String, Object> kriteria, podla ktorych sa aplikuje filter
	 * @return Long pocet vyhovujucich zaznamov
	 */
	Long getCountOfLogs(Map<String, Object> criteria);
	
}
