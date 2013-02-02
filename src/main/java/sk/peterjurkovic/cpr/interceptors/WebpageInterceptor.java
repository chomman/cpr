package sk.peterjurkovic.cpr.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.services.BasicSettingsService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.utils.RequestUtils;

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
			logger.info("WebpageInterceptor called..");
			 Map<String, Object> commonModel = new HashMap<String, Object>();
			 commonModel.put("settings", basicSettingsService.getBasicSettings());
			 commonModel.put("mainMenu", webpageService.getPublicSection(Constants.WEBPAGE_CATEGORY_MAIN_MENU));
			 if(modelAndView != null){
				 modelAndView.addObject("commonPublic", commonModel);
			 }
		}
		super.postHandle(request, response, handler, modelAndView);
	}
}
