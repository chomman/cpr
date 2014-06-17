package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import cz.nlfnorm.entities.IdentifiableByLong;
import cz.nlfnorm.services.IdentifiableByLongService;

public class IdentifiableByLongPropertyEditor<T extends IdentifiableByLong> extends PropertyEditorSupport {
	
	private IdentifiableByLongService<T> service;
	
	
	public IdentifiableByLongPropertyEditor(IdentifiableByLongService<T> service){
		this.service = service;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }	
		setValue( service.getById(id) );
	}
	
	@SuppressWarnings("unchecked")
	public String getAsText() {
        if (getValue() != null) {
            final T t = (T)getValue();
            if (t.getId() != null) {
                return t.getId().toString();
            }
        }
        return "";
    }
}
