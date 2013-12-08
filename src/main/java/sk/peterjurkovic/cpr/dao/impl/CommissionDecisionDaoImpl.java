package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.CommissionDecisionDao;
import sk.peterjurkovic.cpr.entities.CommissionDecision;

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
