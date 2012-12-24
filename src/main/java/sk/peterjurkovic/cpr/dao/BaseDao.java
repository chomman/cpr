package sk.peterjurkovic.cpr.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T, ID extends Serializable> {

    public void save(T entity);
    public void update(T entity);
    public void remove(T entity);
    public void refresh(T entity);
    public T merge(T entity);
    public void evict(T entity);
    public List<T> getAll();
    public T getByID(ID id);
    public void flush();
    public T getByCode(String code);
}
