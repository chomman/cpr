package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.services.WebpageCategoryService;

@Component
public class WebpageCategoryEditor extends PropertyEditorSupport {
	
	@Autowired 
	private WebpageCategoryService webpageCategoryService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }
        setValue(webpageCategoryService.getWebpageCategoryById(id));

	}
	
}
