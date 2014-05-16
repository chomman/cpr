package cz.nlfnorm.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.resolvers.LocaleResolver;

/**
 * Utilita pre pracu s HTTP poziadavkou
 * 
 * @author peto
 *
 */
public class RequestUtils {
	
	
	/**
	 * Ziska cast retazca na danej pozicii z url
	 * 
	 * @param request
	 * @param position
	 * @return String extrahovane data
	 */
	public static String getPartOfURLOnPosition(HttpServletRequest request, int position) {
        String URI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String pattern = URI.replaceFirst(contextPath, "");

        return getPartOFURLOnPosition(pattern, position);
    }
    
	
    public static String getPartOFURLOnPosition(String pattern, int position) {
        StringTokenizer tokenizer = new StringTokenizer(pattern, "/");
        String token = null;
        int currentPosition = 1;
        String result = null;

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            if (currentPosition == position) {
                result = token;
            }
            currentPosition++;
        }
        return result;
    }
    
    public static String getApplicationUrlPrefix(HttpServletRequest request) {
        boolean includePort = true;
        if ("http".equals(request.getScheme().toLowerCase()) && (request.getServerPort() == 80)) {
            includePort = false;
        }
        if ("https".equals(request.getScheme().toLowerCase()) && (request.getServerPort() == 443)) {
            includePort = false;
        }
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme()).append("://");
        url.append(request.getServerName());
        if (includePort) {
            url.append(":").append(request.getServerPort());
        }
        if (request.getRequestURI().startsWith(request.getContextPath())) {
            url.append(request.getContextPath());
        }
        return url.toString();
    }
    
   /**
    * Vrati mapu parametrov daneho requestu
    * 
    * @param request
    * @return mapa parametrov requestu, ak request neobsahuje ziadny paremeter, je vratena prazdna mapa, nikdy NULL
    */
	public static Map<String, Object> getRequestParameterMap(HttpServletRequest request) {
        return getRequestParameterMap(request, null);
    }
	

    public static Map<String, List<String>> getParametersMap(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            List<String> v = new ArrayList<String>();
            for (int j = 0; j < request.getParameterValues(name).length; j++) {
                v.add(request.getParameterValues(name)[j]);
            }
            params.put(name, v);
        }
        return params;
    }
	
	/**
	 * Vrati mapu parametrov requestu okrem daneho parametra.
	 * 
	 * @param request
	 * @param excludeParameter - nazov parametra, ktory sa ma vylucit
	 * @return mapa parametrov requestu, ak request neobsahuje ziadny paremeter, je vratena prazdna mapa, nikdy NULL
	 */
	public static Map<String, Object> getRequestParameterMap(HttpServletRequest request, String excludeParameter){
		Map<String, Object> params = new HashMap<String, Object>(request.getParameterMap().size());
        for (Enumeration<String> keys = request.getParameterNames(); keys.hasMoreElements();) {
            String key = (String)keys.nextElement();
            String[] values = request.getParameterValues(key);
            Object value = ((values == null) ? null :  (Object)values[0]);
            if(StringUtils.isNotBlank(excludeParameter) && excludeParameter.equalsIgnoreCase(key)){
    			continue;
    		}
            params.put(key, value);
        }
        return params;
		
	}
    
	 public static Map<String, String> getHeadersMap(HttpServletRequest request) {
        Enumeration<String> names = request.getHeaderNames();
        Map<String, String> params = new HashMap<String, String>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, request.getHeader(name));
        }
        return params;
     }
	
	
	/**
	 * Vrati parametre url, okrem daneho parametra.
	 * 
	 * @param request
	 * @param excludeParameter - nazov parametra, ktory sa ma vylucit
	 * @return parametre url, v pripade ak poziadavka nepobsahuje ziadny parameter, je vrateny prazdny retazec
	 */
	public static String getRequestParams(HttpServletRequest request, String excludeParameter){
		Map<String, Object> params = getRequestParameterMap(request, null);
		if(params.size() == 0){
			return "";
		}
		StringBuilder strParams = new StringBuilder("?");
		for(Entry<String, Object> entry : params.entrySet()) {
		    String key = entry.getKey();
		    String value = (String)entry.getValue();
		    if(StringUtils.isBlank(value)){
		    	continue;
		    }
		    if(!strParams.toString().equals("?")){
		    	strParams.append("&");
		    }
		    strParams.append(key);
		    strParams.append("=");
		    strParams.append(value);
		}
		return strParams.toString();
	}
	
    
    /**
     * Ziska cislo stranky z HTTP poziadavky
     * 
     * @param request
     * @return
     */
    public static int getPageNumber(HttpServletRequest request){
		if(request.getParameter(Constants.PAGE_PARAM_NAME) != null){
			try{
				return Integer.parseInt(request.getParameter(Constants.PAGE_PARAM_NAME));
			}catch(NumberFormatException ex){
				return 1;
			}
		}
		return 1;
	}
    
    public static Set<String> getBindingResultErrors(BindingResult result){
    	Set<String> errorMessages = new HashSet<String>();
    	List<FieldError> errors = result.getFieldErrors();
    	for (FieldError error : errors ) {
    		errorMessages.add(error.getDefaultMessage());
        }
    	return errorMessages;
    }
    
    
    public static String getLangParameter(HttpServletRequest request){
		String lang = request.getParameter("lang");
		if(StringUtils.isBlank(lang)){
			lang = "CZ";
 		}
		return lang.toUpperCase();
	}
    
    
    public static String buildUrl(String url) {
        final String lang = ContextHolder.getLang();
        return RequestUtils.buildUrl(url, lang);
    }
    
    public static String buildUrl(String url, String withLocale) {
        if(StringUtils.isBlank(url)){
        	return "";
        			
        }
    	if (withLocale != null && !withLocale.equals(LocaleResolver.CODE_CZ)) {
            if (!url.startsWith("/")) {
                return withLocale + "/" + url;
            } else {
                return withLocale + url;
            }
        }
    	if(url.startsWith("/")){
    		return url.substring(1);
    	}
        return url;
    }
    
    
    public static boolean isCzechLocale(){
    	final String langCode = ContextHolder.getLang();
    	if(StringUtils.isBlank(langCode) || langCode.equals(LocaleResolver.CODE_CZ) ){
    		return true;
    	}
    	return false;
    }
    
    public static boolean isEnglishLocale(){
    	return !isCzechLocale();
    }
    
    public static String getIpAddress(HttpServletRequest request){
    	 String ipAddress = request.getHeader("X-FORWARDED-FOR");  
    	   if (ipAddress == null) {  
    		   ipAddress = request.getRemoteAddr();  
    	   }
    	return ipAddress;
    }
    
    
    public static String getUserAgent(HttpServletRequest request, int maxLenght){
    	return cropValue(request.getHeader("User-Agent"), maxLenght);
    }
    
    public static String getBrowserName(String userAgent){
    	if (StringUtils.isBlank(userAgent)) {
            return "neznámý";
        }
    	userAgent = userAgent.toLowerCase();
        StringBuilder result = new StringBuilder();
        if (userAgent.contains("ipad")) {
            result.append("Tablet iPad");
        } else if (userAgent.contains("nexus 7")) {
            result.append("Tablet Nexus 7");
        }
        if (result.length() > 0) {
            result.append("/");
        }
        if (userAgent.contains("chrome")) {
            result.append("Chrome");
        } else if (userAgent.contains("safari")) {
            result.append("Safari");
        } else if (userAgent.contains("firefox")) {
            result.append("Firefox");
        } else if (userAgent.contains("opera")) {
            result.append("Opera");
        } else if (userAgent.contains("msie")) {
            result.append("Internet Explorer");
        } else {
            result.append("neznámý");
        }
        return result.toString();
    }
    
    public static String getReferer(HttpServletRequest request, int maxLenght) {
        return cropValue(request.getHeader("referer"), maxLenght);
    }
    
    private static String cropValue(String value, int maxLength){
    	 if(StringUtils.isNotBlank(value)){
         	if(value.length() > maxLength){
         		return value.substring(0, maxLength -1);
         	}
         	return value;
         }
         return null;
    }
    
    public static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
    
    public static boolean urlContains(final String term, HttpServletRequest request){
    	final String url = getFullURL(request);
    	if(StringUtils.isNotBlank(url)){
    		return StringUtils.contains(url, term);
    	}
    	return false;
    }
    
    /**
     * Return return request parameter integer value. If is parameter blank, or is not a number, 
     * return value is -1
     * 
     * @param name of parameter
     * @param request
     * @return parser int value if is parameter valid number, -1 otherwise
     */
    public static final int getIntParameter(final String name, HttpServletRequest request){
    	final String strInt = request.getParameter(name);
    	if(StringUtils.isNotBlank(strInt)){
    		try{
				return Integer.valueOf(strInt);
			}catch(NumberFormatException e){}
    	}
    	return -1;
    }
}
