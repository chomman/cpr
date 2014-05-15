package cz.nlfnorm.entities;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public enum PortalCurrency {
	
	CZK(1,"CZK", "Kč"),
	EUR(2,"EUR", "EUR");
	
	private int id;
	private String code;
	private String symbol;

	
	private PortalCurrency(int id, String code, String symbol){
		this.id = id;
		this.code = code;
		this.symbol = symbol;
	}

	
	
	public int getId() {
		return id;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getCode() {
		return code;
	}
	
	
	public static List<PortalCurrency> getAll() {
        return Arrays.asList(values());
    }
	
	public static PortalCurrency getById(final int id){
		for(PortalCurrency currency : getAll()){
			if(currency.getId() == id){
				return currency;
			}
		}
		return null;
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
