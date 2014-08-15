package cz.nlfnorm.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@SequenceGenerator(name = "shared_id_seq", sequenceName = "shared_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "regulation")
public class Regulation extends AbstractEntity {
	
	private static final long serialVersionUID = 2004446779583304022L;
	private final static String CZ = "cs";
	private final static String SK = "sk";
	private final static String EU = "en";
	
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
	public boolean isCzechRegulation(){
		return isLocaleSet(CZ);
	}
	
	@Transient
	public boolean isSlovakRegulation(){
		return isLocaleSet(SK);
	}
	
	@Transient
	public boolean isEuRegulation(){
		return isLocaleSet(EU);
	}
	
	@Transient
	public RegulationContent getCzechRegulation(){
		return getInLocale(CZ);
	}
	
	@Transient
	public RegulationContent getSlovakRegulation(){
		return getInLocale(SK);
	}
	
	@Transient
	public RegulationContent getEuRegulation(){
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
	
}
