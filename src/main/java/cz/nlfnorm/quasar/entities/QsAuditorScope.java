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
@Table(name = "quasar_qs_auditor_scope", uniqueConstraints = @UniqueConstraint(columnNames = {"eac_code_id", "auditor_id"}) )
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "quasar_qs_auditor_id_seq", sequenceName = "quasar_qs_auditor_id_seq", initialValue = 1, allocationSize =1)
public class QsAuditorScope extends AbstractAuditorFunction {

	private static final long serialVersionUID = 6370251380332649136L;
	
	private EacCode eacCode;
	
	private int numberOfNbAudits;
	
	private int numberOfIso13485Audits;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_qs_auditor_id_seq")
	@Override
	public Long getId() {
		return super.getId();
	}
	
	@NotNull
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "eac_code_id")
	public EacCode getEacCode() {
		return eacCode;
	}

	public void setEacCode(EacCode eacCode) {
		this.eacCode = eacCode;
	}
	
	@Min(value = 0)
	@Column(name = "number_of_nb_audits")
	public int getNumberOfNbAudits() {
		return numberOfNbAudits;
	}

	public void setNumberOfNbAudits(int numberOfNBAudits) {
		this.numberOfNbAudits = numberOfNBAudits;
	}

	@Min(value = 0)
	@Column(name = "number_of_iso13485_audits")
	public int getNumberOfIso13485Audits() {
		return numberOfIso13485Audits;
	}

	public void setNumberOfIso13485Audits(int numberOfIso13485Audits) {
		this.numberOfIso13485Audits = numberOfIso13485Audits;
	}
	
	
}
