package cz.nlfnorm.quasar.entities;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "quasar_audit_log_id_seq")
public class AuditLog extends AbstractLog {
	
	private static final long serialVersionUID = -5586903173779695020L;
	
	
}
