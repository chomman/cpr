package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.services.StandardService;

@Component
public class StandardPropertyEditor extends PropertyEditorSupport {
	
	@Autowired
	private StandardService standardService;
	
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }
		setValue(standardService.getStandardById(id));
	}
	
	@Override
	public String getAsText() {
		Standard standard = (Standard)getValue();
		if(standard != null){
			return standard.getId().toString();
		}
		return "";
	}
}
