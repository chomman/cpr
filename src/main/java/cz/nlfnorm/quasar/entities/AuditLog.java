package cz.nlfnorm.quasar.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

/**
 * QUASAR entity. Represent Auditor audit log
 * 
 * @author Peter Jurkovic
 * @date Jul 7, 2014
 */
@Entity
@Table(name = "quasar_audit_log")
public class AuditLog extends AbstractLog {
	
	private static final long serialVersionUID = -5586903173779695020L;
	private Set<AuditLogItem> items;
	
	
	public AuditLog(){}
	
	
	public AuditLog(Auditor auditor){
		super(auditor);
	}
	
	@OrderBy(clause = "date")
	@OneToMany(mappedBy = "auditLog", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<AuditLogItem> getItems() {
		return items;
	}

	public void setItems(Set<AuditLogItem> items) {
		this.items = items;
	}

}
