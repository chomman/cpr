package cz.nlfnorm.dao;

import cz.nlfnorm.entities.Tag;

public interface TagDao extends BaseDao<Tag, Long>{

	Tag getTagByName(String name);
	
}
