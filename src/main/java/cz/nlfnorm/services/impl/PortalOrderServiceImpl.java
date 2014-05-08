package cz.nlfnorm.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.PortalOrderDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.BasicSettings;
import cz.nlfnorm.entities.EmailTemplate;
import cz.nlfnorm.entities.PortalCurrency;
import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.entities.PortalOrderItem;
import cz.nlfnorm.entities.PortalProduct;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserOnlinePublication;
import cz.nlfnorm.enums.OrderStatus;
import cz.nlfnorm.enums.PortalOrderSource;
import cz.nlfnorm.enums.PortalProductInterval;
import cz.nlfnorm.enums.SystemLocale;
import cz.nlfnorm.mail.HtmlMailMessage;
import cz.nlfnorm.mail.NlfnormMailSender;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.EmailTemplateService;
import cz.nlfnorm.services.ExceptionLogService;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.editors.LocalDateEditor;


@Transactional(propagation = Propagation.REQUIRED)
@Service("portalOrderService")
public class PortalOrderServiceImpl implements PortalOrderService {

	private final static Logger logger = Logger.getLogger(PortalOrderServiceImpl.class);
	
	@Autowired
	private PortalOrderDao portalOrderDao;
	@Autowired
	private LocalDateEditor localDateEditor;
	@Autowired
	private UserService userService;
	@Autowired
	private PortalUserService portalUserService;
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired
	private BasicSettingsService basicSettingsService;
	@Autowired
	private NlfnormMailSender nlfnormMailSender;
	@Autowired
	private ExceptionLogService exceptionLogService;
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public void create(final PortalOrder pordalOrder) {
		portalOrderDao.save(pordalOrder);
	}

	@Override
	public void update(PortalOrder pordalOrder) {
		portalOrderDao.update(pordalOrder);
	}

	@Override
	public void delete(PortalOrder pordalOrder) {
		portalOrderDao.remove(pordalOrder);
	}

	@Override
	@Transactional(readOnly = true)
	public PortalOrder getById(Long id) {
		return portalOrderDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PortalOrder> getAll() {
		return portalOrderDao.getAll();
	}

	@Override
	public PageDto getPortalOrderPage(int currentPage, Map<String, Object> criteria) {
		return portalOrderDao.getPortalOrderPage(currentPage, validateCriteria(criteria));
	}
	
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put(Filter.ORDER, ParseUtils.parseIntFromStringObject(criteria.get(Filter.ORDER)));
			criteria.put(Filter.CREATED_FROM, ParseUtils.parseDateTimeFromStringObject(criteria.get(Filter.CREATED_FROM)));
			criteria.put(Filter.CREATED_TO, ParseUtils.parseDateTimeFromStringObject(criteria.get(Filter.CREATED_TO)));
		}
		return criteria;
	}

	@Override
	public void updateAndSetChanged(PortalOrder order, boolean sendEmail) {
		setChanged(order);
		boolean isUpdated = false;
		if(sendEmail){
			if(order.getDateOfActivation() == null && order.getOrderStatus().equals(OrderStatus.PAYED)){
				activateProducts(order);
				isUpdated = true;
			}
			// TODO send information email impl
		}
		if(!isUpdated){
			update(order);
		}
	}

	private void setChanged(PortalOrder order){
		final User user = UserUtils.getLoggedUser();
		order.setChangedBy(user);
		order.setChanged(new LocalDateTime());
	}
	
	@Override
	public void activateProducts(PortalOrder order) {
		activateRegistraion(order);
		activatePublications(order);
		portalUserService.syncUser(order.getUser());
		order.setDateOfActivation(new LocalDate());
		update(order);
	}

	private void activateRegistraion(PortalOrder order){
		Validate.notNull(order);
		User user = order.getUser();
		Validate.notNull(user);
		PortalProduct product = order.getRegistrationPortalProduct();
		LocalDate today = new LocalDate();
		if(product != null){
			LocalDate newRegistrationValidity = null;
			if(user.getRegistrationValidity() == null || user.getRegistrationValidity().isBefore(today)){
				newRegistrationValidity = incrementValidity(today, product);
			}else{
				newRegistrationValidity = incrementValidity(user.getRegistrationValidity(), product);
			}
			user.setRegistrationValidity(newRegistrationValidity);
			userService.createOrUpdateUser(user);
		}
	}
	
	private void activatePublications(PortalOrder order){
		List<PortalProduct> publicationList = order.getPublications();
		if(publicationList.size() > 0){
			User user = order.getUser();
			Validate.notNull(user);
			for(PortalProduct product : publicationList){
				UserOnlinePublication userOnlinePublication =  user.getUserOnlinePublication(product);
				if(userOnlinePublication == null){
					// create new one
					userOnlinePublication = new UserOnlinePublication(user);
					LocalDate newValidity = computeNewValidity( null , product);
					userOnlinePublication.setValidity(newValidity);
					userOnlinePublication.setUser(user);
					userOnlinePublication.setPortalProduct(product);
					user.getOnlinePublications().add(userOnlinePublication);
				}else{
					// Extensions of existing
					LocalDate newValidity = computeNewValidity(userOnlinePublication.getValidity(), product);
					userOnlinePublication.setValidity(newValidity);
					userOnlinePublication.setChanged(new LocalDateTime());
				}
			}
		  userService.createOrUpdateUser(user);
		}
	}
	
	private LocalDate computeNewValidity(LocalDate currentValidity, PortalProduct product){
		Validate.notNull(product);
		LocalDate today = new LocalDate();
		if(currentValidity == null || currentValidity.isBefore(today)){
			return incrementValidity(today, product);
		}
		return incrementValidity(currentValidity, product);
	}
	
	private LocalDate incrementValidity(LocalDate fromDate, PortalProduct product){
		Validate.notNull(fromDate);
		Validate.notNull(product);
		PortalProductInterval intervalType = product.getPortalProductInterval();
		Validate.notNull(intervalType);
		if(intervalType.equals(PortalProductInterval.MONTH)){
			return fromDate.plusMonths(product.getIntervalValue());
		}else if(intervalType.equals(PortalProductInterval.YEAR)){
			return fromDate.plusYears(product.getIntervalValue());
		}
		throw new IllegalArgumentException("Unknown portalProduct interval: " + intervalType.toString());
	}

	
	@Override
	public void mergeAndSetChange(PortalOrder order) {
		setChanged(order);
		portalOrderDao.merge(order);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PortalOrder> getUserOrders(final Long userId) {
		return portalOrderDao.getUserOrders(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public void sendRegistrationOrderEmail(final PortalOrder order) {
		final BasicSettings basicSettings = basicSettingsService.getBasicSettings();
		final EmailTemplate template = emailTemplateService.getByCode(EmailTemplate.PORTAL_CREATE_ORDER);
		Validate.notNull(template);
		final Map<String, Object> context = getMailContextForRegistrationOrder(basicSettings, order);
		HtmlMailMessage mailMessage = new HtmlMailMessage(basicSettings.getSystemEmail(), template.getSubject(), template.getBody(), context);
		mailMessage.addRecipientTo(order.getEmail());
		mailMessage.addRecipientBcc(template.getBccEmails());
		final String emailAddress = determineEmailForPortalOrderSource(basicSettings, order);
		if(StringUtils.isNotBlank(emailAddress)){
			mailMessage.addRecipientBcc(emailAddress);
		}
		try{
			nlfnormMailSender.send(mailMessage);
		}catch(Exception e){
			logger.error("Nepodarilo sa odoslat objednavku registracie [oid="+order.getId()+"]", e);
		}
	}

	private Map<String, Object> getMailContextForRegistrationOrder(final BasicSettings basicSettings, final PortalOrder order){
		Map<String, Object> context = new HashMap<String, Object>();
		appendOrederContext(order, context);
		if(order.getCurrency().equals(PortalCurrency.CZK)){
			context.put("cisloUctu",basicSettings.getCzAccountNumber());
			context.put("iban", basicSettings.getCzIban());
			context.put("swift", basicSettings.getCzSwift());
		}else{
			context.put("cisloUctu",basicSettings.getEuAccountNumber());
			context.put("iban", basicSettings.getEuIban());
			context.put("swift", basicSettings.getEuSwift());
		}
		return context;
	}
	
	
	private String determineEmailForPortalOrderSource(final BasicSettings settings, final PortalOrder order){
		final PortalOrderSource orderSource = order.getPortalOrderSource();
		if(orderSource.equals(PortalOrderSource.PLASTIC_PORTAL)){
			return settings.getPlasticPortalEmail();
		}
		return null;
	}
	
	private void appendOrederContext(final PortalOrder order, Map<String, Object> context){
		context.put("vs", order.getId().toString());
		context.put("jmeno", order.getFirstName());
		context.put("prijmeni", order.getLastName());
		context.put("telefon", order.getPhone());
		context.put("email", order.getEmail());
		context.put("mesto", order.getCity());
		context.put("ulice", order.getStreet());
		context.put("psc", order.getZip());
		context.put("nazevFirmy", order.getCompanyName());
		context.put("icFirmy", order.getIco());
		context.put("dicFirmy", order.getDic());
		context.put("sumaCelkem", order.getTotalPriceWithVatAndCurrency());
		context.put("polozkyObjedavky", getForamtedOrderItems(order).toString());
		
	}

	
	@Override
	public StringBuilder getForamtedOrderItems(PortalOrder order) {
		Validate.notNull(order);
		String currency = " " + order.getCurrency().getSymbol();
		String thCss = "style=\"padding:8px 5px;background:#ccc;\"";
		String tdCss = "style=\"padding:5px;background:#fafafa;border:1px solid #ccc;\"";
		StringBuilder html = new StringBuilder("<table style=\"border-collapse:collapse;border:1px solid #555;margin:20px 0;\" >");
		html.append("<tr><th "+thCss+" >")
			.append(messageSource.getMessage("admin.service.name", null, SystemLocale.getCzechLocale()))
			.append("</th><th "+thCss+">")
			.append(messageSource.getMessage("admin.service.price", null, SystemLocale.getCzechLocale()))
			.append("</th></tr>");
		for(PortalOrderItem item : order.getOrderItems()){
			html.append("<tr><td "+tdCss+">")
			.append(item.getPortalProduct().getCzechName())
			.append("</td><td "+tdCss+">")
			.append(item.getPriceWithVat()).append(currency)
			.append("</td></tr>");
		}
		html.append("</table>");
		return html;
	}
}
