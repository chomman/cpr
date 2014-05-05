package cz.nlfnorm.enums;

import java.util.Arrays;
import java.util.List;

public enum OnlinePublication {

	NORMY("normy"),
	OBECNA_BEZPECNOST("obec"),
	PLASTY("plasty"),
	PRYZE("pryze_on_line"),
	POTRBUNI_SYSTEMY("trubky_on_line"),
	CHEMICKE_LATKY("chem_lat"),
	OBALY_ODPADY("obal_odpad"),
	STYK_S_POTRAV("styk"),
	VODA_VZDUCH("voda_vzduch"),
	ZIVOTNE_PRODTREDI("ziv_prost"),
	ANALYZA_REACH("reach");
	
	private String code;
	
	private OnlinePublication(String code){
		this.code = code;
	}

	public static List<OnlinePublication> getAll() {
        return Arrays.asList(values());
    }
	
	public String getCode() {
		return code;
	}
	
	
}
