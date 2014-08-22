package cz.nlfnorm.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import cz.nlfnorm.utils.RequestUtils;

/**
 * Entita reprezentujuca harmonizovanu normu
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Table(name = "standard")
@SequenceGenerator(name = "standard_id_seq", sequenceName = "standard_id_seq", initialValue = 1, allocationSize =1)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Standard extends AbstractStandard {

	private static final long serialVersionUID = 9891333241L;
	private String standardId;
	private String czechName;
	private String englishName;
	private LocalDate startValidity;
	private LocalDate stopValidity;
	private Set<StandardGroup> standardGroups;
	private String text;
	private Set<StandardNotifiedBody> notifiedBodies;
	private Set<AssessmentSystem> assessmentSystems;
	private Set<StandardCsn> standardCsns;
	@JsonIgnore
	private Standard replaceStandard;
	private Set<StandardChange> standardChanges;
	private StandardCategory standardCategory;
	
	public Standard(){
		this.notifiedBodies = new HashSet<StandardNotifiedBody>();
		this.assessmentSystems = new HashSet<AssessmentSystem>();
		this.standardCsns = new HashSet<StandardCsn>();
		this.standardGroups = new HashSet<StandardGroup>();
		this.standardChanges = new HashSet<StandardChange>();
		setEnabled(Boolean.TRUE);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_id_seq")
	public Long getId() {
		return super.getId();
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

	@OrderBy("id")
	@OneToMany(mappedBy = "standard", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public Set<StandardNotifiedBody> getNotifiedBodies() {
		return notifiedBodies;
	}


	public void setNotifiedBodies(Set<StandardNotifiedBody> notifiedBodies) {
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
    @JoinTable(name = "standard_has_csn", joinColumns = @JoinColumn(name = "standard_id"), inverseJoinColumns = @JoinColumn(name = "standard_csn_id") )
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
		
	@OneToMany(mappedBy = "standard", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<StandardChange> getStandardChanges() {
		return standardChanges;
	}

	public void setStandardChanges(Set<StandardChange> standardChanges) {
		this.standardChanges = standardChanges;
	}
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "replaced_standard_id")
	public Standard getReplaceStandard() {
		return replaceStandard;
	}

	public void setReplaceStandard(Standard replaceStandard) {
		this.replaceStandard = replaceStandard;
	}
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "standard_category_id")
	public StandardCategory getStandardCategory() {
		return standardCategory;
	}

	public void setStandardCategory(StandardCategory standardCategory) {
		this.standardCategory = standardCategory;
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

	
	@Transient
	public String getName(){
		if(!RequestUtils.isCzechLocale() && StringUtils.isNotBlank(englishName)){
			return englishName;
		}
		return czechName;
	}
	
	
	@Transient
	public boolean getHasChanges(){
		return CollectionUtils.isNotEmpty(standardChanges);
	}
	
	@Transient
	public boolean isCprCategory(){
		return standardCategory != null && standardCategory.getCode().equals("CPR");
	}
}
