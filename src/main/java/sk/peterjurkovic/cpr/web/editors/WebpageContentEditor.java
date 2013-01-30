package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.services.WebpageContentService;

/**
 * Prekonvertuje identifikator obsahu stranky, na objekt
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Component
public class WebpageContentEditor extends PropertyEditorSupport{
	
	@Autowired
	private WebpageContentService webpageContentService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }
        setValue(webpageContentService.getWebpageContentById(id));

	}
	
}
