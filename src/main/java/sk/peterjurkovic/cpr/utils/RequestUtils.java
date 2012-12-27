package sk.peterjurkovic.cpr.utils;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
	
	
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
        // sestavime url
        StringBuilder url = new StringBuilder();
        // schema
        url.append(request.getScheme()).append("://");
        // nazev hostu
        url.append(request.getServerName());
        // port
        if (includePort) {
            url.append(":").append(request.getServerPort());
        }
        // context path
        if (request.getRequestURI().startsWith(request.getContextPath())) {
            url.append(request.getContextPath());
        }
        // plna url
        return url.toString();
    }
}
