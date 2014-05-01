package sk.peterjurkovic.cpr.enums;

public interface EnumInterface<T> {
	
	T getAll();
	
	T getById(int id);
}
