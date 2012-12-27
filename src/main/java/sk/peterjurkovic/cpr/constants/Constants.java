package sk.peterjurkovic.cpr.constants;

public class Constants {
	
	public static final String ADMIN_PREFIX = "admin";
	
	public static final String SUCCESS_ROLE_ADMIN_URL = "/admin/";
	public static final String SUCCESS_ROLE_USER_URL = "/";
	
	public static final String FAILURE_ROLE_USER_URL = "/login?login_error=1";
	public static final String FAILURE_ROLE_ADMIN_URL = "/admin/login?login_error=1";
	
	public static final String ADMIN_ENTRY_POIN_REDIRECT_URL = "/admin/login";
	public static final String USER_ENTRY_POIN_REDIRECT_URL = "/login";
}
