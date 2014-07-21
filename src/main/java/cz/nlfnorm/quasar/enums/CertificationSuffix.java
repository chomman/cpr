package cz.nlfnorm.quasar.enums;

import java.util.Arrays;
import java.util.List;

public enum CertificationSuffix {
	
	CNNB("CN/NB"),
	QSNB("QS/NB"),
	TMB("T/NB");
	
	private String name;
	
	private CertificationSuffix(final String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public static List<CertificationSuffix> getAll() {
        return Arrays.asList(values());
    }
}
