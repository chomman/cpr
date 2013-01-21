package sk.peterjurkovic.cpr.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.AuthorityDao;
import sk.peterjurkovic.cpr.dao.UserDao;
import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.ParseUtils;

@Service("userService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
	
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
	public List<User> getAllUsers() {
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
	public User getUserById(Long id) {
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
	public List<User> getUserPage(int pageNumber, Map<String, Object> criteria) {
		return userDao.getUserPage(pageNumber, validateCriteria(criteria));
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCountOfUsers(Map<String, Object> criteria) {
		return userDao.getCountOfUsers(validateCriteria(criteria));
	}
	
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put("orderBy", ParseUtils.parseIntFromStringObject(criteria.get("orderBy")));
			criteria.put("createdFrom", ParseUtils.parseDateTimeFromStringObject(criteria.get("createdFrom")));
			criteria.put("createdTo", ParseUtils.parseDateTimeFromStringObject(criteria.get("createdTo")));
			criteria.put("enabled", ParseUtils.partseStringToBoolean(criteria.get("enabled")));
		}
		return criteria;
	}

}
