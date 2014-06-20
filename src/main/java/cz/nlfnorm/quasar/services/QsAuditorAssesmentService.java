package cz.nlfnorm.quasar.services;

import cz.nlfnorm.quasar.dto.QsAuditor;
import cz.nlfnorm.quasar.entities.Auditor;

public interface QsAuditorAssesmentService{

	QsAuditor evaluateFor(Auditor auditor);
	
}
