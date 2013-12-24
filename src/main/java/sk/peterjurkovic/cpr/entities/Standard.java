package sk.peterjurkovic.cpr.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import sk.peterjurkovic.cpr.enums.StandardStatus;

/**
 * Entita reprezentujuca harmonizovanu normu
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Table(name = "standard")
@SequenceGenerator(name = "standard_id_seq", sequenceName = "standard_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class  Standard extends AbstractEntity {

	
	private static final long serialVersionUID = 9891333241L;
	
	private Long id;
	
	private String standardId;
	
	private String replacedStandardId;
	
	private String czechName;
	
	private String englishName;
	
	private LocalDate startValidity;
	
	private LocalDate stopValidity;
		
	private Set<StandardGroup> standardGroups;
	
	private String text;
		
	private Set<NotifiedBody> notifiedBodies;
	
	private Set<AssessmentSystem> assessmentSystems;
	
	private Set<StandardCsn> standardCsns;
	
	private Set<Requirement> requirements;
	
	private Set<Tag> tags;
	
	private Boolean cumulative;
	
	private Long timestamp;
	
	private StandardStatus standardStatus;
	
	private Set<StandardChange> standardChanges;
	
	private Standard replaceStandard;
	
	public Standard(){
		this.notifiedBodies = new HashSet<NotifiedBody>();
		this.assessmentSystems = new HashSet<AssessmentSystem>();
		this.standardCsns = new HashSet<StandardCsn>();
		this.requirements = new HashSet<Requirement>();
		this.standardGroups = new HashSet<StandardGroup>();
		this.tags = new HashSet<Tag>();
		this.standardChanges = new HashSet<StandardChange>();
		setEnabled(Boolean.TRUE);
		setCumulative(Boolean.FALSE);
		this.standardStatus = StandardStatus.NORMAL;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_id_seq")
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
	@Column(name = "replaced_standard_code", length = 45)
	public String getReplacedStandardId() {
		return replacedStandardId;
	}

	public void setReplacedStandardId(String replacedStandardId) {
		this.replacedStandardId = replacedStandardId;
	}
	
	//@NotBlank(message = "Český název harmonizované normy musí být vyplněno")
	@Column(name = "czech_name", length = 300)
	public String getCzechName() {
		return czechName;
	}

	public void setCzechName(String czechName) {
		this.czechName = czechName;
	}
	
	@Column(name = "english_name", length = 300)	
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "start_validity")
	public LocalDate getStartValidity() {
		return startValidity;
	}

	public void setStartValidity(LocalDate startValidity) {
		this.startValidity = startValidity;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "stop_validity")
	public LocalDate getStopValidity() {
		return stopValidity;
	}

	public void setStopValidity(LocalDate stopValidity) {
		this.stopValidity = stopValidity;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "standard_is_in_standard_group", joinColumns = @JoinColumn(name = "standard_id"), inverseJoinColumns = @JoinColumn(name = "standard_group_id"))
	public Set<StandardGroup> getStandardGroups() {
		return standardGroups;
	}

	public void setStandardGroups(Set<StandardGroup> standardGroups) {
		this.standardGroups = standardGroups;
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
	
	@ManyToMany(fetch = FetchType.LAZY)
	@Cascade(value = { org.hibernate.annotations.CascadeType.DETACH })
    @JoinTable(name = "standard_has_csn", joinColumns = @JoinColumn(name = "standard_id"), inverseJoinColumns = @JoinColumn(name = "standard_csn_id"))
	public Set<StandardCsn> getStandardCsns() {
		return standardCsns;
	}

	public void setStandardCsns(Set<StandardCsn> StandardCsns) {
		this.standardCsns = StandardCsns;
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
	
	@OneToMany(mappedBy = "standard", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<StandardChange> getStandardChanges() {
		return standardChanges;
	}

	public void setStandardChanges(Set<StandardChange> standardChanges) {
		this.standardChanges = standardChanges;
	}
	
	@ManyToOne
	@JoinColumn(name = "replaced_standard_id",  insertable = false, updatable = false)
	public Standard getReplaceStandard() {
		return replaceStandard;
	}

	public void setReplaceStandard(Standard replaceStandard) {
		this.replaceStandard = replaceStandard;
	}

	@Transient
	public StandardChange getStandardChangeById(final long id){
		for(StandardChange standardChange : standardChanges){
			Long chId = standardChange.getId();
			if(chId == id){
				return standardChange;
			}
		}
		return null;
	}
	
	@Transient
	public StandardCsn getStandardCsnId(final long id){
		for(StandardCsn csn : standardCsns){
			Long csnId = csn.getId();
			if(csnId == id){
				return csn;
			}
		}
		return null;
	}
	
	@Transient
	public boolean removeStandardChange(final long id){
		StandardChange persisted = getStandardChangeById(id);
		if(persisted != null){
			return standardChanges.remove(persisted);
		}
		return false;
	}
	
	@Transient
	public boolean createOrUpdateStandardChange(final StandardChange form){
		if(form.getId() == null || form.getId() == 0){
			StandardChange newChange = new StandardChange();
			newChange.merge(form);
			newChange.setStandard(this);
			newChange.setCreated(new LocalDateTime());
			standardChanges.add(newChange);
			return true;
		}else{
			StandardChange persisted = getStandardChangeById(form.getId());
			if(persisted != null){
				persisted.merge(form);
				return true;
			}
		}
		return false;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "standard_status", length = 15)
	public StandardStatus getStandardStatus() {
		return standardStatus;
	}

	public void setStandardStatus(StandardStatus standardStatus) {
		this.standardStatus = standardStatus;
	}
	
	
}
