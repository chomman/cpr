package sk.peterjurkovic.cpr.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.TagDao;
import sk.peterjurkovic.cpr.entities.Tag;
import sk.peterjurkovic.cpr.services.TagService;

@Service("tagService")
@Transactional(propagation = Propagation.REQUIRED)
public class TagServiceImpl implements TagService{
	
	@Autowired
	private TagDao tagDao;
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Tag> searchInTags(String tagName) {
		List<Tag> tags = tagDao.searchByTagName(tagName);
		if(tags == null){
			tags = new ArrayList<Tag>();
		}
		return tags;
	}

}
