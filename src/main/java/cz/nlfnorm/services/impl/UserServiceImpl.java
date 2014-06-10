package cz.nlfnorm.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.AuthorityDao;
import cz.nlfnorm.dao.UserDao;
import cz.nlfnorm.dto.UserPage;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.BasicSettings;
import cz.nlfnorm.entities.EmailTemplate;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.mail.HtmlMailMessage;
import cz.nlfnorm.mail.NlfnormMailSender;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.EmailTemplateService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.spring.security.MD5Crypt;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.utils.UserUtils;

@Service("userService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired
	private NlfnormMailSender nlfnormMailSender;
	@Autowired
	private BasicSettingsService basicSettingsService;
	
	@Value("#{config['changePasswordTokenValidity']}")
	private int changePasswordTokenValidity;
		    
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
	public List<Authority> getAllAuthorities(){
		return userDao.getAllAuthorities();
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
	public UserPage getUserPage(int pageNumber, Map<String, Object> criteria) {
		return userDao.getUserPage(pageNumber, validateCriteria(criteria));
	}

	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put(Filter.ORDER, ParseUtils.parseIntFromStringObject(criteria.get(Filter.ORDER)));
			criteria.put(Filter.CREATED_FROM, ParseUtils.parseDateTimeFromStringObject(criteria.get(Filter.CREATED_FROM)));
			criteria.put(Filter.CREATED_TO, ParseUtils.parseDateTimeFromStringObject(criteria.get(Filter.CREATED_TO)));
			criteria.put(Filter.ENABLED, ParseUtils.parseStringToBoolean(criteria.get(Filter.ENABLED)));
		}
		return criteria;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUserNameUniqe(Long id, String userName){
		return userDao.isUserNameUniqe(id, userName.trim());
	}
	
	public void setUserPassword(User user, final String password){
		Validate.notNull(user);
		Validate.notEmpty(password);
		user.setSgpPassword(MD5Crypt.crypt(password));
		user.setPassword(passwordEncoder.encode( password ));
	}
	
	@Override
	public void createOrUpdateUser(User user) {
		final User loggedUser = UserUtils.getLoggedUser();
		user.setChangedBy(loggedUser);
		user.setChanged(new LocalDateTime());
		if(user.getId() == null){
			user.setCreatedBy(loggedUser);
			user.setCreated(new LocalDateTime());
			userDao.save(user);
		}else{
			userDao.update(user);
		}
		
	}

	@Override
	public List<User> autocomplateSearch(String query) {
		return userDao.autocomplateSearch(query);
	}

	@Override
	public void sendChangePassowrdEmail(final User user, final String changePasswordUrl) {
		Validate.notNull(user);
		final String token = RandomStringUtils.randomAlphanumeric(32);
		user.setChangePasswordRequestDate(new LocalDateTime().plusHours(changePasswordTokenValidity));
		user.setChangePasswordRequestToken(token);
		final EmailTemplate template = emailTemplateService.getByCode(EmailTemplate.USER_CHANGE_PASSWORD_REQUEST);
		Validate.notNull(template);
		final BasicSettings settings = basicSettingsService.getBasicSettings();
		Map<String, Object> mailContext = new HashMap<String, Object>();
		mailContext.put("odkaz", changePasswordUrl + token);
		mailContext.put("platnostOdkazu", changePasswordTokenValidity);
		final HtmlMailMessage mailMessage = new HtmlMailMessage(settings.getSystemEmail(), template,  mailContext);
		mailMessage.addRecipientTo(user.getEmail());
		nlfnormMailSender.send(mailMessage);
		updateUser(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserByChangePasswordRequestToken(final String token) {
		if(StringUtils.isBlank(token)){
			return null;
		}
		return userDao.getUserByChangePasswordRequestToken(token);
	}

	@Override
	public List<User> getPortalUserBeforeExpiration(int weeksBefore) {
		// TODO
		LocalDate threshold = new LocalDate().plusWeeks(weeksBefore);
		StringBuilder hql = new StringBuilder("from User u");
		hql.append("	left join u.onlinePublications uop ")
		   .append("	inner join u.authoritySet authority ")
		   .append(" where u.enabled=true and authority.code = :authority and ")
		   .append(" ((u.registrationValidity < :threshold and u.emailAlertSent = false) or")
		   .append("  (uop.validity < :threshold and uop.emailAlertSent = false ) )");
		return null;
	}
}
