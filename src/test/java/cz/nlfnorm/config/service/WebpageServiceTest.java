package cz.nlfnorm.config.service;

import java.util.List;

import junit.framework.Assert;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.nlfnorm.config.WebpageDataSet;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.WebpageType;
import cz.nlfnorm.services.WebpageService;

public class WebpageServiceTest extends WebpageDataSet{
	
	
	@Autowired
	private WebpageService webpageService;
	
	
	@Before
	public void setUp(){
		fillData();
		for(Webpage w : list){
			if(w.getParent() == null){
				w.setId( webpageService.createNewWebpage(w) );
			}else{
				w.setId( webpageService.createNewWebpage(w, w.getParent().getId()));
			}
		}
	}
	
	@Test
	public void testCreate(){
		Webpage w1 = webpageService.getWebpageByCode("w1");
		Webpage w2 = webpageService.getWebpageByCode("w2");
		Webpage w3 = webpageService.getWebpageByCode("w3");
		Webpage w11 = webpageService.getWebpageByCode("w11");
		Webpage w12 = webpageService.getWebpageByCode("w12");
		Assert.assertEquals(getByCode("w1"), w1);
		Assert.assertEquals(getByCode("w2"), w2);
		Assert.assertEquals(getByCode("w3"), w3);
		Assert.assertEquals(w11.getParent(), w1);
		Assert.assertEquals(w12.getParent(), w1);
	}
	
		
	@Test
	public void webpageOrderTest(){
		Assert.assertEquals(0, webpageService.getWebpageByCode("w1").getOrder());
		Assert.assertEquals(1, webpageService.getWebpageByCode("w2").getOrder());
		Assert.assertEquals(2, webpageService.getWebpageByCode("w3").getOrder());
		Assert.assertEquals(3, webpageService.getWebpageByCode("w4").getOrder());
		
		Assert.assertEquals(0, webpageService.getWebpageByCode("w11").getOrder());
		Assert.assertEquals(1, webpageService.getWebpageByCode("w12").getOrder());
		Assert.assertEquals(2, webpageService.getWebpageByCode("w13").getOrder());
		
		Assert.assertEquals(0, webpageService.getWebpageByCode("w21").getOrder());
		Assert.assertEquals(1, webpageService.getWebpageByCode("w22").getOrder());
		Assert.assertEquals(2, webpageService.getWebpageByCode("w23").getOrder());
	}
	
	
	@Test
	public void changeOrderTest(){
		Webpage w4 = webpageService.getWebpageByCode("w4");
		webpageService.moveWebpage(w4, null, 1);
		w4 = webpageService.getWebpageByCode("w4");
		
		Webpage w1 = webpageService.getWebpageByCode("w1");
		Webpage w2 = webpageService.getWebpageByCode("w2");
		Webpage w3 = webpageService.getWebpageByCode("w3");
		
		Assert.assertEquals(0, w1.getOrder());
		Assert.assertEquals(1, w4.getOrder());
		Assert.assertEquals(2, w2.getOrder());
		Assert.assertEquals(3, w3.getOrder());
		
		webpageService.moveWebpage(w1, null, 3);
		
		Assert.assertEquals(3, w1.getOrder());
		Assert.assertEquals(0, w4.getOrder());
		Assert.assertEquals(1, w2.getOrder());
		Assert.assertEquals(2, w3.getOrder());
		 
		Webpage w12 = webpageService.getWebpageByCode("w12");
		
		webpageService.moveWebpage(w12, null, 0);
		
		w12 = webpageService.getWebpageByCode("w12");
		w1 = webpageService.getWebpageByCode("w1");
		w2 = webpageService.getWebpageByCode("w2");
		w3 = webpageService.getWebpageByCode("w3");
		
		Assert.assertNull(w12.getParent());

		Assert.assertEquals(0, w12.getOrder());
		Assert.assertEquals(1, w4.getOrder());
		Assert.assertEquals(2, w2.getOrder());
		Assert.assertEquals(3, w3.getOrder());
		Assert.assertEquals(4, w1.getOrder());
		
		
		Webpage w11 = webpageService.getWebpageByCode("w11");
		Webpage w13 = webpageService.getWebpageByCode("w13");
		
		Assert.assertEquals(0, w11.getOrder());
		Assert.assertEquals(1, w13.getOrder());
		
		webpageService.moveWebpage(w11, w13.getId(), 0);
		
		w11 = webpageService.getWebpageByCode("w11");
		w13 = webpageService.getWebpageByCode("w13");
		
		Assert.assertEquals(w13, w11.getParent());
		Assert.assertEquals(0, w13.getOrder());
		Assert.assertEquals(0, w11.getOrder());
	}
	
	
	
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
	
	@Test
	public void moveOldNewsToArchiveTest(){
		Webpage w1 = webpageService.getWebpageByCode("w1");
		Webpage w11 = webpageService.getWebpageByCode("w11");
		
		w11.setWebpageType(WebpageType.NEWS);
		w11.setPublishedSince(new LocalDateTime().minusWeeks(50));
		webpageService.updateWebpage(w11);
		
		List<Webpage> list = webpageService.getOldNonArchivedNewsInNode(w1);
		
		boolean found = false;
		for(Webpage w : list){
			if(w.equals(w11)){
				found = true;
			}
		}
		Assert.assertTrue(found);
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
