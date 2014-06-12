package cz.nlfnorm.services.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.ExceptionLogDao;
import cz.nlfnorm.entities.ExceptionLog;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.mail.HtmlMailMessage;
import cz.nlfnorm.mail.NlfnormMailSender;
import cz.nlfnorm.services.ExceptionLogService;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;

@Service("exceptionLogService")
@Transactional(propagation = Propagation.REQUIRED)
public class ExceptionLogServiceImpl implements ExceptionLogService {
	
	@Value("#{config['mail.sendExcaptions']}")
	private boolean sendExceptions;
	
	@Value("#{config['mail.sendExceptionTo']}")
	private String sendExceptionsToEmailAddress;
	
	@Autowired
	private NlfnormMailSender nlfnormMailSender;

	
	/**
	 * Delay between sending email alert in minutes
	 */
	public final static int EMAIL_ALERT_DELAY_BETWEEN_EXCEPTIONS = 10;
	
	@Autowired
	private ExceptionLogDao exceptionLogDao;
	
	@Override
	public void create(ExceptionLog exceptionLog) {
		exceptionLogDao.save(exceptionLog);
	}
	
	@Override
	public void logException(Exception exception){
		logException(exception, new ExceptionLog());
	}
	
	
	@Override
	public void logException(HttpServletRequest request, Exception exception){
		ExceptionLog log = new ExceptionLog();
		log.setUrl(request.getRequestURL()+ "?" +request.getQueryString());
		log.setMehtod(request.getMethod());
		log.setReferer(request.getHeader("referer"));
		log.setRequestParams(getRequestMparams(request));
		log.setRequestHeaders(getRequestHeaders(request));
		log.setQueryParams(request.getQueryString());
		logException(exception, log);
	}
	
	private void logException(Exception exception, ExceptionLog log){
		log.setCreated(new DateTime());
		log.setStackTrace(ExceptionUtils.getStackTrace(exception));
		log.setMessage(exception.getMessage());
		log.setType(exception.getClass().getName());
		User user = UserUtils.getLoggedUser();
		if(user != null){
			log.setUser(user);
		}
		if(shuldSendEmailAlertFor(log)){
			sendExceptionEmailAlert(log);
		}
		create(log);
	}
	
	private String getRequestMparams(HttpServletRequest request){
		 Map<String, List<String>> reqParams = RequestUtils.getParametersMap(request);
		 StringBuilder items = new StringBuilder();
	        if (reqParams != null) {
	            for (Map.Entry<String, List<String>> entry : reqParams.entrySet()) {
	                items.append(entry.getKey() + ": ");
	                for (String value : entry.getValue()) {
	                    items.append(value);
	                }
	                items.append("<br>");
	            }
	        }
	    return items.toString();
	}
	
	
	private String getRequestHeaders(HttpServletRequest request){
		Map<String, String> headers = RequestUtils.getHeadersMap(request);
		 StringBuilder items = new StringBuilder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                items.append(entry.getKey() + ": " + entry.getValue() + "<br>");
            }
        }
        return items.toString();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ExceptionLog> getExceptionLogPage(int pageNumber, Map<String, Object> criteria) {
		return exceptionLogDao.getExceptionLogPage(pageNumber, validateCriteria(criteria));
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCountOfLogs(Map<String, Object> criteria) {
		return exceptionLogDao.getCountOfLogs(validateCriteria(criteria));
	}
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put("orderBy", ParseUtils.parseIntFromStringObject(criteria.get("orderBy")));
			criteria.put("createdFrom", ParseUtils.parseDateTimeFromStringObject(criteria.get("createdFrom")));
			criteria.put("createdTo", ParseUtils.parseDateTimeFromStringObject(criteria.get("createdTo")));
		}
		return criteria;
	}

	@Override
	@Transactional(readOnly = true)
	public ExceptionLog getExceptionLogById(Long id) {
		return exceptionLogDao.getByID(id);
	}

	@Override
	public void deleteException(ExceptionLog exceptionLog) {
		exceptionLogDao.remove(exceptionLog);
	}

	@Override
	@Transactional(readOnly = true)
	public ExceptionLog getLastException() {
		return exceptionLogDao.getLastException();
	}
	
	private boolean shuldSendEmailAlertFor(final ExceptionLog ex){
		final ExceptionLog lastEx = getLastException();
		if(lastEx == null){
			return true;
		}else if(lastEx.getType().equals(ex.getType())){
			return false;
		}
		DateTime now = new DateTime();
		DateTime exCreatedTimeWithOffest = lastEx.getCreated().plusMinutes(EMAIL_ALERT_DELAY_BETWEEN_EXCEPTIONS);
		if(exCreatedTimeWithOffest.isBefore(now)){
			return true;
		}
		return false;
	}
	
	
	@Async
	private void sendExceptionEmailAlert(final ExceptionLog ex){
		new Thread(){
			@Override
			public void run() {
				HtmlMailMessage message = new HtmlMailMessage("portal@nlfnorm.cz",ex.getType());
				message.addRecipientTo(sendExceptionsToEmailAddress);
				message.setHtmlContent(ex.getStackTrace());
				nlfnormMailSender.send(message);
			}
		}.run();
	}
	
}
