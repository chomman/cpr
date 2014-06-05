package cz.nlfnorm.config.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.nlfnorm.config.TestConfig;
import cz.nlfnorm.dao.UserDao;
import cz.nlfnorm.entities.User;

public class UserDaoTest extends TestConfig {
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testUserCreate(){
		
		User user = getUserTestInstance();
		
		userDao.save(user);
		
		User persistedUser = userDao.getUserByUsername(user.getEmail());
		
		Assert.assertEquals(user, persistedUser);
		
		userDao.remove(user);
		
		persistedUser = userDao.getUserByUsername(user.getEmail());
		
		Assert.assertEquals(null, persistedUser);
	}
	
	
	
	private User getUserTestInstance(){
		User user = new User();
		user.setEmail("test@test.com");
		user.setFirstName("Test");
		user.setLastName("Test");
		return user;
	}
}
