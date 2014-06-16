package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.Partner;

/**
 * QUASAR Component
 * 
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
public interface PartnerService {
	
	void update(Partner partner);
	
	void create(Partner partner);
	
	void delete(Partner partner);
	
	Partner getById(Long id);
	
	List<Partner> getAll();
	
	void createOrUpdate(Partner partner);
	
}
