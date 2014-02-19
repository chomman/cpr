package sk.peterjurkovic.cpr.entities;

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

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name="standard_has_notified_body")
@SequenceGenerator(name = "standard_has_notified_body_id_seq", sequenceName = "standard_has_notified_body_id_seq", initialValue = 1, allocationSize =1)
public class StandardNotifiedBody {
	
	private Long id;
	
	private Standard standard;
	
	private NotifiedBody notifiedBody;
	
	private LocalDate assignmentDate = new LocalDate();
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_has_notified_body_id_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
	public Standard getStandard() {
		return standard;
	}

	public void setStandard(Standard standard) {
		this.standard = standard;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notified_body_id")
	public NotifiedBody getNotifiedBody() {
		return notifiedBody;
	}

	public void setNotifiedBody(NotifiedBody notifiedBody) {
		this.notifiedBody = notifiedBody;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "assignment_date")
	public LocalDate getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(LocalDate assignmentDate) {
		this.assignmentDate = assignmentDate;
	}
	
	
}
