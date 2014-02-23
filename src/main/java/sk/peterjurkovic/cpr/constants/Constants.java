package sk.peterjurkovic.cpr.constants;

public class Constants {
	
	
	/**
	 * Prefix administracie 
	 * 
	 * http://localhost:8080/cpr/<strong>admin</strong>/
	 */
	public static final String ADMIN_PREFIX = "admin";
	
	
	
	
	/** SUCCESS_ROLE_ADMIN_URL
	 * 
	 * Rrelativna adresa, na ktoru bude po uspesnom prihlaseni presmerovany
	 * uzivatel, ktory ma uzivatelsku rolu "ROLE_ADMIN", "ROLE_SUPERADMIN"
	 */
	public static final String SUCCESS_ROLE_ADMIN_URL = "/admin/";
	
	
	
	
	/** SUCCESS_ROLE_USER_URL
	 * 
	 * Relativna adresa, na ktoru bude po uspesnom prihlaseni presmerovany
	 * uzivatel, ktory ma uzivatelsku rolu "ROLE_USER"
	 */
	public static final String SUCCESS_ROLE_USER_URL = "/";
	
	
	
	
	/** FAILURE_ROLE_USER_URL
	 * 
	 * Relativna adresa, na ktoru bude uzivatel presmerovany po neuspesnom prihlaseni
	 * v pripade ak sa prihlasoval uzivatelskej prihlasovacej stranky definovanej v "USER_ENTRY_POIN_REDIRECT_URL"
	 */
	public static final String FAILURE_ROLE_USER_URL = "/login?login_error=1";
	
	
	
	
	
	/** FAILURE_ROLE_ADMIN_URL
	 * 
	 * Relativna adresa, na ktoru bude uzivatel presmerovany po neuspesnom prihlaseni, 
	 * v pripade ak sa prihlasoval prostrednictvom administracneho prihlasovacieho formulara,
	 * Definovaneho v "ADMIN_ENTRY_POIN_REDIRECT_URL" 
	 */
	public static final String FAILURE_ROLE_ADMIN_URL = "/admin/login?login_error=1";
	
	
	
	
	
	/** ADMIN_ENTRY_POIN_REDIRECT_URL
	 * 
	 * 
	 */
	public static final String ADMIN_ENTRY_POIN_REDIRECT_URL = "/admin/login";
	
	
	
	
	
	/** USER_ENTRY_POIN_REDIRECT_URL
	 * 
	 * 
	 */
	public static final String USER_ENTRY_POIN_REDIRECT_URL = "/login";
	
	
	/** ADMIN_PAGINATION_PAGE_SIZE
	 *  	
	 *  Definuje maximalny pocet zaznamov na stranku, pri strankovani zaznamov.
	 */
	public static final int ADMIN_PAGINATION_PAGE_SIZE =  30; 
	
	
	
	/** PUBLIC_PAGINATION_PAGE_SIZE
	 *  	
	 *  Definuje maximalny pocet zaznamov na stranku, pri strankovani zaznamov.
	 */
	public static final int PUBLIC_PAGINATION_PAGE_SIZE =  10; 
	
	
	public static final int PUBLIC_STANDARD_PAGE_SIZE =  20; 
	
	
	/** PAGE_PARAM_NAME
	 *  Nazov parametru v URL, ktory obsahuje aktualnu stranku.
	 *  
	 *  e.g. ?page=2
	 */
	public static final String PAGE_PARAM_NAME = "page";
	
	
	/**
	 * Format datumu 
	 */
	public static final String DATE_FORMAT = "dd.MM.yyyy";
	
	
	/**
	 *  Format datumu a casu
	 */
	public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
	
	
	/**
	 * Identifikator nastaveni, pod ktorym su ulozene v DB
	 */
	public static final Long BASIC_SETTINGS_DB_ID = Long.valueOf(1);
	
	
	/**
	 * Identifikator kategorie - hlavne menu
	 */
	public static final Long WEBPAGE_CATEGORY_MAIN_MENU = Long.valueOf(1);
	/**
	 * Identifikator kategorie - nezobrazovat v ziadnom menu
	 */
	public static final Long WEBPAGE_CATEGORY_NO_MENU = Long.valueOf(2);
	/**
	 * Identifikator kategorie - podmenu CPR
	 */
	public static final Long WEBPAGE_CATEGORY_CPR_SUBMENU = Long.valueOf(3);
	/**
	 * Identifikator kategorie - podmenu CPR
	 */
	public static final Long WEBPAGE_CATEGORY_EHN_SUBMENU = Long.valueOf(4);
	
	
	/**
	 * Dlzka jedinecneho tokenu, identifikujuceho DoP
	 */
	public static final int DOP_TOKEN_LENGTH = 20;
	
	/**
	 * Prefix, ktory mapuje prisulusny kontroler. Automaticky sa prida ku kodu kazdej verejnej sekcie, ktoru
	 * prida uzivatel s rolou Editor, Administrator. 
	 */
	public static final String DEFAULT_WEBPAGE_URL_PREFIX = "/p/"; 
	
	/**
	 * Prefix oprazkov, ktory mapuje {@see sk.peterjurkovic.cpr.web.controllers.ImageController}
	 */
	public static final String IMAGE_URL_PREFIX = "/image/";
	
	
	public static final String SYSTEM_NAME = "Informační systém CPR";
	
	
	/**
	 * Prefix dynamicky vytvaranych loziek CSN noriem. tvar: CSN_DIR_PREFIX + csn ID
	 */
	public static final String CSN_DIR_PREFIX = "csn-";
	
	
	/**
	 * V pripade ak nebude nastavena podzlozka pre ukladanie suborov
	 */
	public static final String DEFAULT_IMAGE_SAVE_DIR = "images/";
	
	
}
