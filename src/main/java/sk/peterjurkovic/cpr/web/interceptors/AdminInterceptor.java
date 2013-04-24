package sk.peterjurkovic.cpr.web.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.utils.UserUtils;


/**
 * Interceptor, ktory je volany po kazdom poziadavku na admin controllery, resp. na pattern /admin/*
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */

public class AdminInterceptor extends HandlerInterceptorAdapter {
		
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	
		 Map<String, Object> commonModel = new HashMap<String, Object>();
		 commonModel.put("user", UserUtils.getLoggedUser());
		 if(modelAndView != null){
			 modelAndView.addObject("common", commonModel);
			 modelAndView.addObject("time", new DateTime().toString(Constants.DATE_TIME_FORMAT));
			 modelAndView.addObject("dateTimeFormat", Constants.DATE_TIME_FORMAT);
			 
		 }
		super.postHandle(request, response, handler, modelAndView);
	}
}
