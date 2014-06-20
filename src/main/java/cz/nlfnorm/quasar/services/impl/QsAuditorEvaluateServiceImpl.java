package cz.nlfnorm.quasar.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.quasar.dto.AuditorFunction;
import cz.nlfnorm.quasar.dto.EvaluatedAuditorEacCode;
import cz.nlfnorm.quasar.dto.QsAuditor;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorEacCode;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.services.QsAuditorAssesmentService;

@Service("qsAuditorEvaluateService")
@Transactional(readOnly = true)
public class QsAuditorEvaluateServiceImpl extends AbstractEvaluationService implements QsAuditorAssesmentService {

	private final static int QS_AUDITOR_TRAINING_ISO13485_THRESHOLD = 8;
	private final static int QS_AUDITOR_CLASS_ROOM_TRAINING_THRESHOLD = 40;
	private final static int QS_AUDITOR_MD_TRAINING_THRESHOLD = 32;
	private final static int QS_AUDITOR_NB1023_PROCEDURES_THRESHOLD = 16;
	
	@Autowired
	private AuditorService auditorService;
	
	@Override
	public QsAuditor evaluateFor(final Auditor auditor) {
		Validate.notNull(auditor);
		final QsAuditor qsAuditor = new QsAuditor(auditor);
		evaluateFormalAndLegalRequirements(qsAuditor);
		evaluateGeneralRequirements(qsAuditor);
		evaluateTrainingAuditingExperience(qsAuditor);
		evaluateActivitiesInTheMostRecentYear(qsAuditor);
		qsAuditor.setEvaluatedAuditorEacCode(evaludateCodesFor(qsAuditor));
		return qsAuditor;
	}
	
	
	@Override
	public void evaluateGeneralRequirements(final AuditorFunction auditor) {
		boolean complaint = 
				hasSufficientEducationForActiveMd(auditor) &&
				hasSufficientWorkExperienceForActiveMd(auditor);
		auditor.setGeneralRequirements(complaint);
	}

	@Override
	public void evaluateTrainingAuditingExperience(final AuditorFunction auditor) {
		boolean complaint = 
					isSufficientNb1023Procedures(auditor) &&
					isSufficientMdTraining(auditor) &&
					isSufficientIso9001Training(auditor);
		auditor.setTrainingAuditingExperience(complaint);
	}

	@Override
	public void evaluateActivitiesInTheMostRecentYear(final AuditorFunction auditor) {
		// TODO Zapracovat elect poslednych kativoit
		auditor.setActivitiesInRecentYear(true);
	}
	
	
	
	private List<EvaluatedAuditorEacCode> evaludateCodesFor(final QsAuditor qsAuditor){
		List<EvaluatedAuditorEacCode> evaluatedCodeList = new ArrayList<>();
		final Set<AuditorEacCode> eacCodes = qsAuditor.getAuditor().getAuditorsEacCodes();
		for(final AuditorEacCode code : eacCodes){
			if(code.getEacCode().isEnabled()){
				EvaluatedAuditorEacCode eac = new EvaluatedAuditorEacCode(code);
				eac.setGranted(isEacCodeGranted(code));
			}
		}
		return evaluatedCodeList;
	}
	
	
	private boolean isEacCodeGranted(final AuditorEacCode auditorEacCode){
		if(auditorEacCode.isItcApproved() || auditorEacCode.getNotifiedBody() != null){
			return true;
		}
		if(auditorEacCode.getTotalNumberOfAudits() >= auditorEacCode.getEacCode().getThreshold()){
			return true;
		}
		return false;
	}
	
	private boolean isSufficientNb1023Procedures(final AuditorFunction qsAuditor){
		return qsAuditor.getAuditor().getNb1023Procedures() >= QS_AUDITOR_NB1023_PROCEDURES_THRESHOLD;
	}
	
	private boolean isSufficientMdTraining(final AuditorFunction qsAuditor){
		final int mdTraining = qsAuditor.getAuditor().getMdd();
		final int ivdTraining = qsAuditor.getAuditor().getIvd();
		return (mdTraining + ivdTraining) >= QS_AUDITOR_MD_TRAINING_THRESHOLD;
	}
	
	private boolean isSufficientIso9001Training(final AuditorFunction qsAuditor){
		final Auditor auditor = qsAuditor.getAuditor();
		
		if(
			auditor.isAprovedForIso13485() ||
			(auditor.isAprovedForIso9001() && auditor.getIso13485() >= QS_AUDITOR_TRAINING_ISO13485_THRESHOLD) ||
			(
				auditor.getIso13485() >= QS_AUDITOR_TRAINING_ISO13485_THRESHOLD && 
				auditor.getIso13485() + auditor.getTrainingIso9001InHours() > QS_AUDITOR_CLASS_ROOM_TRAINING_THRESHOLD
			)
		){
			return true;
		}
		return false;
	}

	

}
