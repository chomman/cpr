package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;


/**
 * DAO rozhranie pre manipulaciu s uzivatelom
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface UserDao extends BaseDao<User, Long>{
	
	/**
	 * Vrati uzivatela na zaklade daneho uzivatelskeho mena
	 * 
	 * @param String uzivatelske meno hladaneho uzivatela
	 * @return User
	 */
	User getUserByUsername(String username);
	
	
	/**
	 * Vrati zoznam uzivatelov, ktorych nazov, id, meno, priezvisko vyhovuje hladanemu vyrazu
	 * 
	 * @param String hladany term
	 * @return List<User> zoznam najdenych uzivatelov
	 */
	List<User> getUsersByRole(String roleName);
	
	
	/**
	 * Vrati zoznam uzivatelov s danou uzivatelskou rolou
	 * 
	 * @param String kod uzivatelskej role
	 * @return List<User> zoznam vyhovujucich uzivatelov
	 */
	List<Authority> getAllAuthorities();
	

	
	List<User> autocomplateSearch(String query);
	
	
	/**
	 * Vrati stranku uzivatelov, vyhovujucich danym kriteriam
	 * 
	 * @param int cislo stranky
	 * @param Map<String, Object> kriteria
	 */
	List<User> getUserPage(int pageNumber, Map<String, Object> criteria);
	
	
	/**
	 * Vrati pocet zaznamov/uzivatelov, vyhovujucich danym kriteriam
	 * 
	 * @param Map<String, Object> kriteria
	 * @return Long pocet zaznamov
	 */
	Long getCountOfUsers(Map<String, Object> criteria);
	
	
	/**
	 * Skontroluje, ci je dane uzivatelske meno v ramci systemu jedinecne
	 * 
	 * @param Long id uzivatela
	 * @param String kontrolovane uzivatelske meno
	 */
	boolean isUserNameUniqe(Long id, String userName);
}
