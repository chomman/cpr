package sk.peterjurkovic.cpr.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.joda.time.DateTime;


@Entity
@Table(name = "standard")
@Inheritance(strategy = InheritanceType.JOINED)
@TypeDefs( { @TypeDef(name = "jodaDateTime", typeClass = PersistentDateTime.class) })
public class Standard extends AbstractEntity {

	
	private static final long serialVersionUID = 9891333241L;
	
	private Long id;
	
	private String standardId;
	
	private String replacedStandardId;
	
	private String standardName;
	
	private DateTime startValidity;
	
	private DateTime stopValidity;
	
	private DateTime startConcurrentValidity;
	
	private DateTime stopConcurrentValidity;
	
	private StandardGroup standardGroup;
	
	private String text;
	
	private Set<Mandate> mandates;
	
	private Set<NotifiedBody> notifiedBodies;
	
	private Set<AssessmentSystem> assessmentSystems;
	
	private Set<StandardCsn> standardCsns;

	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "standard_id", length = 25, nullable = false)
	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	@Column(name = "replaced_standard_id", length = 25)
	public String getReplacedStandardId() {
		return replacedStandardId;
	}

	public void setReplacedStandardId(String replacedStandardId) {
		this.replacedStandardId = replacedStandardId;
	}
	
	@Column(name = "standard_name")
	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}
	
	@Type(type = "jodaDateTime")
	@Column(name = "start_validity")
	public DateTime getStartValidity() {
		return startValidity;
	}

	public void setStartValidity(DateTime startValidity) {
		this.startValidity = startValidity;
	}
	
	@Type(type = "jodaDateTime")
	@Column(name = "stop_validity")
	public DateTime getStopValidity() {
		return stopValidity;
	}

	public void setStopValidity(DateTime stopValidity) {
		this.stopValidity = stopValidity;
	}
	
	@Type(type = "jodaDateTime")
	@Column(name = "start_concurrent_validity")
	public DateTime getStartConcurrentValidity() {
		return startConcurrentValidity;
	}

	public void setStartConcurrentValidity(DateTime startConcurrentValidity) {
		this.startConcurrentValidity = startConcurrentValidity;
	}
	
	@Type(type = "jodaDateTime")
	@Column(name = "stop_concurrent_validity")
	public DateTime getStopConcurrentValidity() {
		return stopConcurrentValidity;
	}

	public void setStopConcurrentValidity(DateTime stopConcurrentValidity) {
		this.stopConcurrentValidity = stopConcurrentValidity;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_group_id")
	public StandardGroup getStandardGroup() {
		return standardGroup;
	}

	public void setStandardGroup(StandardGroup standardGroup) {
		this.standardGroup = standardGroup;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "standard_has_mandate", joinColumns = @JoinColumn(name = "standard_id"), inverseJoinColumns = @JoinColumn(name = "mandate_id"))
	public Set<Mandate> getMandates() {
		return mandates;
	}

	public void setMandates(Set<Mandate> mandates) {
		this.mandates = mandates;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "standard_has_notified_body", joinColumns = @JoinColumn(name = "standard_id"), inverseJoinColumns = @JoinColumn(name = "notified_body_id"))
	public Set<NotifiedBody> getNotifiedBodies() {
		return notifiedBodies;
	}

	public void setNotifiedBodies(Set<NotifiedBody> notifiedBodies) {
		this.notifiedBodies = notifiedBodies;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "standard_has_assessment_system", joinColumns = @JoinColumn(name = "standard_id"), inverseJoinColumns = @JoinColumn(name = "notified_body_id"))
	public Set<AssessmentSystem> getAssessmentSystems() {
		return assessmentSystems;
	}

	public void setAssessmentSystems(Set<AssessmentSystem> assessmentSystems) {
		this.assessmentSystems = assessmentSystems;
	}
	
	 @OneToMany(mappedBy = "standard", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public Set<StandardCsn> getStandardCsns() {
		return standardCsns;
	}

	public void setStandardCsns(Set<StandardCsn> standardCsns) {
		this.standardCsns = standardCsns;
	}

	@Column
	@Type(type = "text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
	
	
}
