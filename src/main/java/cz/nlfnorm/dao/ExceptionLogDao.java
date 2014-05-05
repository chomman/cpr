package cz.nlfnorm.dao;

import java.util.List;
import java.util.Map;

import cz.nlfnorm.entities.ExceptionLog;

/**
 * Dao rozhranie pre dotazovanie logov
 * 
 * @author peto
 *
 */
public interface ExceptionLogDao extends BaseDao<ExceptionLog, Long>{
	
	
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
}
