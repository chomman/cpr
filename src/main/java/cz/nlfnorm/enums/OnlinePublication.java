package cz.nlfnorm.enums;

import java.util.Arrays;
import java.util.List;

public enum OnlinePublication {

	NORMY("normy", "normy/csn.htm", "normy_demo/csn.htm"),
	OBECNA_BEZPECNOST("obec", "obec/web.htm", "on_line/obec/demo/web.htm"),
	PLASTY("plasty", "plasty/web.htm", "on_line/plasty/demo/web.htm"),
	PRYZE("pryze_on_line", "pryze_on_line/web.htm", "on_line/pryz/demo/web.htm"),
	POTRBUNI_SYSTEMY("trubky_on_line", "trubky_on_line/trubky.htm", "on_line/trubky/demo/trubky.htm"),
	CHEMICKE_LATKY("chem_lat", "chem_lat/web.htm", "on_line/chem_latky/demo/web.htm"),
	OBALY_ODPADY("obal_odpad", "obal_odpad/web.htm", "on_line/obal_odpad/demo/obal.htm"),
	STYK_S_POTRAV("styk", "styk/web.htm", "on_line/styk/demo/web.htm"),
	VODA_VZDUCH("voda_vzduch", "voda_vzduch/web.htm", "on_line/vzduch_voda/demo/web.htm"),
	ZIVOTNE_PRODTREDI("ziv_prost", "ziv_prost/web.htm", "on_line/ziv_prostr/demo/web.htm"),
	ANALYZA_REACH("reach", "reach/web.htm", "on_line/reach/demo/web.htm");
	
	private String code;
	private String url;
	private String previewUrl;
	
	private OnlinePublication(String code, String url, String previewUrl){
		this.code = code;
		this.url = url;
		this.previewUrl = previewUrl;
	}

	public static List<OnlinePublication> getAll() {
        return Arrays.asList(values());
    }
	
	public String getCode() {
		return code;
	}
	
	private String getSgpUrl(){
		return "http://www.sgpstandard.cz/editor/files/";
	}
	
	public String getUrl() {
		return  getSgpUrl() + url;
	}
	
	public String getPreviewUrl() {
		return getSgpUrl() + previewUrl;
	}

	
	
	
	
}
