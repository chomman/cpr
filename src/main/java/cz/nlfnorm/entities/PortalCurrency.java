package cz.nlfnorm.entities;

import org.apache.commons.lang.StringUtils;


public enum PortalCurrency {
	
	CZK( "CZK" , "Kč"),
	EUR( "EUR",  "EUR");
	
	private String code;
	private String symbol;

	
	private PortalCurrency(String code, String symbol){
		this.code = code;
		this.symbol = symbol;
	}

	
	public String getSymbol() {
		return symbol;
	}

	public String getCode() {
		return code;
	}

	public static boolean isAvaiable(String code){
		if(StringUtils.isBlank(code)){
			return false;
		}
		if(code.equals(CZK.getCode()) || code.equals(EUR.getCode())){
			return true;
		}
		return false;
	}
	
}
