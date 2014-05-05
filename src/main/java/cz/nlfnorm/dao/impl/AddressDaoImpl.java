package cz.nlfnorm.dao.impl;

import cz.nlfnorm.dao.AddressDao;
import cz.nlfnorm.entities.Address;

public class AddressDaoImpl extends BaseDaoImpl<Address, Long> implements AddressDao {
	
	public AddressDaoImpl(){
		super(Address.class);
	}
}
