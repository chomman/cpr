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
import sk.peterjurkovic.cpr.entities.UserOnlinePublication;
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
				UserOnlinePublication userOnlinePublication =  user.getUserOnlinePublication(product.getOnlinePublication());
				if(userOnlinePublication == null){
					// create new one
					userOnlinePublication = new UserOnlinePublication(user);
					LocalDate newValidity = computeNewValidity( null , product);
					userOnlinePublication.setValidity(newValidity);
					userOnlinePublication.setOnlinePublication(product.getOnlinePublication());
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

}
