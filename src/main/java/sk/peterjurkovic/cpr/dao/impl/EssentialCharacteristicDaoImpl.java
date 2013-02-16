package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.EssentialCharacteristicDao;
import sk.peterjurkovic.cpr.entities.EssentialCharacteristic;


@Repository("essentialCharacteristic")
public class EssentialCharacteristicDaoImpl extends BaseDaoImpl<EssentialCharacteristic, Long> implements EssentialCharacteristicDao{
	
	public EssentialCharacteristicDaoImpl(){
		super(EssentialCharacteristic.class);
	}
}
