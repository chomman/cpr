package sk.peterjurkovic.cpr.dao.impl;

import sk.peterjurkovic.cpr.dao.TagDao;
import sk.peterjurkovic.cpr.entities.Tag;

public class TagDaoImpl extends BaseDaoImpl<Tag, Long> implements TagDao{
	
	public TagDaoImpl(){
		super(Tag.class);
	}
}
