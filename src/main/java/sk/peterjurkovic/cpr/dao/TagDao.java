package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Tag;

/**
 * Rozhranie pre pracu so stitkami
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface TagDao extends BaseDao<Tag, Long>{
	
	/**
	 * Vyhlada vsetky stitky, podla daneho nazvu
	 * 
	 * @param term - vyraz, na zaklade ktoreho sa vykona hladanie
	 * @return List<Tag> zoznam vyhovujucich tago
	 */
	List<Tag> searchByTagName(String tagName);
}
