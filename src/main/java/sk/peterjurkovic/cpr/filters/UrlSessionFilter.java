package sk.peterjurkovic.cpr.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class UrlSessionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,	FilterChain filterChain) throws IOException, ServletException {
		   if (!(req instanceof HttpServletRequest)) {
	            return;
	        }

	        HttpServletRequest request = (HttpServletRequest)req;
	        HttpServletResponse response = (HttpServletResponse)res;

	        // Redirect requests with JSESSIONID in URL to clean version (old links
	        // bookmarked/stored by bots)
	        // This is ONLY triggered if the request did not also contain a
	        // JSESSIONID cookie! Which should be fine for bots...
	        if (request.isRequestedSessionIdFromURL()) {
	            String url = request.getRequestURL().append(request.getQueryString() != null ? "?" + request.getQueryString() : "").toString();
	            // TODO: The url is clean, at least in Tomcat, which strips out the
	            // JSESSIONID path parameter automatically (Jetty does not?!)
	            response.setHeader("Location", url);
	            response.sendError(HttpServletResponse.SC_MOVED_PERMANENTLY);
	            return;
	        }

	        // Prevent rendering of JSESSIONID in URLs for all outgoing links
	        HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(response) {

	            @Override
	            public String encodeRedirectUrl(String url) {
	                return url;
	            }

	            @Override
	            public String encodeRedirectURL(String url) {
	                return url;
	            }

	            @Override
	            public String encodeUrl(String url) {
	                return url;
	            }

	            @Override
	            public String encodeURL(String url) {
	                return url;
	            }
	        };
	        filterChain.doFilter(req, wrappedResponse);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
