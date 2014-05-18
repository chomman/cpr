package cz.nlfnorm.services.impl;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.RequestContext;

import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.dao.PortalOrderDao;
import cz.nlfnorm.dto.ByteFileDto;
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
import cz.nlfnorm.export.pdf.PdfXhtmlExporter;
import cz.nlfnorm.mail.HtmlMailMessage;
import cz.nlfnorm.mail.NlfnormMailSender;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.EmailTemplateService;
import cz.nlfnorm.services.ExceptionLogService;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.DateTimeUtils;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.controllers.ExportController;
import cz.nlfnorm.web.editors.LocalDateEditor;


@Transactional(propagation = Propagation.REQUIRED)
@Service("portalOrderService")
public class PortalOrderServiceImpl implements PortalOrderService {

	
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
	@Autowired
	private PdfXhtmlExporter pdfXhtmlExporter;
	
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
	public void updateAndSetChanged(PortalOrder order) {
		setChanged(order);
		final OrderStatus orderStatus = order.getOrderStatus();
		if(!order.getEmailSent() && !orderStatus.equals(OrderStatus.PENDING)){
			if(orderStatus.equals(OrderStatus.PAYED)){
				activateProducts(order);
				sendOrderActivationEmail(order);
				order.setEmailSent(true);
			}else if(orderStatus.equals(OrderStatus.CANCELED)){
				sendOrderCancelationEmail(order);
				order.setEmailSent(true);
			}
		}
		setChanged(order);
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
	public void sendOrderCreateEmail(final PortalOrder order, final RequestContext requestContext) {
		Thread thread = new Thread(){
			 public void run(){
				final EmailTemplate template = emailTemplateService.getByCode(EmailTemplate.PORTAL_ORDER_CREATE);
				sendPortalOrderEmail(order, template, requestContext);
			 }
		};
		thread.run();
	}
	
	@Override
	@Transactional(readOnly = true)
	public void sendOrderActivationEmail(final PortalOrder order) {
		final EmailTemplate template = emailTemplateService.getByCode(EmailTemplate.PORTAL_ORDER_ACTIVATION);
		sendPortalOrderEmail(order, template);
		
	}
	@Override
	@Transactional(readOnly = true)
	public void sendOrderCancelationEmail(final PortalOrder order) {
		 Thread thread = new Thread(){
			 public void run(){
				final EmailTemplate template = emailTemplateService.getByCode(EmailTemplate.PORTAL_ORDER_CANCELATION);
				sendPortalOrderEmail(order, template);
			 }
		 };
		 thread.run();
	}
	
	private void sendPortalOrderEmail(final PortalOrder order, final EmailTemplate emailTemplate, final RequestContext requestContext){
		Validate.notNull(emailTemplate);
		Validate.notNull(order);
		
		final BasicSettings basicSettings = basicSettingsService.getBasicSettings();
		final Map<String, Object> context = getMailContextPortalOrder(basicSettings, order);
		HtmlMailMessage customerMailMessage = new HtmlMailMessage(basicSettings.getSystemEmail(), emailTemplate,  context);
		customerMailMessage.addRecipientTo(order.getEmail());
		final String mediatorEmailAddress = determineMediatorEmail(basicSettings, order);
		final String templateCode = emailTemplate.getCode();
		if(templateCode.equals(EmailTemplate.PORTAL_ORDER_CREATE)){
			if(requestContext != null){
				final ByteFileDto file = createInvoiceAttachement(order, basicSettings, requestContext);
				customerMailMessage.addAttachment(file);
			}
			sendEmailToMediator(EmailTemplate.PORTAL_MEDIATOR_INFO_CREATE_ORDER, context, mediatorEmailAddress , basicSettings);
		}else if(templateCode.equals(EmailTemplate.PORTAL_ORDER_ACTIVATION)){
			sendEmailToMediator(EmailTemplate.PORTAL_MEDIATOR_INFO_ACTIVATION, context, mediatorEmailAddress , basicSettings);
		}else if(templateCode.equals(EmailTemplate.PORTAL_ORDER_CANCELATION)){
			sendEmailToMediator(EmailTemplate.PORTAL_MEDIATOR_INFO_CANCELATION, context, mediatorEmailAddress , basicSettings);
		}
		nlfnormMailSender.send(customerMailMessage);
	}
	
	
	private void sendPortalOrderEmail(final PortalOrder order, final EmailTemplate emailTemplate){
		sendPortalOrderEmail(order, emailTemplate, null);
	}
	
	
	
	
	private void sendEmailToMediator(final String emailTemplateCode,final  Map<String, Object> context, final  String mediatorEmailAddress, final BasicSettings settings){
			if(StringUtils.isNotBlank(mediatorEmailAddress)){
				final EmailTemplate mediatorTemplate = emailTemplateService.getByCode(emailTemplateCode);
				HtmlMailMessage customerMailMessage = new HtmlMailMessage(settings.getSystemEmail(), mediatorTemplate, context);
				customerMailMessage.addRecipientTo(mediatorEmailAddress);
				nlfnormMailSender.send(customerMailMessage);
			}
	}

	private Map<String, Object> getMailContextPortalOrder(final BasicSettings basicSettings, final PortalOrder order){
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
	
	
	private String determineMediatorEmail(final BasicSettings settings, final PortalOrder order){
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
	public StringBuilder getForamtedOrderItems(final PortalOrder order) {
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

	@Override
	public PortalOrder getByCode(final String code) {
		return portalOrderDao.getByCode(code);
	}
	
	private ByteFileDto createInvoiceAttachement(final PortalOrder order, final BasicSettings settings, final RequestContext context){
		// type == 1 proforma invoice
		Map<String,Object> model = prepareInvoiceModel(order, settings, 1, context);
		try{
			ByteArrayOutputStream file = pdfXhtmlExporter.generatePdf(ExportController.INVOICE_FLT_TEMPLATE, model, "/");
			ByteFileDto fileDto = new ByteFileDto();
			fileDto.setData(file.toByteArray());
			fileDto.setName(getFileNameFor(1, order));
			fileDto.setContentType("application/pdf");
			return fileDto;
		}catch(Exception e){
			
		}
		return null;
	}
	
	@Override
	public Map<String, Object> prepareInvoiceModel(final PortalOrder order, final BasicSettings settings, final int type, final RequestContext context){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("springMacroRequestContext", context);
		model.put("portalOrder", order);
		model.put("settings", basicSettingsService.getBasicSettings());
		model.put("created", DateTimeUtils.getFormatedLocalDate(order.getChanged()));
		model.put("customerCountry", messageSource.getMessage(order.getPortalCountry().getCode(), null, ContextHolder.getLocale()));
		model.put("type", type);
		return model;
	}

	
	@Override
	public String getFileNameFor(final int type, final PortalOrder portalOrder) {
		if(type == 1){
			return "proforma-" + portalOrder.getOrderNo() + ".pdf";
		}
		return "prikaz-k-fakturaci-" + portalOrder.getOrderNo() + ".pdf";
	}
	
}
