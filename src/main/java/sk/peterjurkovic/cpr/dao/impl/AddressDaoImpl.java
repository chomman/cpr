package sk.peterjurkovic.cpr.dao.impl;

import sk.peterjurkovic.cpr.dao.AddressDao;
import sk.peterjurkovic.cpr.entities.Address;

public class AddressDaoImpl extends BaseDaoImpl<Address, Long> implements AddressDao {
	
	public AddressDaoImpl(){
		super(Address.class);
	}
}
