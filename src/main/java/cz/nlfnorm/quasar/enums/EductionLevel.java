package cz.nlfnorm.quasar.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Enum of Quality system assesmet reporting system, represents education level
 * 
 * 
 * A master’s degree can substitute 1 year of the working experience,
 * Post-Graduate study PGS can substitute 2 years, and PhD and higher (Doc.,
 * Prof. etc.) in a relevant subject e.g. including device design,
 * clinical/performance requirements may be used to substitute 3 years working
 * experience, respectively. The total substitution together cannot exceed 3
 * years.
 * 
 * @author Peter Jurkovic
 * @date Jun 6, 2014
 */
public enum EductionLevel {
	
	BASIC(1, 0, "educationLevel.basicSchool"), 
	HIGH(2, 0,"educationLevel.highSchool"),
	BACHELOR(3, 0,"educationLevel.bachelor"),
	MASTER(4, 1,"educationLevel.master"),
	PGS(5, 2,"educationLevel.postgraduate"),
	PHD(6, 3,"educationLevel.phd"),
	PROF(7, 3,"educationLevel.prof");

	private int id;
	private int yeasSubstitution;
	private String code;

	private EductionLevel(int id, int yearsSubstitution, String code) {
		this.id = id;
		this.code = code;
		this.yeasSubstitution = yearsSubstitution;
	}
	
	public static List<EductionLevel> getAll() {
        return Arrays.asList(values());
    }
	
	public static EductionLevel getById(int id) {
        for (EductionLevel i : getAll()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

	public int getId() {
		return id;
	}

	public int getYeasSubstitution() {
		return yeasSubstitution;
	}

	public String getCode() {
		return code;
	}

}
