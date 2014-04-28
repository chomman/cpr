package sk.peterjurkovic.cpr.utils;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;

public class PriceUtilsTest {

	@Test
	public void priceWithVatTest(){
		BigDecimal vat = new BigDecimal("1.2");
		Assert.assertEquals(new BigDecimal("120.00"), PriceUtils.getPriceWithVat(new BigDecimal("100"), vat));
		Assert.assertEquals(new BigDecimal("1276.00"), PriceUtils.getPriceWithVat(new BigDecimal("1063.33"), vat));
		Assert.assertEquals(new BigDecimal("1465.00"), PriceUtils.getPriceWithVat(new BigDecimal("1220.83"), vat));
		Assert.assertEquals(new BigDecimal("10.74"), PriceUtils.getPriceWithVat(new BigDecimal("8.95"), vat));
		Assert.assertEquals(new BigDecimal("0.86"), PriceUtils.getPriceWithVat(new BigDecimal("0.72"), vat));
	}
	
	
	@Test
	public void vatOnlyText(){
		BigDecimal vat = new BigDecimal(1.2);
		Assert.assertEquals(new BigDecimal("20.00"), PriceUtils.getVatOfPrice(new BigDecimal("100"), vat));
		Assert.assertEquals(new BigDecimal("0.14"), PriceUtils.getVatOfPrice(new BigDecimal("0.72"), vat));
	}
	
}
