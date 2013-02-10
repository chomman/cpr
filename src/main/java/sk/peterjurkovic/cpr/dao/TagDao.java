package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Tag;

public interface TagDao extends BaseDao<Tag, Long>{
	
	List<Tag> searchByTagName(String tagName);
}
