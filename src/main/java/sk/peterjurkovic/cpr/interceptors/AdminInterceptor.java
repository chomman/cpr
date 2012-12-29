package sk.peterjurkovic.cpr.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import sk.peterjurkovic.cpr.utils.UserUtils;




public class AdminInterceptor extends HandlerInterceptorAdapter {
	
	protected final Logger logger = Logger.getLogger(getClass());
	
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
			
		logger.info("Called date: " + new DateTime().toString("yyyy.MM.dd / HH:mm"));
		 Map<String, Object> commonModel = new HashMap<String, Object>();
		 commonModel.put("user", UserUtils.getLoggedUser());
		 
		 modelAndView.addObject("common", commonModel);
		 modelAndView.addObject("time", new DateTime().toString("yyyy.MM.dd / HH:mm"));
		super.postHandle(request, response, handler, modelAndView);
	}
}
