package cz.nlfnorm.tests;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = 
	{ 
	"/application-testContext.xml",  
	"file:src/main/webapp/WEB-INF/spring/applicationContext-security.xml"
})
@TransactionConfiguration
@Transactional
public class AbstractTest{
	
	 @Autowired
	 private UserService userService;
	
	 @Before
     public void onSetUp() {
         User user = new User();
         user.setFirstName("testF");
         user.setLastName("testL");
         user.setEmail("logged-user@nlfnorm.cz");
         userService.saveUser(user);
         Authentication auth = new UsernamePasswordAuthenticationToken(user,null);
         SecurityContextHolder.getContext().setAuthentication(auth);
     }
	 
}
