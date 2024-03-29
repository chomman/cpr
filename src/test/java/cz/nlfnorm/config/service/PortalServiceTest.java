package cz.nlfnorm.config.service;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;

import cz.nlfnorm.config.TestConfig;
import cz.nlfnorm.entities.BasicSettings;
import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.entities.PortalOrderItem;
import cz.nlfnorm.entities.PortalProduct;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserInfo;
import cz.nlfnorm.entities.UserOnlinePublication;
import cz.nlfnorm.enums.OnlinePublication;
import cz.nlfnorm.enums.OrderStatus;
import cz.nlfnorm.enums.PortalOrderSource;
import cz.nlfnorm.enums.PortalProductType;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.web.forms.portal.PortalUserForm;

public class PortalServiceTest extends TestConfig{
	
	@Autowired
	private PortalOrderService portalOrderService;
	@Autowired
	private PortalProductService portalProductService;
	@Autowired
	private PortalUserService portalUserService;
	@Autowired
	private BasicSettingsService basicSettingsService;
	
	@Value("${mail.developer}")
	private String developerEmail;

		
	
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
		
		PortalUserForm userForm  = getPortalUserRegistrationForm();
		User user = userForm.toUser();
		user = portalUserService.createNewUser(user);
		Assert.assertNotNull(user.getId());
		PortalOrder order = userForm.toPortalOrder();
		addProduct(registrationProduct, order);
		
		Assert.assertNotNull(order.getRegistrationPortalProduct());
		PortalProduct analyzaPublication =  create(OnlinePublication.ANALYZA_REACH);
		addProduct(analyzaPublication, order);
		order.setUser(user);
		portalOrderService.create(order);
		Assert.assertNotNull(order.getId());
		
		portalOrderService.activateProducts(order);
		Assert.assertNotNull(order.getDateOfActivation());
		
		LocalDate date = new LocalDate().plusYears(1);
		Assert.assertEquals(date, user.getRegistrationValidity());
		
		Assert.assertTrue(user.hasValidOnlinePublication(analyzaPublication));
		PortalProduct chemLatkyPublication =  create(OnlinePublication.CHEMICKE_LATKY);
		Assert.assertFalse(user.hasValidOnlinePublication(chemLatkyPublication));
		
		UserOnlinePublication uop = user.getUserOnlinePublication(analyzaPublication);
		
		Assert.assertNotNull(uop);
		Assert.assertEquals(date, uop.getValidity());
		

		
		order.setDateOfActivation(null);
		addProduct(chemLatkyPublication, order);
		portalOrderService.update(order);
		portalOrderService.activateProducts(order);
		Assert.assertNotNull(order.getDateOfActivation());
		
		Assert.assertTrue(user.hasValidOnlinePublication(chemLatkyPublication));
		Assert.assertTrue(user.hasValidOnlinePublication(analyzaPublication));
		
		LocalDate twoYars = date.plusYears(1);
		Assert.assertEquals(twoYars, user.getRegistrationValidity());
		Assert.assertEquals(twoYars, uop.getValidity());
		
		UserOnlinePublication uop2 = user.getUserOnlinePublication(chemLatkyPublication);
		
		Assert.assertNotNull(uop2);
		Assert.assertEquals(date, uop2.getValidity());
	}
	
	public void orderCancelationTest(){
		PortalProduct registrationProduct = getRegistrationProduct();
		portalProductService.create(registrationProduct);
		PortalUserForm userForm  = getPortalUserRegistrationForm();
		User user = userForm.toUser();
		user = portalUserService.createNewUser(user);
		PortalOrder order = userForm.toPortalOrder();
		order.setPortalOrderSource(PortalOrderSource.PLASTIC_PORTAL);
		addProduct(registrationProduct, order);
		PortalProduct analyzaPublication =  create(OnlinePublication.ANALYZA_REACH);
		addProduct(analyzaPublication, order);
		order.setUser(user);
		portalOrderService.create(order);
		
		BasicSettings settings = basicSettingsService.getBasicSettings();
		
		settings.setPlasticPortalEmail(developerEmail);
		basicSettingsService.updateBasicSettings(settings);
		order.setOrderStatus(OrderStatus.CANCELED);
		portalOrderService.updateAndSetChanged(order);
	}
	
	private PortalUserForm getPortalUserRegistrationForm(){
		PortalUserForm form = new PortalUserForm();
		form.setFirstName("test");	
		form.setLastName("test");
		form.setEmail(developerEmail);
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
	
	private PortalProduct create(OnlinePublication pub){
		PortalProduct publicationProduct = getPublicationProduct(pub);
		portalProductService.create(publicationProduct);
		return publicationProduct;
	}
}
