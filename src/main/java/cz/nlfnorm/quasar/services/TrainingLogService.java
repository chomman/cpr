package cz.nlfnorm.quasar.services;

import cz.nlfnorm.quasar.entities.TrainingLog;
import cz.nlfnorm.quasar.enums.LogStatus;

public interface TrainingLogService extends PageableLogService<TrainingLog>{
	
	Long createNewToLoginedUser();
	
	void update(TrainingLog trainingLog);
	
	void updateAndSetChanged(TrainingLog trainingLog);
	
	void changeStatus(TrainingLog trainingLog, LogStatus newStatus, String comment);
	
	void setPendingStatus(TrainingLog trainingLog, String withComment);
	
	void setRfusedStatus(TrainingLog trainingLog, String withComment);
	
	void setApprovedStatus(TrainingLog trainingLog, String withComment);

}
