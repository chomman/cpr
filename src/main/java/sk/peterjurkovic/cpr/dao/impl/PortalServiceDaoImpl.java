package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.PortalServiceDao;
import sk.peterjurkovic.cpr.entities.PortalService;

@Repository("portalServiceDao")
public class PortalServiceDaoImpl extends BaseDaoImpl<PortalService, Long> implements PortalServiceDao {

	public PortalServiceDaoImpl() {
		super(PortalService.class);
	}

}
