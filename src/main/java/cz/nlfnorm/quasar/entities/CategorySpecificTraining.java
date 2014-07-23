package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@SequenceGenerator(name = "quasar_log_item_id_seq", sequenceName = "quasar_log_item_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "quasar_training_log_has_cst")
public class CategorySpecificTraining extends IdentifiableEntity{

	private static final long serialVersionUID = -2032766225387809476L;
	private TrainingLog trainingLog;
	private NandoCode nandoCode;
	private int hours;
	
	public CategorySpecificTraining(){}
	
	public CategorySpecificTraining(final TrainingLog log, NandoCode code, int hours){
		this.trainingLog = log;
		this.nandoCode  = code;
		this.hours = hours;
	}
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_log_item_id_seq")
	public Long getId() {
		return super.getId();
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_log_id", nullable = false)
	public TrainingLog getTrainingLog() {
		return trainingLog;
	}
	public void setTrainingLog(TrainingLog trainingLog) {
		this.trainingLog = trainingLog;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nando_code_id", nullable = false)
	public NandoCode getNandoCode() {
		return nandoCode;
	}
	public void setNandoCode(NandoCode nandoCode) {
		this.nandoCode = nandoCode;
	}
	
	@Min(value = 1)
	@Column(name = "hours", nullable = false, columnDefinition = "SMALLINT")
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
		
}
