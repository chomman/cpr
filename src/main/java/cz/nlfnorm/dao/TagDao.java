package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.entities.Tag;

public interface TagDao extends BaseDao<Tag, Long>{

	Tag getTagByName(String name);
	
	List<String> autocomplete(String term);
}
