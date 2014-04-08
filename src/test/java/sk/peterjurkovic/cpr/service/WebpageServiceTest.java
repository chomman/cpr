package sk.peterjurkovic.cpr.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.services.WebpageService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-testContext.xml")
@TransactionConfiguration
@Transactional
public class WebpageServiceTest {
	
	
	@Autowired
	private WebpageService webpageService;
	
	
	@Test
	public void createWebpageTest(){
		Webpage parentWebpage = new Webpage();
		parentWebpage.getDefaultWebpageContent().setName("Toplevel");
		webpageService.saveOrUpdate(parentWebpage);
		Webpage childWebpage = new Webpage(parentWebpage);
		childWebpage.getDefaultWebpageContent().setName("childWebpage");
		webpageService.saveOrUpdate(childWebpage);
		Webpage child = webpageService.getWebpageById(childWebpage.getId());
		Assert.assertEquals(child.getParent(), parentWebpage);
		Assert.assertEquals(parentWebpage.getChildrens().size(),  1);
		
		Webpage childOfChild = new Webpage();
		childOfChild.getDefaultWebpageContent().setName("childOfChild");
		Long id = webpageService.createNewWebpage(childOfChild, child.getId());
		
		Webpage thirdChild = webpageService.getWebpageById(id);
		
		Assert.assertEquals(thirdChild.getParent(),  child );
	}
	
	
	
}
