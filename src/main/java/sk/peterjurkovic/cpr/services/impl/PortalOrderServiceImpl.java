package sk.peterjurkovic.cpr.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.constants.Filter;
import sk.peterjurkovic.cpr.dao.PortalOrderDao;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.PortalOrder;
import sk.peterjurkovic.cpr.entities.PortalProduct;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.enums.OrderStatus;
import sk.peterjurkovic.cpr.enums.PortalProductInterval;
import sk.peterjurkovic.cpr.services.PortalOrderService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;
import sk.peterjurkovic.cpr.web.editors.LocalDateEditor;


@Transactional(propagation = Propagation.REQUIRED)
@Service("portalOrderService")
public class PortalOrderServiceImpl implements PortalOrderService {

	@Autowired
	private PortalOrderDao portalOrderDao;
	@Autowired
	private LocalDateEditor localDateEditor;
	@Autowired
	private UserService userService;
	
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
		final User user = UserUtils.getLoggedUser();
		order.setChangedBy(user);
		order.setChanged(new LocalDateTime());
		
		if(sendEmail){
			if(order.getDateOfActivation() == null && order.getOrderStatus().equals(OrderStatus.PAYED)){
				activateProduct(order);
			}
			// TODO send information email impl
		}
		update(order);
	}

	@Override
	public void activateProduct(PortalOrder order) {
		Validate.notNull(order);
		User user = order.getUser();
		Validate.notNull(user);
		PortalProduct product = order.getPortalProduct();
		Validate.notNull(product);
		
		LocalDate today = new LocalDate();
		LocalDate newRegistrationValidity = null;
		if(user.getRegistrationValidity() == null || user.getRegistrationValidity().isBefore(today)){
			newRegistrationValidity = calculateNewRegistrationValidity(today, product);
		}else{
			newRegistrationValidity = calculateNewRegistrationValidity(user.getRegistrationValidity(), product);
		}
		
		user.setRegistrationValidity(newRegistrationValidity);
		userService.createOrUpdateUser(user);
		
		order.setDateOfActivation(today);
		update(order);
	}
	
	
	private LocalDate calculateNewRegistrationValidity(LocalDate fromDate, PortalProduct product){
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

}
