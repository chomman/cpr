package sk.peterjurkovic.cpr.entities;

import java.util.HashSet;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.joda.time.DateTime;

/**
 * Entita reprezentujuca harmonizovanu normu
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
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
	
	private Set<Csn> standardCsns;
	
	private Set<Requirement> requirements;
	
	private Set<Tag> tags;
	
	private Boolean cumulative;
	
	private Long timestamp;
	
	public Standard(){
		this.mandates = new HashSet<Mandate>();
		this.notifiedBodies = new HashSet<NotifiedBody>();
		this.assessmentSystems = new HashSet<AssessmentSystem>();
		this.standardCsns = new HashSet<Csn>();
		this.requirements = new HashSet<Requirement>();
		this.tags = new HashSet<Tag>();
		setEnabled(Boolean.FALSE);
		setCumulative(Boolean.FALSE);
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Označení harmonizované normy musí být vyplněno")
	@Length(max = 45, message = "Označení harmonizované normy může mít max. 45 znaků")
	@Column(name = "standard_id", length = 45, nullable = false)
	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	
	@Length(max = 45, message = "Nahrazená harmonizovaná norma může mít max. 45 znaků")
	@Column(name = "replaced_standard_id", length = 45)
	public String getReplacedStandardId() {
		return replacedStandardId;
	}

	public void setReplacedStandardId(String replacedStandardId) {
		this.replacedStandardId = replacedStandardId;
	}
	
	@NotEmpty(message = "Český název harmonizované normy musí být vyplněno")
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
	public Set<Csn> getStandardCsns() {
		return standardCsns;
	}

	public void setStandardCsns(Set<Csn> standardCsns) {
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

	
	@OneToMany(mappedBy = "standard", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public Set<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(Set<Requirement> requirements) {
		this.requirements = requirements;
	}

	@OneToMany(mappedBy = "standard", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public Set<Tag> getTags() {
		return tags;
	}
	
	
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	@Transient
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Column(name = "is_cumulative")
	public Boolean getCumulative() {
		return cumulative;
	}

	public void setCumulative(Boolean cumulative) {
		this.cumulative = cumulative;
	}
	
	
	
	
	
	
	
	
	
	
}
