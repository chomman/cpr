package cz.nlfnorm.enums;

import java.util.Arrays;
import java.util.List;


/**
 * Enum, reprezentujuci jazyk terminologie ČSN
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date 02.08.2013
 */
public enum CsnTerminologyLanguage {
	
	CZ("cz", "lang.czech"),
	EN("en", "lang.english");
	
	private String code;
	
	private String name;
	
	
	private CsnTerminologyLanguage(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	public static List<CsnTerminologyLanguage> getAll() {
        return Arrays.asList(values());
    }
	
	
	public static CsnTerminologyLanguage getByCode(String code) {
        for (CsnTerminologyLanguage i : getAll()) {
            if (i.getCode().equals(code)) {
                return i;
            }
        }
        return null;
    }

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}
