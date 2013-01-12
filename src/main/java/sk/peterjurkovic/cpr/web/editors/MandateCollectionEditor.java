package sk.peterjurkovic.cpr.web.editors;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.services.MandateService;

/**
 * Prekonvertuje z webovy multiple select na kolekciu Set<Mandate>
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Component
public class MandateCollectionEditor extends CustomCollectionEditor {
	
	@Autowired
	private MandateService mandateService;
	
	public MandateCollectionEditor() {
		super(Set.class);
	}
	
	@Override
	protected Object convertElement(Object element) {
		
		Long id = null;
		
		if(element instanceof String && !((String)element).equals("")){
			try{
                id = Long.parseLong((String) element);
            }catch (NumberFormatException e) {
               return null;
            }
			
		}else if(element instanceof Long) {
            id = (Long) element;
        }
		return id != null ? mandateService.getMandateById(id) : null;
	}
	
	protected boolean alwaysCreateNewCollection() {
        return true;
    }
	
}
