package sk.peterjurkovic.cpr.filters;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.context.ContextHolder;
import sk.peterjurkovic.cpr.resolvers.LocaleResolver;

public class LocaleFilter implements Filter {

	@Autowired
	private LocaleResolver localeResolver;

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		if(excludeRequest((HttpServletRequest)req)){
			chain.doFilter(req, res);
		}else{
			updateContext((HttpServletRequest)req);
			chain.doFilter(req, res);
		}
		
	}
	
	private void updateContext(HttpServletRequest req) { 
	        Locale locale = localeResolver.resolve(req);
	        ContextHolder.setLocale(locale);
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	
	private boolean excludeRequest(HttpServletRequest request){
		final String url = request.getRequestURI().substring(request.getContextPath().length());;
		if(url.startsWith(Constants.IMAGE_URL_PREFIX) || url.startsWith("/resources/")){
			return true;
		}
		
		return false;
	}
	
}
