package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.Tag;
import cz.nlfnorm.entities.Webpage;

public interface TagService {
	
	Tag getById(Long id);
	
	Tag getByTagName(String name);
	
	void removeTag(Tag tag);
	
	void createTag(Tag tag);
	
	void removeNotAssociatedTags();
	
	void setWebpageTags(Webpage webpage, List<String> tagList);
	
}
