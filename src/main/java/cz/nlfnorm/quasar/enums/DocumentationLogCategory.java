package cz.nlfnorm.quasar.enums;

import java.util.Arrays;
import java.util.List;

import cz.nlfnorm.quasar.hibernate.PersistentEnum;

/**
 * QUASAR enum. Represent caregory in documentation log
 * 
 * @author Peter Jurkovic
 * @date Jul 16, 2014
 */
public enum DocumentationLogCategory implements PersistentEnum{
	
	IS(1,"IS"),
	IM(2,"IM"),
	IIA(3,"IIa"),
	IIB(4,"IIb"),
	III(5,"III"),
	UVID(6,"UIVD"),
	LIST_A(7,"Seznam A"),
	LIST_B(8,"Seznam B");
	
	private int id;
	private String name;

	private DocumentationLogCategory(final int id, final String name){
		this.id = id;
		this.name = name;
	}
	
	@Override
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public static List<DocumentationLogCategory> getAll() {
        return Arrays.asList(values());
    }
	
	public static DocumentationLogCategory getById(final int id){
		for(final DocumentationLogCategory c : getAll()){
			if(id == c.getId()){
				return c;
			}
		}
		return null;
	}

}
