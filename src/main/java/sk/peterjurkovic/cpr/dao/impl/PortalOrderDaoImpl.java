package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.PortalOrderDao;
import sk.peterjurkovic.cpr.entities.PortalOrder;

@Repository("portalOrderDao")
public class PortalOrderDaoImpl extends BaseDaoImpl<PortalOrder, Long> implements PortalOrderDao {

	public PortalOrderDaoImpl(){
		super(PortalOrder.class);
	}
	

}
