package cz.nlfnorm.enums;

import java.util.Arrays;
import java.util.List;

public enum OnlinePublication {

	NORMY("normy", "/normy/csn.htm"),
	OBECNA_BEZPECNOST("obec", "/obec/web.htm"),
	PLASTY("plasty", "/plasty/web.htm"),
	PRYZE("pryze_on_line", "/pryze_on_line/web.htm"),
	POTRBUNI_SYSTEMY("trubky_on_line", "/trubky_on_line/trubky.htm"),
	CHEMICKE_LATKY("chem_lat", "/chem_lat/web.htm"),
	OBALY_ODPADY("obal_odpad", "/obal_odpad/web.htm"),
	STYK_S_POTRAV("styk", "/styk/web.htm"),
	VODA_VZDUCH("voda_vzduch", "/voda_vzduch/web.htm"),
	ZIVOTNE_PRODTREDI("ziv_prost", "/ziv_prost/web.htm"),
	ANALYZA_REACH("reach", "/reach/web.htm");
	
	private String code;
	private String url;
	
	private OnlinePublication(String code, String url){
		this.code = code;
		this.url = url;
	}

	public static List<OnlinePublication> getAll() {
        return Arrays.asList(values());
    }
	
	public String getCode() {
		return code;
	}

	public String getUrl() {
		return "http://www.sgpstandard.cz/editor/files" + url;
	}
	
	public String getPreviewUrl() {
		return "http://www.sgpstandard.cz/editor/files/on_line" + url;
	}

	
	
	
	
}
