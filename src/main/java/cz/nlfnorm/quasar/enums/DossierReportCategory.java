package cz.nlfnorm.quasar.enums;

import java.util.Arrays;
import java.util.List;

/**
 * QUASAR enum. Represent category in documentation log
 * 
 * @author Peter Jurkovic
 * @date Jul 16, 2014
 */
public enum DossierReportCategory{
	
	IS("Is"),
	IM("Im"),
	IIA("IIa"),
	IIB("IIb"),
	III("III"),
	UVID("AIMD"),
	LIST_A("List A"),
	LIST_B("List B");
	
	private String name;

	private DossierReportCategory(final String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static List<DossierReportCategory> getAll() {
        return Arrays.asList(values());
    }

}
