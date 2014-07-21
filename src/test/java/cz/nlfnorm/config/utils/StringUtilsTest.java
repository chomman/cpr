package cz.nlfnorm.config.utils;

import junit.framework.Assert;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import cz.nlfnorm.utils.NlfStringUtils;

public class StringUtilsTest {
	
	@Test
	public void randomTokenTest(){
		String token = RandomStringUtils.randomAlphanumeric(32);
			   token = RandomStringUtils.randomAscii(32);
		Assert.assertNotNull(token);
	}

	@Test
	public void testInsertionOfCharaters(){
		String val = "141234";
		Assert.assertEquals("14 1234", NlfStringUtils.insertCharatersAt(2, val, " "));
		
		val = "";
		Assert.assertEquals("", NlfStringUtils.insertCharatersAt(2, val, " "));
		
		val = null;
		Assert.assertEquals(null, NlfStringUtils.insertCharatersAt(2, val, " "));
	}
}
