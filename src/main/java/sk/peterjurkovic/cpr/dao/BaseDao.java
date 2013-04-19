package sk.peterjurkovic.cpr.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Zakladne rozhranie, pre poskytovanie zakladnych CRUD operacii, rozsirujucim triedam
 * 
 * @author peter.jurkovic
 *
 * @param <T> 
 * @param <ID>
 */
public interface BaseDao<T, ID extends Serializable> {
	
	/**
	 * Vytvori novu polozku v databazy
	 * @param entity
	 */
    public void save(T entity);
    
    /**
     *  Aktualizuje entitu
     * 
     * @param entity
     */
    public void update(T entity);
    
    /**
     * Odstrani polozku z DB
     * 
     * @param entity
     */
    public void remove(T entity);
    
    /**
     * Refreshne entity
     * @param entity
     */
    public void refresh(T entity);
    
    /**
     * Aktualizuje entity
     * 
     * @param entity
     * @return
     */
    public T merge(T entity);
    public void evict(T entity);
    
    /**
     * Vrati vsetky polozky danej entity
     * 
     * @return
     */
    public List<T> getAll();
    
    /**
     *  Vrati polozku danej entity, na zaklade daneho ID
     * @param id
     * @return T
     */
    public T getByID(Long id);
    public void flush();
    public T getByCode(String code);
}
