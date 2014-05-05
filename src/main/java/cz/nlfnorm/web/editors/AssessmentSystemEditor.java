package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.services.AssessmentSystemService;

@Component
public class AssessmentSystemEditor extends PropertyEditorSupport {
	
	@Autowired
	private AssessmentSystemService assessmentSystemService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }
		setValue(assessmentSystemService.getAssessmentSystemById(id));
	} 
}
