package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.CommissionDecision;

public interface CommissionDecisionService {
	
	void create(CommissionDecision commisionDecision);
	
	void update(CommissionDecision commisionDecision);
	
	void delete(CommissionDecision commisionDecision);
	
	CommissionDecision getById(Long id);
	
	List<CommissionDecision> getAll();
	
	void saveOrUpdate(CommissionDecision commissionDecision);
	
}
