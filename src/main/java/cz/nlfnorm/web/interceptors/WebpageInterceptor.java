
package cz.nlfnorm.web.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.WebpageService;
import cz.nlfnorm.utils.RequestUtils;

public class WebpageInterceptor extends HandlerInterceptorAdapter{
	
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private BasicSettingsService basicSettingsService;
	@Autowired
	private WebpageService webpageService;
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
		String prefix = RequestUtils.getPartOfURLOnPosition(request, 1);
		if(StringUtils.isBlank(prefix) || !prefix.equals(Constants.ADMIN_PREFIX)){
			 Map<String, Object> commonModel = new HashMap<String, Object>();
			 

			 if(StringUtils.isNotBlank(request.getParameter(Constants.PREVIEW_PARAM))){
				 modelAndView.addObject(Constants.PREVIEW_PARAM, true); 
			 }
			 commonModel.put("dateTimeFormat", Constants.DATE_FORMAT);
			 commonModel.put("locale", ContextHolder.getLang());
			 if(modelAndView != null){
				 modelAndView.addObject("commonPublic", commonModel);
			 }
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
}
