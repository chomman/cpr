package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.AuthorityDao;
import sk.peterjurkovic.cpr.dao.UserDao;
import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.UserService;

@Service("userService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService, UserDetailsService {
	
	 @Autowired
	 private UserDao userDao;
	 @Autowired
	 private AuthorityDao authorityDao;
	    
	@Override
	@Transactional(readOnly = true)
	public Authority getAuthorityByCode(String code) {
		return authorityDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return userDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public void saveUser(User user) {
		userDao.save(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public User mergeUser(User user) {
		return userDao.merge(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User getById(Long id) {
		return userDao.getByID(id);
	}

	@Override
	public void removeUser(User user) {
		userDao.remove(user);
	}

	@Override
	public void disableUser(User user) {
		 user.setEnabled(Boolean.FALSE);
	     saveUser(user);
	}
	
	@Override
	public void enableUser(User user) {
		 user.setEnabled(Boolean.TRUE);
	     saveUser(user);
	}

	@Override
	@Transactional(readOnly = true)
	public Authority getAuthorityByName(String name) {
		return authorityDao.getAuthorityByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsUser(String username) {
		if (getUserByUsername(username) == null) {
            return false;
        }
        return true;
	}

	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDao.getUserByUsername(username);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Authority> getAuthorities(){
		return authorityDao.getAll();
	}

}
