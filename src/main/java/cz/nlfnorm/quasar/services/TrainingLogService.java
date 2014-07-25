package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.entities.TrainingLog;
import cz.nlfnorm.quasar.enums.LogStatus;

public interface TrainingLogService extends PageableLogService<TrainingLog>{
	
	Long createNew();
	
	Long createNewToLoginedUser();
	
	void update(TrainingLog trainingLog);
	
	void updateAndSetChanged(TrainingLog trainingLog);
	
	void changeStatus(TrainingLog trainingLog, LogStatus newStatus, String comment);
	
	void setPendingStatus(TrainingLog trainingLog, String withComment);
	
	void setRfusedStatus(TrainingLog trainingLog, String withComment);
	
	void setApprovedStatus(TrainingLog trainingLog, String withComment);
	
	List<Auditor> getUnassignedAuditorsForLog(TrainingLog log);

	List<Auditor> getAllUnassignedAuditorsForLog(TrainingLog log);
	
	List<Auditor> getUnassignedAuditorsFor(TrainingLog log);
	
	List<NandoCode> getAllUnassignedNandoCodesForLog(TrainingLog log);
	
	void updateQualification(final TrainingLog log);
}
