package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entita reprezentujuca skupinu vyrobku podla EU vesniku 305/2011
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */

@Entity
@Table(name = "standard_group")
public class StandardGroup extends AbstractEntity {

	
	private static final long serialVersionUID = 1985481354L;
	
	private Long id;
	
	private String groupName;
	
	private String commissionDecisionFileUrl;
	
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "grup_name")
	public String getGroupName() {
		return groupName;
	}
	

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "comission_decision_file_url")
	public String getCommissionDecisionUrl() {
		return commissionDecisionFileUrl;
	}

	public void setCommissionDecisionUrl(String commissionDecisionUrl) {
		this.commissionDecisionFileUrl = commissionDecisionUrl;
	}

	
	
	
	
	
}
