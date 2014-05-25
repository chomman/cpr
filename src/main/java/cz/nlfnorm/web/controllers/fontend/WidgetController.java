package cz.nlfnorm.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.web.forms.portal.PortalUserForm;

@Controller
public class WidgetController extends PortalWebpageControllerSupport{

	@RequestMapping(value = { "/widget/registrace", "/{lang}/widget/registrace"} )
	public String handleRegistrationPage(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		preparePortalOrderModel(model, modelMap, request, new PortalUserForm());
		appendModel(modelMap, model);
		return "/widget/registration"; 
	}
}
