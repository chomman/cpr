package cz.nlfnorm.quasar.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "quasar_product_assesor_a_scope", uniqueConstraints = @UniqueConstraint(columnNames = {"nando_code_id", "auditor_id"}) )
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "quasar_product_assesor_a_id_seq", sequenceName = "quasar_product_assesor_a_id_seq", initialValue = 1, allocationSize =1)
public class ProductAssesorAScope extends AbstractAuditorFunction {

	private static final long serialVersionUID = -4193813519262412701L;
	
	/**
	 * Category-specific training (hours)
	 */
	private int training;
	/**
	 * Number of NB audits in category
	 */
	private int numberOfNbAudits;
	/**
	 * Number of ISO 13485 audits in relevant technical area
	 */
	private int numberOfIso13485Audits;
	
	private NandoCode nandoCode;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_product_assesor_a_id_seq")
	@Override
	public Long getId() {
		return super.getId();
	}
	
	@Min(value = 0)
	@Column(name = "training_hours")
	public int getTraining() {
		return training;
	}
	public void setTraining(int training) {
		this.training = training;
	}
	
	@Min(value = 0)
	@Column(name = "number_of_nb_audits")
	public int getNumberOfNbAudits() {
		return numberOfNbAudits;
	}
	public void setNumberOfNbAudits(int numberOfNbAudits) {
		this.numberOfNbAudits = numberOfNbAudits;
	}
	
	@Min(value = 0)
	@Column(name = "number_of_iso13485_audits")
	public int getNumberOfIso13485Audits() {
		return numberOfIso13485Audits;
	}
	public void setNumberOfIso13485Audits(int numberOfIso13485Audits) {
		this.numberOfIso13485Audits = numberOfIso13485Audits;
	}
	
	@NotNull
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "nando_code_id")
	public NandoCode getNandoCode() {
		return nandoCode;
	}
	public void setNandoCode(NandoCode nandoCode) {
		this.nandoCode = nandoCode;
	}
	
	
}
