package sk.peterjurkovic.cpr.service;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.entities.PortalOrderItem;
import cz.nlfnorm.entities.PortalProduct;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserInfo;
import cz.nlfnorm.entities.UserOnlinePublication;
import cz.nlfnorm.enums.OnlinePublication;
import cz.nlfnorm.enums.PortalProductType;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.web.forms.portal.PortalUserForm;
import sk.peterjurkovic.cpr.test.AbstractTest;

public class PortalServiceTest extends AbstractTest{
	
	private final static String USER_EMAIL = "test@nlfnorm.cz";
	
	@Autowired
	private PortalOrderService portalOrderService;
	@Autowired
	private PortalProductService portalProductService;
	@Autowired
	private PortalUserService portalUserService;
	
		
	
	@Test
	public void portalProductCreationTest(){
		PortalProduct product = getRegistrationProduct();
		portalProductService.create(product);
		Assert.assertNotNull(product.getId());
	}
	
	
	@Test
	@Rollback(true)
	public void userRegistrationTest(){
		PortalProduct registrationProduct = getRegistrationProduct();
		portalProductService.create(registrationProduct);
		PortalProduct publicationProduct = getPublicationProduct(OnlinePublication.ANALYZA_REACH);
		portalProductService.create(publicationProduct);
		PortalUserForm userForm  = getPortalUserRegistrationForm();
		User user = userForm.toUser();
		user = portalUserService.createNewUser(user);
		Assert.assertNotNull(user.getId());
		PortalOrder order = userForm.toPortalOrder();
		addProduct(registrationProduct, order);
		
		Assert.assertNotNull(order.getRegistrationPortalProduct());
		
		addProduct(publicationProduct, order);
		order.setUser(user);
		portalOrderService.create(order);
		Assert.assertNotNull(order.getId());
		
		portalOrderService.activateProducts(order);
		Assert.assertNotNull(order.getDateOfActivation());
		
		LocalDate date = new LocalDate().plusYears(1);
		Assert.assertEquals(date, user.getRegistrationValidity());
		
		Assert.assertTrue(user.hasValidOnlinePublication(OnlinePublication.ANALYZA_REACH));
		Assert.assertFalse(user.hasValidOnlinePublication(OnlinePublication.CHEMICKE_LATKY));
		
		UserOnlinePublication uop = user.getUserOnlinePublication(OnlinePublication.ANALYZA_REACH);
		
		Assert.assertNotNull(uop);
		Assert.assertEquals(date, uop.getValidity());
		
		PortalProduct publicationProduct2 = getPublicationProduct(OnlinePublication.CHEMICKE_LATKY);
		portalProductService.create(publicationProduct2);
		
		order.setDateOfActivation(null);
		addProduct(publicationProduct2, order);
		portalOrderService.update(order);
		portalOrderService.activateProducts(order);
		Assert.assertNotNull(order.getDateOfActivation());
		
		Assert.assertTrue(user.hasValidOnlinePublication(OnlinePublication.ANALYZA_REACH));
		Assert.assertTrue(user.hasValidOnlinePublication(OnlinePublication.CHEMICKE_LATKY));
		Assert.assertFalse(user.hasValidOnlinePublication(OnlinePublication.NORMY));
		
		LocalDate twoYars = date.plusYears(1);
		Assert.assertEquals(twoYars, user.getRegistrationValidity());
		Assert.assertEquals(twoYars, uop.getValidity());
		
		UserOnlinePublication uop2 = user.getUserOnlinePublication(OnlinePublication.CHEMICKE_LATKY);
		
		Assert.assertNotNull(uop2);
		Assert.assertEquals(date, uop2.getValidity());
	}
	
	
	
	private PortalUserForm getPortalUserRegistrationForm(){
		PortalUserForm form = new PortalUserForm();
		form.setFirstName("test");	
		form.setLastName("test");
		form.setEmail(USER_EMAIL);
		form.setPassword("123456");
		form.setUserInfo(new UserInfo());
		form.getUserInfo().setPhone("+421 123 123 165");
		form.getUserInfo().setCity("city");
		form.getUserInfo().setStreet("street");
		form.getUserInfo().setZip("904 00");
		return form;
		
	}
	
	private PortalProduct getRegistrationProduct(){
		PortalProduct product = new PortalProduct();
		product.setPriceCzk(new BigDecimal("1000"));
		product.setPriceEur(new BigDecimal("33"));
		product.setCzechName("test product");
		product.setPortalProductType(PortalProductType.REGISTRATION);
		return product;
	}
	private PortalProduct getPublicationProduct(OnlinePublication pub){
		PortalProduct product = new PortalProduct();
		product.setPriceCzk(new BigDecimal("2000"));
		product.setPriceEur(new BigDecimal("50"));
		product.setCzechName("test product");
		product.setPortalProductType(PortalProductType.PUBLICATION);
		product.setOnlinePublication(pub);
		return product;
	}
	
	private void addProduct(PortalProduct product, PortalOrder order){
		PortalOrderItem item = new PortalOrderItem();
		item.setPortalProduct(product);
		item.setPortalOrder(order);
		item.setPrice(product.getPriceCzk());
		order.getOrderItems().add(item);
	}
}
