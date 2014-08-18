package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.Regulation;

public interface RegulationService extends IdentifiableByLongService<Regulation>{
	
	void update(Regulation regulation);
	
	void create(Regulation regulation);
	
	List<Regulation> getAll();
	
	void createOrUpdate(Regulation regulation);
	
}
