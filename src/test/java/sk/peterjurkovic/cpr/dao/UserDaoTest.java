package sk.peterjurkovic.cpr.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.entities.User;

public class UserDaoTest extends AbstractTest {
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testCreateUser(){
		User user = getUserTestInstance();
		userDao.save(user);
		User persistedUser = userDao.getUserByUsername(user.getEmail());
		Assert.assertEquals(user, persistedUser);
	}
	
	
	
	private User getUserTestInstance(){
		User user = new User();
		user.setEmail("test@test.com");
		user.setFirstName("Test");
		user.setLastName("Test");
		return user;
	}
}
