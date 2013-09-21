package sk.peterjurkovic.cpr.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import sk.peterjurkovic.cpr.constants.Constants;

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
    
   
	public static Map<String, Object> getRequestParameterMap(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>(request.getParameterMap().size());
        for (Enumeration<String> keys = request.getParameterNames(); keys.hasMoreElements();) {
            String key = (String)keys.nextElement();
            String[] values = request.getParameterValues(key);
            Object value = ((values == null) ? null :  (Object)values[0]);
            params.put(key, value);
        }
        return params;
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
    
    
    
}
