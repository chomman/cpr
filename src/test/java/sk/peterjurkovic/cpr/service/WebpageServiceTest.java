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
	
	public void getTopLevelWebpageTest(){
		Webpage w0 = createWebpage(0);
		Webpage w1 = createWebpage(1);
		Webpage w2 = createWebpage(2);
		Webpage w3 = createWebpage(3);
		Webpage w01 = createWebpage(0);
		
		Assert.assertEquals(w0, w1);
		Assert.assertEquals(w0, w2);
		Assert.assertEquals(w0, w3);
		Assert.assertEquals(w0, w01);
	}
	
	private Webpage createWebpage(int deep){
		Webpage w = new Webpage();
		Long id = null;
		w.getDefaultWebpageContent().setName("web " + deep);
		if(deep == 0){
			id = webpageService.createNewWebpage(w);
		}else{
			Webpage parent = webpageService.getWebpageByCode("web-" + (deep -1));
			id = webpageService.createNewWebpage(w, parent.getId());
		}
		return webpageService.getWebpageById(id);
	}
	
}
