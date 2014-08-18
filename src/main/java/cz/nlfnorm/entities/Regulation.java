package cz.nlfnorm.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity, which represent regulation
 * 
 * @author Peter Jurkovic
 * @date Aug 18, 2014
 */
@Entity
@SequenceGenerator(name = "shared_id_seq", sequenceName = "shared_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "regulation")
public class Regulation extends IdentifiableEntity {
	
	private static final long serialVersionUID = 2004446779583304022L;
	private final static String CS = "cs";
	private final static String SK = "sk";
	private final static String EU = "eu";
	private String code;
	private Map<String, RegulationContent> localized = new HashMap<>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shared_id_seq")
	public Long getId() {
		return super.getId();
	}
		
	@ElementCollection
	@CollectionTable(name = "regulation_content")
	@MapKeyJoinColumn(name = "locale")
	public Map<String, RegulationContent> getLocalized() {
		return localized;
	}

	public void setLocalized(Map<String, RegulationContent> localized) {
		this.localized = localized;
	}
	
	@Transient
	public boolean isCsRegulation(){
		return isLocaleSet(CS);
	}
	
	@Transient
	public boolean isSkRegulation(){
		return isLocaleSet(SK);
	}
	
	@Transient
	public boolean isEuRegulation(){
		return isLocaleSet(EU);
	}
	
	@Transient
	public RegulationContent getCsRegulationContent(){
		return getInLocale(CS);
	}
	
	@Transient
	public RegulationContent getSkRegulationContent(){
		return getInLocale(SK);
	}
	
	@Transient
	public RegulationContent getEuRegulationContent(){
		return getInLocale(EU);
	}
	
	private boolean isLocaleSet(final String locale){
		return localized.containsKey(locale);
	}
	
	private RegulationContent getInLocale(final String locale){
		if(isLocaleSet(locale)){
			return localized.get(locale);
		}
		return null;
	}

	@NotEmpty( message = "{error.regulation.code}")
	@Length(max = 20, message = "{error.regulation.code.length}")
	@Column(length = 20, unique = true, nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Transient
	public static boolean isAvaiable(final String locale){
		if(locale != null && (
					locale.equals(EU) || 
					locale.equals(CS) || 
					locale.equals(SK)
			)){
			return true;
		}
		return false;
	}
}
