package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Tag;

public interface TagService {

	List<Tag> searchInTags(String tagName);
	
}
