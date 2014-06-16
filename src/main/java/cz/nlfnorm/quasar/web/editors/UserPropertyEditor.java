package cz.nlfnorm.quasar.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.services.UserService;

/**
 * User property editor
 *  
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
@Component
public class UserPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private UserService userService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }	
		setValue( userService.getUserById(id) );
	}
}
