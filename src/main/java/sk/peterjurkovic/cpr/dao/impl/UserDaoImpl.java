package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.UserDao;
import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;



@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao{
	
	public UserDaoImpl() {
        super(User.class);
    }
	
	@Override
	public User getUserByUsername(String username) {
		return (User) sessionFactory.getCurrentSession()
				.createQuery("FROM User u WHERE u.email=:username")
				.setString("username", username)
				.setMaxResults(1)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByRole(String code) {
		return sessionFactory.getCurrentSession()
			.createQuery("FROM User u LEFT JOIN u.authoritySet authority WHERE authority.code = :code")
			.setString("code", code)
			.list();
	}

	@Override
	public List<User> getUsers() {
		return getAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Authority> getAuthorities() {
		 StringBuffer hql = new StringBuffer();
         hql.append("  SELECT role ");
         hql.append("    FROM Authority role ");
         hql.append("   WHERE role.enabled = true  ");
         hql.append("     AND role.code like 'ROLE_%' ");
         hql.append("ORDER BY role.name");
         
         return sessionFactory.getCurrentSession()
         				.createQuery(hql.toString())
         				.list();
	}


}
