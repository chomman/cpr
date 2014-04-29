package sk.peterjurkovic.cpr.service;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import sk.peterjurkovic.cpr.entities.PortalOrder;
import sk.peterjurkovic.cpr.entities.PortalProduct;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.UserInfo;
import sk.peterjurkovic.cpr.enums.PortalProductInterval;
import sk.peterjurkovic.cpr.services.PortalOrderService;
import sk.peterjurkovic.cpr.services.PortalProductService;
import sk.peterjurkovic.cpr.services.PortalUserService;
import sk.peterjurkovic.cpr.test.AbstractTest;
import sk.peterjurkovic.cpr.web.forms.portal.PortalUserForm;

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
		PortalProduct product = getTempPortalProduct();
		portalProductService.create(product);
		Assert.assertNotNull(product.getId());
	}
	
	
	@Test
	@Rollback(true)
	public void userRegistrationTest(){
		PortalProduct product = getTempPortalProduct();
		portalProductService.create(product);
		
		PortalUserForm userForm  = getPortalUserRegistrationForm();
		userForm.setPortalProduct(product);
		
		User user = userForm.toUser();
		user = portalUserService.createNewUser(user);
		Assert.assertNotNull(user.getId());
		
		PortalOrder order = userForm.toPortalOrder();
		order.setUser(user);
		portalOrderService.create(order);
		Assert.assertNotNull(order.getId());
	}
	
	@Test
	@Rollback(true)
	public void testProductActivation(){
		PortalProduct product = getTempPortalProduct();
		portalProductService.create(product);
		PortalUserForm userForm  = getPortalUserRegistrationForm();
		userForm.setPortalProduct(product);
		User user = userForm.toUser();
		user = portalUserService.createNewUser(user);
		PortalOrder order = userForm.toPortalOrder();
		order.setUser(user);
		portalOrderService.create(order);
	
		portalOrderService.activateProduct(order);
		Assert.assertNotNull(order.getDateOfActivation());
		
		LocalDate date = new LocalDate().plusYears(1);
		Assert.assertEquals(date, user.getRegistrationValidity());
		
		product.setPortalProductInterval(PortalProductInterval.MONTH);
		product.setIntervalValue(6);
		
		portalOrderService.activateProduct(order);
		// + 1 rok a 6 mes
		LocalDate newValidity = new LocalDate().plusYears(1).plusMonths(6);
		Assert.assertEquals(newValidity,  user.getRegistrationValidity());
		
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
	
	private PortalProduct getTempPortalProduct(){
		PortalProduct product = new PortalProduct();
		product.setPortalProductInterval(PortalProductInterval.YEAR);
		product.setIntervalValue(1);
		product.setPrice(new BigDecimal("1000"));
		product.setCzechName("test product");
		return product;
	}
}
