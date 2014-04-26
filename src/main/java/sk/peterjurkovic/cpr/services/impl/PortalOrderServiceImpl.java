package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.PortalOrderDao;
import sk.peterjurkovic.cpr.entities.PortalOrder;
import sk.peterjurkovic.cpr.services.PortalOrderService;


@Transactional(propagation = Propagation.REQUIRED)
@Service("portalOrderService")
public class PortalOrderServiceImpl implements PortalOrderService {

	@Autowired
	private PortalOrderDao portalOrderDao;
	
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

}
