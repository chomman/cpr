package sk.peterjurkovic.cpr.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceUtils {
	
	
	public static BigDecimal getPriceWithVat(BigDecimal price, BigDecimal vat){
		if(price == null || vat == null){
			return new BigDecimal(0);
		}
		return price.multiply(vat).setScale(2, RoundingMode.HALF_UP);
	}
	
	
	public static BigDecimal getVatOfPrice(BigDecimal priceWithoutVat, BigDecimal vat){
		if(priceWithoutVat == null || vat == null){
			return new BigDecimal(0);
		}
		return getPriceWithVat(priceWithoutVat, vat).subtract(priceWithoutVat);
	}
	
	public static String getFormatedVat(BigDecimal vat){
		if(vat == null){
			return "";
		}
		return vat.subtract(new BigDecimal("1"))
				  .multiply(new BigDecimal("100"))
				  .setScale(0)
				  .toString()+ "%";
	}
	
}
