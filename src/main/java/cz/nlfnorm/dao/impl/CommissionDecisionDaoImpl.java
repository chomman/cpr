package cz.nlfnorm.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.CommissionDecisionDao;
import cz.nlfnorm.entities.CommissionDecision;

/**
 * Implementacia rozhrania CommissionDecision
 * 
 * @author Peter Jurkovič
 * @date Dec 8, 2013
 *
 */
@Repository("commissionDecisionDao")
public class CommissionDecisionDaoImpl extends BaseDaoImpl<CommissionDecision, Long> implements CommissionDecisionDao {

	public CommissionDecisionDaoImpl(){
		super(CommissionDecision.class);
	}

}
