package sk.peterjurkovic.cpr.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.services.WebpageService;

/**
 * Test calculates with empty database (table webpage, table webpage_content)
 * @author peto
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-testContext.xml")
@TransactionConfiguration
@Transactional
public class WebpageUtilsTest {
	
	private static final String CONTEXT_PATH = "/cpr/";
	
	@Autowired
	private WebpageService webpageService;
	
	@Test
	public void testUrlBuild(){
		Webpage w0 = createWebpage(0);
		Webpage w1 = createWebpage(1);
		Webpage w2 = createWebpage(2);
		Webpage w3 = createWebpage(3);
		Webpage w01 = createWebpage(0);
		
		Assert.assertTrue(w0.isHomepage());
		Assert.assertFalse(w01.isHomepage());

		Assert.assertEquals(CONTEXT_PATH, WebpageUtils.getUrlFor(w0, CONTEXT_PATH));
		Assert.assertEquals(CONTEXT_PATH + w01.getCode(), WebpageUtils.getUrlFor(w01, CONTEXT_PATH));
		Assert.assertEquals(CONTEXT_PATH + "web-0/" +w1.getId()+ "/web-1", WebpageUtils.getUrlFor(w1, CONTEXT_PATH));
		Assert.assertEquals(CONTEXT_PATH + "web-0/" +w2.getId()+ "/web-2", WebpageUtils.getUrlFor(w2, CONTEXT_PATH));
		Assert.assertEquals(CONTEXT_PATH + "web-0/" +w3.getId()+ "/web-3", WebpageUtils.getUrlFor(w3, CONTEXT_PATH));
		
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
