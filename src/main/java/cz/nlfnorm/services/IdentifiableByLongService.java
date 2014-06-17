package cz.nlfnorm.services;


public interface IdentifiableByLongService<T> {
	
	T getById(Long id);
}
