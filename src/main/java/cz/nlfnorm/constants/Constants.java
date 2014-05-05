package cz.nlfnorm.constants;

import java.math.BigDecimal;

public class Constants {
	
	
	/**
	 * Prefix administracie 
	 * 
	 * Example: http://localhost:8080/cpr/<strong>admin</strong>/
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
	
	
	public static final String FAILURE_LOGIN_PARAM_KEY = "loginError";
	
	/** FAILURE_ROLE_ADMIN_URL
	 * 
	 * Relativna adresa, na ktoru bude uzivatel presmerovany po neuspesnom prihlaseni, 
	 * v pripade ak sa prihlasoval prostrednictvom administracneho prihlasovacieho formulara,
	 * Definovaneho v "ADMIN_ENTRY_POIN_REDIRECT_URL" 
	 */
	public static final String ADMIN_FAILURE_LOGIN_URL = "/admin/login?"+Constants.FAILURE_LOGIN_PARAM_KEY+"=1";
	
	
	
	
	
	/** ADMIN_ENTRY_POIN_REDIRECT_URL
	 * 
	 * 
	 */
	public static final String ADMIN_ENTRY_POIN_REDIRECT_URL = "/admin/login";
	
	
		
	
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
	 * Prefix, ktory mapuje prisulusny kontroler. Automaticky sa prida ku kodu kazdej verejnej sekcie, ktoru
	 * prida uzivatel s rolou Editor, Administrator. 
	 */
	public static final String DEFAULT_WEBPAGE_URL_PREFIX = "/p/"; 
	
	/**
	 * Prefix oprazkov, ktory mapuje {@link cz.nlfnorm.web.controllers.ImageController}
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
	
	
	public static final String PREVIEW_PARAM = "isPreview";
	
	public static final String PORTAL_URL = "informacni-portal";
	
	public static final String PORTAL_ID_PARAM_KEY = "ip";

	public static final String PORTAL_FAILURE_LOGIN_URL = "/"+Constants.PORTAL_URL+"?"+Constants.FAILURE_LOGIN_PARAM_KEY+"=1";
	
	public static final BigDecimal VAT = new BigDecimal("1.21");
	
	
	
}
