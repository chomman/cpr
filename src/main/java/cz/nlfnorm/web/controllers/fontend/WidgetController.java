package cz.nlfnorm.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.nlfnorm.web.forms.portal.PortalUserForm;

@Controller
public class WidgetController extends PortalWebpageControllerSupport{

	private final static String CSS_PARAM = "css";
	
	@RequestMapping(value = { "/widget/registrace", "/{lang}/widget/registrace"} )
	public String handleRegistrationPage(
			@RequestParam(value = CSS_PARAM, required = false) String css,
			ModelMap modelMap, 
			HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(CSS_PARAM, css);
		preparePortalOrderModel(model, modelMap, request, new PortalUserForm());
		appendModel(modelMap, model);
		return "/widget/widget"; 
	}
}
