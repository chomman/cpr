package cz.nlfnorm.services.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.TagDao;
import cz.nlfnorm.entities.Tag;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.services.TagService;

@Service("tagService")
@Transactional(propagation = Propagation.REQUIRED)
public class TagServiceImpl implements TagService {

	@Autowired
	private TagDao tagDao;
	
	@Override
	@Transactional(readOnly = true)
	public Tag getById(final Long id) {
		return tagDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Tag getByTagName(final String name) {
		return tagDao.getTagByName(name);
	}

	@Override
	public void removeTag(final Tag tag) {
		tagDao.remove(tag);
	}

	@Override
	public void createTag(final Tag tag) {
		tagDao.save(tag);
	}

	@Override
	public void removeNotAssociatedTags() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWebpageTags(Webpage webpage, final List<String> tagList) {
		Validate.notNull(webpage);
		Validate.notNull(webpage.getTags());
		webpage.getTags().clear();
		if(CollectionUtils.isNotEmpty(tagList)){
			for(String tagName : tagList){
				webpage.getTags().add(getOrCreateTag(tagName));
			}
		}
	}

	private Tag getOrCreateTag(final String tagName){
		Tag tag = getByTagName(tagName);
		if(tag != null){
			return tag;
		}
		tag = new Tag(tagName);
		createTag(tag);
		return tag;
	}
}
