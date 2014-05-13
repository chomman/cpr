package cz.nlfnorm.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.tests.WebpageDateSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-testContext.xml")
@TransactionConfiguration
@Transactional
public class WebpageDaoTest extends WebpageDateSet{
	
	@Autowired
	private WebpageDao webpageDao;
	
	
	@org.junit.Before
	public void init(){
		fillData();
	}
	
	@Test
	public void saveTest(){
		for(Webpage w : list){
			int order = 0;
			if(w.getParent() == null){
				order = webpageDao.getMaxOrderInNode(null);
			}else{
				order = webpageDao.getMaxOrderInNode(w.getParent().getId());
			}
			w.setOrder(order);
			webpageDao.save(w);
		}
		
		Webpage w1 = webpageDao.getByCode("w1");
		Webpage w2 = webpageDao.getByCode("w2");
		Webpage w3 = webpageDao.getByCode("w3");
		Webpage w11 = webpageDao.getByCode("w11");
		Webpage w12 = webpageDao.getByCode("w12");
		Assert.assertEquals(getByCode("w1"), w1);
		Assert.assertEquals(getByCode("w2"), w2);
		Assert.assertEquals(getByCode("w3"), w3);
		Assert.assertEquals(w11.getParent(), w1);
		Assert.assertEquals(w12.getParent(), w1);
	}
	
	
	@Test
	public void testWebpageOrder(){
		
		Assert.assertEquals(0, getByCode("w1").getOrder());
		Assert.assertEquals(1, getByCode("w2").getOrder());
		Assert.assertEquals(2, getByCode("w3").getOrder());
		
		Assert.assertEquals(0, getByCode("w11").getOrder());
		Assert.assertEquals(1, getByCode("w12").getOrder());
		Assert.assertEquals(2, getByCode("w13").getOrder());
		
		Assert.assertEquals(0, getByCode("w21").getOrder());
		Assert.assertEquals(1, getByCode("w22").getOrder());
		Assert.assertEquals(2, getByCode("w33").getOrder());
		
	}
}
