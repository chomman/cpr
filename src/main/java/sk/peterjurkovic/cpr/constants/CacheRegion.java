package sk.peterjurkovic.cpr.constants;


/**
 * Definica konstant regionov cache, definovanych v ehcache-hibernate.xml
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */
public class CacheRegion {
	
	
	/** USER_CACHE
	 *  Cache uchovavajuce data tykajuce sa uzivatelov
	 */
	public static final String USER_CACHE = "userCache";
	
	
	
	/** CPR_CACHE
	 * Cache uchovavajuce data tykajuce sa CPR
	 */
	public static final String CPR_CACHE = "cprCache";
	
	
	
	
	/** NEWS_CACHE
	 * Cache uchovavajuce data tykajuce sa aktualit
	 */
	public static final String NEWS_CACHE = "newsCache";
	
	
	/** WEBPAGE CACHE
	 * Cache uchovavajuce data tykajuce sa webovych sekci
	 */
	public static final String WEBPAGE_CACHE = "webpageCache";
	
	
	
	public static final String CSN_CACHE = "csnCache";
}
