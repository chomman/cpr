package cz.nlfnorm.utils;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;

import cz.nlfnorm.utils.DateTimeUtils;

public class DateTimeUtilsTest {

	@Test
	public void subtractTest(){
		LocalDate today = new LocalDate();
		
		LocalDate tomorrow = today.plusDays(1);
		
		Assert.assertEquals(0, DateTimeUtils.daysLeft(today));
		Assert.assertEquals(1, DateTimeUtils.daysLeft(tomorrow));
		
		
		Assert.assertEquals(-10, DateTimeUtils.daysLeft( today.plusDays(-10) ) );
	}
}
