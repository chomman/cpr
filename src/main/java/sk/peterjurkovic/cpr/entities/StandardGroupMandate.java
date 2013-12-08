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

/**
 * Asociacna entita Mandatov k Skupine harzmonizovanych noriem
 * 
 * 
 * @author Peter Jurkovič
 * @date Dec 8, 2013
 *
 */
@Entity
@SequenceGenerator(name = "standard_group_has_mandate_id_seq", sequenceName = "standard_group_has_mandate_id_seq", initialValue = 1, allocationSize =1)
@Table(name="standard_group_has_mandate")
public class StandardGroupMandate {
	
	private Long id;
	
	private StandardGroup standardGroup;
	
	private Mandate mandate;
	
	/*
	 * Identifikuje, ci je dany mandat doplnok k priradzenym mandatom
	 */
	private boolean complement = false;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_group_has_mandate_id_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_group_id")
	public StandardGroup getStandardGroup() {
		return standardGroup;
	}
	
	public void setStandardGroup(StandardGroup standardGroup) {
		this.standardGroup = standardGroup;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mandate_id")
	public Mandate getMandate() {
		return mandate;
	}

	public void setMandate(Mandate mandate) {
		this.mandate = mandate;
	}
	
	@Column(name = "complement")
	public boolean isComplement() {
		return complement;
	}

	public void setComplement(boolean complement) {
		this.complement = complement;
	}
	
	
}
