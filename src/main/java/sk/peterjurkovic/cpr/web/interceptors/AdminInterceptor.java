package sk.peterjurkovic.cpr.web.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

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
	
		
		 if(modelAndView != null && !(modelAndView.getView() instanceof RedirectView)){
			 Map<String, Object> commonModel = new HashMap<String, Object>();
			 commonModel.put("user", UserUtils.getLoggedUser());
			 commonModel.put("time", new DateTime().toString(Constants.DATE_TIME_FORMAT));
			 commonModel.put("dateTimeFormat", Constants.DATE_TIME_FORMAT);
			 commonModel.put("dateFormat", Constants.DATE_FORMAT);
			 modelAndView.addObject("common", commonModel);
			 
		 }
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	
}
