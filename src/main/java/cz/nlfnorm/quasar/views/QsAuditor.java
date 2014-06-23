package cz.nlfnorm.quasar.views;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


@Entity
@Immutable
@Cache (usage=CacheConcurrencyStrategy.READ_ONLY) 
@Table(name = "quasar_qs_auditor")
public class QsAuditor extends AbstractFunction {

	private boolean anyEacCodeGranted;
	
	@Column(name = "has_any_eac_code_granted")
	public boolean isAnyEacCodeGranted() {
		return anyEacCodeGranted;
	}
	public void setAnyEacCodeGranted(boolean anyEacCodeGranted) {
		this.anyEacCodeGranted = anyEacCodeGranted;
	}
	
	
	
}
