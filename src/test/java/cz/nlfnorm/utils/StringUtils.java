package cz.nlfnorm.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.springframework.util.Assert;

public class StringUtils {
	
	@Test
	public void randomTokenTest(){
		String token = RandomStringUtils.randomAlphanumeric(32);
			   token = RandomStringUtils.randomAscii(32);
		Assert.notNull(token);
	}
}
