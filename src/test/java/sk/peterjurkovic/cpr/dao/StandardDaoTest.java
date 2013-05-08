package sk.peterjurkovic.cpr.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.entities.Standard;


public class StandardDaoTest extends AbstractTest{
	
	@Autowired
	private StandardDao standardDao;
	
	@Test
	public void testCreate(){
		Standard standard = getTestInstance();
		standardDao.save(standard);
		Standard persistedStandard = standardDao.getByCode(standard.getCode());
		Assert.assertEquals(standard, persistedStandard);
		
		standardDao.remove(persistedStandard);

	}
	
	@Test
	public void testGetAll(){
		int expectedSize = standardDao.getAll().size();
		
		Standard standard = getTestInstance();
		
		standardDao.save(standard);
		
		int newSize = standardDao.getAll().size();
		
		Assert.assertEquals(expectedSize, newSize-1);
		
		standardDao.remove(standard);
		
		Assert.assertEquals(expectedSize, standardDao.getAll().size());
	}
	
	
	private Standard getTestInstance(){
		Standard standard  = new Standard();
		standard.setStandardId("EN 12345");
		standard.setStandardName("test");
		standard.setCode("test");
		return standard;
	}
}
