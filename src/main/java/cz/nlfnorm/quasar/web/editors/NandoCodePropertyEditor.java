package cz.nlfnorm.quasar.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.quasar.services.NandoCodeService;

@Component
public class NandoCodePropertyEditor extends PropertyEditorSupport {
	
	@Autowired
	private NandoCodeService nandoCodeService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }	
		setValue( nandoCodeService.getById(id) );
	}
	
}
