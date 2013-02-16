package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.services.NotifiedBodyService;


@Component
public class NotifiedBodyEditor extends PropertyEditorSupport {
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }
		setValue(notifiedBodyService.getNotifiedBodyById(id));
	} 
}
